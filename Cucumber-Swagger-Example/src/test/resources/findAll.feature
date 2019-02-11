Feature: All records are retrieved
	Scenario: client makes a call to GET /doodles/
		When the client makes a GET call to /doodles/
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response should contain at least 1 entity

	Scenario: client makes a call to GET /doodles/findAll1
		When the client makes a GET call to /doodles/findAll1
		Then the client receives status code of 500
		And the client receives a valid JSON
		And the response should contain "error" with value "Internal Server Error"
