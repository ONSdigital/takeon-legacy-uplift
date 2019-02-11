Feature: All respondent records are retrieved
	Scenario: client makes a call to GET /respondent/
	    When the client makes a GET call to /respondent/
		Then the client receives status code of 200
		And the client receives a valid JSON array
		And the response should contain at least 1 entity
		And the response entity at 0 should contain the path "Reference" 
				
	Scenario: the client makes a call to GET /respondent//
		When the client makes a GET call to /respondent//
		Then the client receives status code of 404
		And the client receives a valid JSON
		And the response should contain "error" with value "Not Found"