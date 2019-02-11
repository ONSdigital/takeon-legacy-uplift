################################################################################
#
#   Name: DataWrangler.py
#
#   Author: kilbar
#   Purpose: We needed a way of massaging data into the correct format for
#            insertion into the new Contributor table.
#
#   Operation: Builds a tree of consisting of columns that corispond to a model
#              of the Contributor table. Then preforms a breath first search on
#              the tree transposing a dictionary of columns into a list of rows
#
#   Inputs: Flat file, pipe delimited. List of columns in the order that they
#           appear in the file. List of columns as they're required to appear in
#           the database table.
#
#
#
################################################################################

from collections import OrderedDict
import time
import os
from pathlib import Path
import datetime
import random
import pyodbc

class DataWrangler:
    def __init__(self, path, delimiter, givenOrder, wantedOrder):
        self.inputData = None
        self.delimiter = delimiter
        self.splitData = []
        self.givenOrder = givenOrder.split(',')
        self.path = path
        self.wantedOrder = wantedOrder.split(',')
        self.defaultEntry = {}
        self.duplcateHeaders = {}

    def additionalColDefault(self):
        for header in self.wantedOrder:
            if header not in self.givenOrder:
                self.defaultEntry[header] = []

    def importData(self):
        with open(self.path, 'r') as file:
            self.inputData = file.read()

    def divideData(self):
        inputData = self.inputData.split("\n")
        for row in inputData:
            self.splitData.append(row.split("{}".format(self.delimiter)))
        self.splitData.pop()

    def createColumns(self):
        self.columnHolder = {self.givenOrder[i]: []
                            for i in range(len(self.splitData[0]))}
        #Insert columns that might not exist in the CSW database
        #We can get these from the defaultEntry dictionary
        for header in self.defaultEntry.keys():
                self.columnHolder[header] = []

        for key in self.defaultEntry.keys():
            if key in ['ReceiptDate', 'LockedDate','LastUpdatedBy','LastUpdatedDate']:
                self.columnHolder[key] = [None for i in range(len(self.splitData))]
            else:
                self.columnHolder[key] = [0 for i in range(len(self.splitData))]

        creatorNames = ['Terry', 'Dave', 'Berta']
        status = ['Dead', 'Saved not Validated', 'Overridden', 'Validation Failed', 'No Response']
        self.columnHolder['CreatedDate'] = [datetime.datetime.now().isoformat() for i in range(len(self.splitData))]
        self.columnHolder['CreatedBy'] = [creatorNames[random.randint(0,2)] for i in range(len(self.splitData))]
        self.columnHolder['Status'] = [status[random.randint(0,4)] for i in range(len(self.splitData))]

        for row in self.splitData:
            for dataum in range(len(self.splitData[0])):
                self.columnHolder[self.givenOrder[dataum]].append(row[dataum])

    def reorderColumns(self):
        self.reOrderedColumns = OrderedDict((i, self.columnHolder[i]) for i in self.wantedOrder)
        print(len(self.reOrderedColumns.keys()))

    def recombineData(self):
        self.inputData = None
        self.splitData = None
        reOrderedColumns = self.reOrderedColumns
        reOrderedColumnsKeys = self.reOrderedColumns.keys()
        reOrderedColumnsValues = self.reOrderedColumns.values()
        delimiterOut = "|"
        rectifedData = []
        dictValues = list(reOrderedColumnsValues)
        print(len(dictValues[0]))
        print(reOrderedColumnsKeys)
        for j in range(len(dictValues[0])):
            x = [reOrderedColumns[i][j] for i in reOrderedColumnsKeys]
            rectifedData.append(x)
        return rectifedData

class SimpleMsSqlConnection():
    def __init__(self, ServerName = "", Database = ""):
        self.Driver = '{SQL Server Native Client 11.0}'
        self.ConnectionString = "Driver={};Server={};Database={};UID={};PWD={}".format(self.Driver, ServerName, Database, os.environ["DATASOURCE_USERNAME"], os.environ["DATASOURCE_PASSWORD"])

    def connect(self):
        self.connection = pyodbc.connect(self.ConnectionString)
        self.cursor = self.connection.cursor()
        #self.cursor.fast_executemany = True

    def runSQL(self, command = "", parameters = ""):
        try:
            print("Trying")
            self.connect()
            self.cursor.executemany(command,parameters)
            self.connection.commit()
        except pyodbc.Error as e:
            print("ERROR!")
            print(str(e))
        finally:
            print("Finally")
            self.disconnect()

    def disconnect(self):
        self.cursor.close()  # Closing connections
        self.connection.close()


def insertContributorsToDb(sqlParameters = ''):
    query = "Insert Into [dev01].Contributor_csw (Reference,Period,Survey,FormID,Status,ReceiptDate,LockedBy," \
            "LockedDate,FormType,Checkletter,FrozenSicOutdated,RuSicOutdated,FrozenSic,RuSic,FrozenEmployees," \
            "Employees,FrozenEmployment,Employment,FrozenFteEmployment,FteEmployment,FrozenTurnover,Turnover," \
            "EnterpriseReference,WowEnterpriseReference,CellNumber,Currency,VatReference,PayeReference," \
            "CompanyRegistrationNumber,NumberLiveLocalUnits,NumberLiveVat,NumberLivePaye,LegalStatus," \
            "ReportingUnitMarker,Region,BirthDate,EnterpriseName,ReferenceName,ReferenceAddress,ReferencePostcode," \
            "TradingStyle,Contact,Telephone,Fax,SelectionType,InclusionExclusion,CreatedBy,CreatedDate," \
            "LastUpdatedBy,LastUpdatedDate)" \
            "Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
    connection = SimpleMsSqlConnection(ServerName='cr1vwsql14-d-01', Database='CollectionDev')
    connection.runSQL(query, sqlParameters)


print(os.listdir(r'C:\Users\kilbar\Documents'))
print("Using {} as root".format(Path.home()))
fileName = "fs_cora_contributor.pipe"
path = r'C:\Users\kilbar\Documents\fs_cora_contributor.pipe.txt'
givenCols = "Period_Contributor_Id,Inquiry_Id,Inquiry_IDBR_Code,Form_Version_Id,Period,Period_Year,Period_Month,RU_Ref,RU_Check_Letter,EntRef,WowEntRef,Status,Employee_Count,Current_SIC,Previous_SIC,Contributor_Region,RU_Name,Address_Line1,Address_Line2,Address_Line3,Address_Line4,Address_Line5,Post_Code,Contact,Telephone,Email_address,Form_Currency,Contributor_Source,Data_Source,Turnover,Manager_Checked,Legal_Status,Receipt_Date,First_Time_Failures,Cell_Number,Cell_Selection,Form_Value,Form_Type,Newly_Selected,IDBR_Employees,IDBR_FroEmployees,IDBR_Employment,IDBR_FroEmployment,IDBR_FTEmpment,IDBR_FroFTEmpment,IDBR_Turnover,IDBR_FroTurnover,IDBR_RuSic_Current,IDBR_FroSic_Current,IDBR_RuSic_Previous,IDBR_FroSic_Previous,Locked_By,Locked_Date,Non_Responded_Periods,Created_By,Created_Date,Last_Updated_By,Last_Updated_Date"
wantedCols = "RU_Ref,Period,Inquiry_IDBR_Code,Form_Version_Id,Status,Receipt_Date,Locked_By,LockedDate,Form_Type,RU_Check_Letter,IDBR_FroSic_Previous,IDBR_RuSic_Previous,IDBR_FroSic_Current,IDBR_RuSic_Current,IDBR_FroEmployees,IDBR_Employees,IDBR_FroEmployment,IDBR_Employment,IDBR_FroFTEmpment,IDBR_FTEmpment,IDBR_FroTurnover,IDBR_Turnover,EntRef,WowEntRef,Cell_Number,Form_Currency,VatReference,PayeReference,CompanyRegistrationNumber,NumberLiveLocalUnits,NumberLiveVat,NumberLivePaye,Legal_Status,ReportingUnitMarker,Contributor_Region,BirthDate,Address_Line1,Address_Line2,Address_Line3,Post_Code,TradingStyle,Contact,Telephone,Fax,SelectionType,InclusionExclusion,Created_By,Created_Date,Last_Updated_By,Last_Updated_Date"
x = DataWrangler(path, "|", givenCols, wantedCols)

#wanted = "period,reference,inquiry_code,form,sic92,selection_to,selection_emp,enterprise,sub_date,receipt_mkr,error_mkr,key_responder,response_type,seltype,legalstatus,inclexcl,receipt_date,go_region,currency,checkletter,name,address,post_code,contact_name,tel_no,fax,region,vat_reference,birthdate,email"
#given = "reference,period,inquiry_code,form,sic92,selection_to,selection_emp,enterprise,sub_date,receipt_mkr,error_mkr,key_responder,response_type,seltype,legalstatus,inclexcl,receipt_date,go_region,currency,checkletter,name,address,post_code,contact_name,tel_no,fax,region,vat_reference,birthdate,email"
#x = DataWrangler(r'C:\Users\kilbar\Documents\output.txt', ':', given, wanted)
x.additionalColDefault()
x.importData()
x.divideData()
x.createColumns()
x.reorderColumns()
output = x.recombineData()
#with open(r'C:\Users\kilbar\Documents\output.txt', 'w+') as file:
#    file.write(output)

sqlParameters = output
insertContributorsToDb(sqlParameters)
