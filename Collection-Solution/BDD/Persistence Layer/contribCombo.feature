Feature: Find all records using a combination of fields
	Scenario: client makes a call to GET /contributor/getContributor/Reference=49900000001;Period=201801;Survey=076
		Given that the database contains records with Reference=49900000001;Period=201801;Survey=076
		When the client makes a GET call to /contributor/getContributor/Reference=49900000001;Period=201801;Survey=076
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response entity should contain "Reference" with value "49900000001"
		And the response entity should contain "Period" with value "201801"
		And the response entity should contain "Survey" with value "076"
		
	Scenario: client makes a call to GET /contributor/getContributor/Survey=076;Period=201801;Reference=49900000001
		Given that the database contains records with Survey=076;Period=201801;Reference=49900000001
		When the client makes a GET call to /contributor/getContributor/Survey=076;Period=201801;Reference=49900000001
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response entity should contain "Survey" with value "076"
		And the response entity should contain "Period" with value "201801"
		And the response entity should contain "Reference" with value "49900000001"
	
	Scenario: client makes a call to GET /contributor/getContributor/Reference=49900000001;Checkletter=L;RuSic=23630
		Given that the database contains records with Reference=49900000001;Checkletter=L;RuSic=23630
		When the client makes a GET call to /contributor/getContributor/Reference=49900000001;Checkletter=L;RuSic=23630
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response entity should contain "Reference" with value "49900000001"
		And the response entity should contain "Checkletter" with value "L"
		And the response entity should contain "RuSic" with value "23630"
		
	Scenario:  client makes a call to GET /contributor/getContributor/Reference=xxxxxxxxxxx;Checkletter=L;Survey=076
		When the client makes a GET call to /contributor/getContributor/Reference=xxxxxxxxxxx;Checkletter=L;Survey=076
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response should be an empty array
		
	Scenario:  client makes a call to GET /contributor/getContributor/Reference=00000000000;Checkletter=L;Survey=076
		When the client makes a GET call to /contributor/getContributor/Reference=00000000000;Checkletter=L;Survey=076
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response should be an empty array