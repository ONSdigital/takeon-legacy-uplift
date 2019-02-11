SQL Server Standards

1) All table names are singular
2) Camelcase is to be used for all tablenames (e.g. FirstSic) - this includes acronyms (e.g. SIC, FTE, etc).
3) Use Decimal instead of Integer (Discuss - what about BigInt instead?)
4) DateTimeOffset is to be used as the default date/time column data type
5) Natural keys are preferred but only if appropriate and unique.
6) Identity columns can be added but should only be used if a natural key is not defined for the table
7) Identity columns will consist of the table name followed by Id (e.g. Form -> FormId)
8) Identity columns are of type BigInt/Int (Decimal can cause clustered index issues)
9) All schema changes are to be versioned appropriately and have it identifiable in the script
10) All changes are to be run using the correct service account (the db user name)
11) All scripts are targeted at SQL Server v2014
12) Schemas have to be created before these scripts can be executed
13) Indentation of 4 characters per level(convert tabs to spaces)
