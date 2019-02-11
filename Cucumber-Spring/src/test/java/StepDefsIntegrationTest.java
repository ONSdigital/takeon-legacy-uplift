package uk.gov.ons.collection.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.jayway.jsonpath.JsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.*;
import net.minidev.json.JSONArray;

import org.springframework.http.HttpStatus;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import uk.gov.ons.collection.test.SpringIntegrationTest;

public class StepDefsIntegrationTest extends SpringIntegrationTest {

    private static final String HTTP_METHODS = "GET|POST|PUT|HEAD|DELETE|OPTIONS|PATCH|TRACE";
    private static final String COUNT_COMPARISON = "(?: (less than|more than|at least|at most))?";

    @When("^the client calls /version$")
    public void the_client_issues_GET_version() throws Throwable {
        executeGet("http://localhost:8080/version");
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @And("^the client receives server version (.+)$")
    public void the_client_receives_server_version_body(String version) throws Throwable {
        assertThat(latestResponse.getBody(), is(version));
    }

    @When("^the client calls /getJSON$")
    public void the_client_issues_GET_version2() throws Throwable {
        executeGet("http://localhost:8080/getJSON");
    }

    @Then("^I retrieve JSON from the call to (.+)")
    public void the_client_issues_GET_insecure(String call) throws Throwable {
        executeGet("http://localhost:8080"+call);
    }

    @And("^The response should be(?:[:])?$")
    public void the_client_receives_json(String jsonString) throws Throwable {
        assertThat(latestResponse.getBody(), is(jsonString));
    }

    @And("^The response should contain \"([^\"]*)\"$")
    public void the_client_receives_json_contains(String value) throws Throwable {
        assertThat(latestResponse.getBody(), containsString(value));
    }

    // And The response should contain "foo" with value "bar"
    @And("^The response should contain \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void the_client_receives_json_with_key_and_value(String key, String value) throws Throwable {
        assertThat(latestResponse.getBody(), isJson());
        assertThat(latestResponse.getBody(), hasJsonPath("$."+key, equalTo(value)));
    }

    // And The response should not contain "foo" with value "wee"
    @And("^The response should not contain \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void the_client_receives_json_without_key_and_value(String key, String value) throws Throwable {
        assertThat(latestResponse.getBody(), isJson());
        assertThat(latestResponse.getBody(), not(hasJsonPath("$."+key,equalTo(value))));
    }

    // And The response should contain at least 2 entities
    @And("^The response should contain(?: (less than|more than|at least|at most))? (\\d+) entit(?:ies|y)$")
    public void the_client_receives_json_with_number_of_entities(String compareOp, int noOfEntities) throws Throwable {
        assertThat(latestResponse.getBody(), isJson());
        JSONArray jsonArray = JsonPath.compile("$").read(new String(latestResponse.getBody()));
        assertThat(jsonArray.size() , equalTo(noOfEntities) );
    }

}