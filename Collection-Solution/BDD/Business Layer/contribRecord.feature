Feature: Find a record by Reference,Period,Survey
	Scenario: client makes a call to GET /respondent/getRespondent/Reference=49900000000;Period=201801;Survey=076
		Given that the database contains a record with Reference=49900000000;Period=201801;Survey=076
		When the client makes a GET call to /respondent/getRespondent/Reference=49900000000;Period=201801;Survey=076
		Then the client receives status code of 200 
		And the client receives a valid JSON
		And the response entity at 0 should contain "Reference" with value "49900000000"
		And the response entity at 0 should contain "Period" with value "201801"
		And the response entity at 0 should contain "Survey" with value "076"
		
	Scenario: client makes a call to GET /respondent/getRespondent/Reference=499xxxxxxxx;Period=201801;Survey=076
		Given that the database does not contain a record with Reference=499xxxxxxxx;Period=201801;Survey=076
		When the client makes a GET call to /contributor/getContributor/Reference=499xxxxxxxx;Period=201801;Survey=076
		Then the client receives status code of 200 
		And the client receives a valid JSON
		And the response entity at 0 should be an empty array