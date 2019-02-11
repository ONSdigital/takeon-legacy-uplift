Feature: Find record by Surname
	Scenario: client makes a call to GET /doodles/findBySurNameLike
		When the client makes a GET call to /doodles/findBySurNameLike
		Then the client receives status code of 500

	Scenario: client makes a call to GET /doodles/findBySurNameLike/Nicholson
		When the client makes a GET call to /doodles/findBySurNameLike/Nicholson
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response entity at 0 should contain "surName" with value "Nicholson"

	Scenario: client makes a call to GET /doodles/findBySurNameLike/xyz
		When the client makes a GET call to /doodles/findBySurNameLike/xyz
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response should be an empty array

	Scenario: client makes a call to GET /doodles/findBySurNameLike/111
		When the client makes a GET call to /doodles/findBySurNameLike/111
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response should be an empty array
