Feature: Find record by First Name
	Scenario: client makes a call to GET /doodles/findByFirstNameLike
		When the client makes a GET call to /doodles/findByFirstNameLike
		Then the client receives status code of 500

	Scenario: client makes a call to GET /doodles/findByFirstNameLike/Jane
		When the client makes a GET call to /doodles/findByFirstNameLike/Jane
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response entity at 0 should contain "firstName" with value "Jane"

	Scenario: client makes a call to GET /doodles/findByFirstNameLike/xyz
		When the client makes a GET call to /doodles/findByFirstNameLike/xyz
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response should be an empty array

	Scenario: client makes a call to GET /doodles/findByFirstNameLike/111
		When the client makes a GET call to /doodles/findByFirstNameLike/111
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response should be an empty array
