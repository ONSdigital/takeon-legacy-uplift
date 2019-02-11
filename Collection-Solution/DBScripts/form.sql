USE [CollectionDev]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* Create Schema dev01 */
ALTER USER [collection_user] With DEFAULT_SCHEMA = dev01;

IF OBJECT_ID('Form', 'U') IS NOT NULL
    DROP TABLE Form;
    
CREATE TABLE [CollectionDev].[dev01].Form
(
    FormID              Integer IDENTITY(1,1) NOT NULL,
    Survey              Char(3) NOT NULL,
    Description         Varchar(128) NOT NULL,
    PeriodStart         Char(6) NOT NULL,
    PeriodEnd           Char(6) NOT NULL,
    CreatedBy           Varchar(16) NOT NULL,
    CreatedDate         DatetimeOffset(7) NOT NULL,
    LastUpdatedBy       Varchar(16) NOT NULL,
    LastUpdatedDate     DatetimeOffset(7) NOT NULL,

    CONSTRAINT PK_Form PRIMARY KEY CLUSTERED (FormID)
)


CREATE TABLE [CollectionDev].[dev01].FormDefinition
(
    FormID                  Integer NOT NULL,           -- Foreign key from Form table. Identify unique form.
    QuestionCode            Char(4) NOT NULL,           -- e.g. 0401
    DisplayQuestionNumber   Varchar(16) Not Null,       -- e.g. 14.a
    DisplayText             Varchar(128) Not Null,      -- e.g. How many fish did you sell?
    Type                    Varchar(16) NOT NULL,       -- e.g. Numeric/Date/Text/Lookup/etc.
    CreatedBy               Varchar(16) NOT NULL,       -- Derived [Determined when form initially saved/created]
    CreatedDate             DatetimeOffset(7) NOT NULL, -- Derived [Determined when form initially saved/created]
    LastUpdatedBy           Varchar(16) NOT NULL,       -- Derived [Determined when form updated]
    LastUpdatedDate         DatetimeOffset(7) NOT NULL, -- Derived [Determined when form updated]

    CONSTRAINT PK_FormDefinition PRIMARY KEY CLUSTERED (FormID, QuestionCode),
    
    CONSTRAINT     FK_FormDefinition_Form FOREIGN KEY (FormID)
        REFERENCES [CollectionDev].[dev01].Form (FormID)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)