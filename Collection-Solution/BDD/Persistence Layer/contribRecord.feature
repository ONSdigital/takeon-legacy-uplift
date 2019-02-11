Feature: Find a record by Reference,Period,Survey
	Scenario: client makes a call to GET /contributor/getContributor/Reference=49900000000;period=201801;survey=076
		Given that the database contains a record with Reference=49900000000;period=201801;survey=076
		When the client makes a GET call to /contributor/getContributor/Reference=49900000000;period=201801;survey=076
		Then the client receives status code of 200 
		And the client receives a valid JSON
		And the response entity at 0 should contain "Reference" with value "49900000000"
		And the response entity at 0 should contain "period" with value "201801"
		And the response entity at 0 should contain "survey" with value "076"
		
	Scenario: client makes a call to GET /contributor/getContributor/Reference=499xxxxxxxx;period=201801;survey=076
		Given that the database does not contain a record with Reference=499xxxxxxxx;period=201801;survey=076
		When the client makes a GET call to /contributor/getContributor/Reference=499xxxxxxxx;period=201801;survey=076
		Then the client receives status code of 200 
		And the client receives a valid JSON
		And the response entity at 0 should be an empty array
		
	Scenario: client makes a call to GET /contributor/getContributor/Reference=49900000000;period=201813;survey=076
		Given that the database does not contain a record with Reference=49900000000;period=201813;survey=076
		When the client makes a GET call to /contributor/getContributor/Reference=49900000000;period=201813;survey=076
		Then the client receives status code of 200 
		And the client receives a valid JSON
		And the response entity at 0 should be an empty array