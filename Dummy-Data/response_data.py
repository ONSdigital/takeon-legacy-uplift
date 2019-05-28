"""
Generates a dummy file based on QCAS survey input data supplied by the electronic questionnaire
The output file created is in JSON format
Requires a dummy finalsel file as input (created in the 'generate dummy' script

Input Parameters -
selection file location: 'finalsel file, with csv extention'
response file location: 'Name of the output file'
survey code: 'eg. 019'
Period: 'eg. 202003'
"""

import json
import random
from faker import Faker

fake = Faker('en_GB')
selection_location = input("selection file location: ")
output_location = input("response file location: ")
survey = input("survey code: ")
period = input("period: ")

month = period[4:]
year = period[:4]
#response_list = ["Yes", "No"]


def create_file_paths(selection_location, output_location):
    return {"selection": selection_location, "response": output_location}


class ResponseData:
    def __init__(self, survey, selection, response_file, period):
        file_dict = create_file_paths(selection, response_file)
        self.response_list = ["Yes", "No"]
        self.survey = survey
        self.file = file_dict["selection"]
        self.q_codes1 = {'019': [678, 679]}
        self.q_codes2 = {'019': [11, 12]}
        self.q_codes3 = {'019': [681, 688, 689, 695, 696, 703, 704, 707, 708, 709, 710, 711, 712, 714, 715, 692, 693]}
        self.q_codes4 = {'019': [146]}
        self.refs = []
        self.data = []
        self.output = file_dict["response"]
        self.period = period

    def get_selection_list(self):
        with open(self.file, 'r') as selection_file:
            self.selection_file = selection_file.read()
        self.selection_file = self.selection_file.split('\n')

    def get_refs(self):
        del self.selection_file[-1]
        for row in self.selection_file:
            self.refs.append(row.split(':')[0])

    def generate_response_data(self):
#        self.resp_list = self.response_list
        for ref in self.refs:
            self.data1 = {i: str(random.randint(1, 2)) for i in self.q_codes1[self.survey]}
            data2 = {i: fake.date(pattern='%Y-%m-%d') for i in self.q_codes2[self.survey]}
            data3 = {i: str(random.randrange(0, 9999999999)) for i in self.q_codes3[self.survey]}
            data4 = {i: random.choice(self.response_list) for i in self.q_codes4[self.survey]}
            self.data1.update(data2)
            self.data1.update(data3)
            self.data1.update(data4)


            datum = {
                "submission": {
                    "case_id": fake.uuid4(),
                    "collection": {
                        "exercise_sid": fake.uuid4(),
                        "instrument_id": "0203",
                        "period": self.period
                    },
                    "data": self.data1,
                    "flushed": str(fake.boolean()),
                    "metadata": {
                        "ref_period_end_date": str(year + '-' + month + '-' + '25'),
                        "ref_period_start_date": str(year + '-' + month + '-' + '01'),
                        "ru_ref": ref,
                        "user_id": fake.user_name()
                    },
                    "origin": "uk.gov.ons.edc.eq",
                    "started_at": fake.iso8601(tzinfo=None, end_datetime=None),
                    "submitted_at": fake.iso8601(tzinfo=None, end_datetime=None),
                    "survey_id": self.survey,
                    "tx_id": fake.uuid4(),
                    "type": "uk.gov.ons.edc.eq:surveyresponse",
                    "version": "0.0.1"
                }
            }
            self.data.append(datum)

    def write_file(self):
        json_file = json.dumps(self.data, indent=4, separators=(',', ':'))
        with open(self.output, "w+") as output_file:
            output_file.write(json_file)


x = ResponseData(survey, selection_location, output_location, period)
x.get_selection_list()
x.get_refs()
x.generate_response_data()
x.write_file()