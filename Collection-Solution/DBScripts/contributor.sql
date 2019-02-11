USE [CollectionDev]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* Create Schema dev01 */
ALTER USER [collection_user] With DEFAULT_SCHEMA = dev01;

IF OBJECT_ID('Contributor', 'U') IS NOT NULL
    DROP TABLE Contributor;

CREATE TABLE Contributor
(
    Reference                   Char(11) NOT NULL,              -- ruref
    Period                      Char(6) NOT NULL,               -- N/A [Derived from finalsel filename]
    Survey                      Char(3) NOT NULL,               -- N/A [Derived from finalsel filename]
    FormID                      BigInt NOT NULL,                -- Derived [FK -> Form Table]
    Status                      Varchar(20) NOT NULL,           -- Derived [Determined when contributor saved]
    ReceiptDate                 DatetimeOffset(7),              -- Derived [Determined when form first saved]    
    LockedBy                    Varchar(16) NOT NULL,           -- Derived [Determined when form is viewed]
    LockedDate                  DatetimeOffset(7),              -- Derived [Determined when form is viewed]
    FormType                    Char(4) NOT NULL,               -- formtype
    Checkletter                 Char(1) NOT NULL,               -- checkletter
    FrozenSicOutdated           Char(5) NOT NULL,               -- frosic2003
    RuSicOutdated               Char(5) NOT NULL,               -- rusic2003
    FrozenSic                   Char(5) NOT NULL,               -- frosic2007
    RuSic                       Char(5) NOT NULL,               -- rusic2007
    FrozenEmployees             Decimal(13,0) NOT NULL,         -- froempees
    Employees                   Decimal(13,0) NOT NULL,         -- employees
    FrozenEmployment            Decimal(13,0) NOT NULL,         -- froempment
    Employment                  Decimal(13,0) NOT NULL,         -- employment
    FrozenFteEmployment         Decimal(10,3) NOT NULL,         -- frofteempt
    FteEmployment               Decimal(10,3) NOT NULL,         -- fteempt
    FrozenTurnover              Decimal(13,0) NOT NULL,         -- frotover
    Turnover                    Decimal(13,0) NOT NULL,         -- turnover
    EnterpriseReference         Char(10) NOT NULL,              -- entref
    WowEnterpriseReference      Varchar(10) NOT NULL,           -- wowentref
    CellNumber                  SmallInt NOT NULL,              -- cell_no    
    Currency                    Char(1) NOT NULL,               -- currency
    VatReference                Varchar(12) NOT NULL,           -- vatref & checkdigit
    PayeReference               Varchar(13) NOT NULL,           -- payeref
    CompanyRegistrationNumber   Varchar(8) NOT NULL,            -- crn
    NumberLiveLocalUnits        Decimal(6,0) NOT NULL,          -- live_lu
    NumberLiveVat               Decimal(6,0) NOT NULL,          -- live_vat
    NumberLivePaye              Decimal(6,0) NOT NULL,          -- live_paye
    LegalStatus                 Char(1) NOT NULL,               -- legalstatus
    ReportingUnitMarker         Char(1) NOT NULL,               -- entrepmkr
    Region                      Char(2) NOT NULL,               -- region
    BirthDate                   Varchar(16) NULL,               -- birthdate
    EnterpriseName              Varchar(107) NOT NULL,          -- entname1 + entname2 + entname3
    ReferenceName               Varchar(107) NOT NULL,          -- runame1 + runame2 + runame3
    ReferenceAddress            Varchar(154) NOT NULL,          -- ruaddr1 + ruaddr2 + ruaddr3 + ruaddr4 + ruaddr5
    ReferencePostcode           Varchar(8) NOT NULL,            -- rupostcode
    TradingStyle                Varchar(107) NOT NULL,          -- tradstyle1 + tradstyle2 + tradstyle3
    Contact                     Varchar(30) NOT NULL,           -- contact
    Telephone                   Varchar(20) NOT NULL,           -- telephone
    Fax                         Varchar(20) NOT NULL,           -- fax
    SelectionType               Char(1) NOT NULL,               -- seltype
    InclusionExclusion          Char(1) NOT NULL,               -- inclexcl
    CreatedBy                   Varchar(16) NOT NULL,           -- Derived [Determined when selection loaded]
    CreatedDate                 DatetimeOffset(7) NOT NULL,     -- Derived [Determined when selection loaded]
    LastUpdatedBy               Varchar(16),                    -- Derived [Determined when form/contributor updated]
    LastUpdatedDate             DatetimeOffset(7),              -- Derived [Determined when form/contributor updated]

    CONSTRAINT PK_contributor PRIMARY KEY CLUSTERED (reference, period, survey)
)


-- CORA NOTES
-- Period_Contributor_Id    (ID column) - Support pain.
-- Inquiry_Id               (ID column) - Support pain. Using survey code instead.
-- Form_Version_Id          (ID column) - Replaced with Form_Id (No natural key exists for forms)
-- Period_Year              Can be derived from period
-- Period_Month             Can be derived from period

-- Non_Responded_Periods    Derived counter.
-- Manager_Checked          Derived flag. Set by business area to confirm form details
-- Newly_Selected           Derived attribute based on business formula (True/False)
-- Form_Value               Determine value/worth/impact of form to allow business area to prioritise
-- First_Time_Failures      Used by MD for survey analysis
-- Turnover                 Used to be a mapping to the survey choice of IDBR turnover field
-- Employee_Count           Used to be a mapping to the survey choice of IDBR employment field
-- Cell_Number              User allocated cell/stratum (used when IDBR selection cell were broken do
-- Contributor_Source       Identify how contributor was added to selection (finalsel, manually added, etc)
-- Data_Source              Identify how data was returned ('manual', SDC, SPC, Seft, etc)

-- IDBR attributes have been renamed slightly in attempt to be clearer (and combined if appropriate)
-- i.e. 5 x Address_Line -> 1 Address attribute