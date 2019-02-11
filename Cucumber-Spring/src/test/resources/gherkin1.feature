Feature: the version can be retrieved
  Scenario: client makes call to GET /version
    When the client calls /version
    Then the client receives status code of 200
    And the client receives server version 1.0
  Scenario: client makes call to GET /getJSON
    When the client calls /getJSON
    Then the client receives status code of 200
    And the client receives server version {'firstName':'Mona', 'lastName':'Lisa'}
  Scenario: Client makes another kind of call to GET /getJSON
    When I retrieve JSON from the call to /getJSON
    Then the client receives status code of 200
    And The response should be:
      """
      {'firstName':'Mona', 'lastName':'Lisa'}
      """
    And The response should contain "Mona"
    And The response should contain "lastName" with value "Lisa"
    And The response should not contain "foo" with value "wee"
  Scenario: Client makes another kind of call to GET /getJSONArray
    When I retrieve JSON from the call to /getJSONArray
    Then the client receives status code of 200
    And The response should contain at least 2 entities

