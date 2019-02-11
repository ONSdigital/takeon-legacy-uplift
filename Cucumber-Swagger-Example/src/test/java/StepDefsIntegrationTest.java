package uk.gov.ons.collection.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import net.minidev.json.JSONObject;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.http.HttpStatus;

import com.jayway.jsonpath.JsonPath;
import cucumber.api.PendingException;

import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.And;
import net.minidev.json.JSONArray;


import static com.jayway.jsonassert.JsonAssert.with;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.ons.collection.test.SpringIntegrationTest;

import java.util.Map;

public class StepDefsIntegrationTest extends SpringIntegrationTest {

    private static final String HTTP_METHODS = "GET|POST|PUT|HEAD|DELETE|OPTIONS|PATCH|TRACE";
    private static final String COUNT_COMPARISON = "(?: (less than|more than|at least|at most))?";
    private static final String LOCAL_HOST = "http://localhost:";

    @LocalServerPort
    private int port;

    @When("^the client makes a (.+) call to (.+)$")
    public void the_client_makes_GET_call (String typeOfCall, String call) throws Throwable {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LOCAL_HOST + this.port);
            if (typeOfCall.equals("GET")) {
                executeGet(builder.build().encode().toUri() + call);
            }
            else if (typeOfCall.equals("POST")) {
                executePost();
            }
        }
        catch (ResourceAccessException ex) {
            ex.printStackTrace();
        }
    }

    @Then("^the client receives a valid JSON$")
    public void the_client_receives_a_valid_JSON() throws Throwable {
        assertThat(latestResponse.getBody(), isJson());
    }

    @Then("^the response should contain(?: (less than|more than|at least|at most|exactly))? (\\d+) entit(?:ies|y)$")
    public void the_response_should_n_entity(String compareOp, int noOfEntities) throws Throwable {
        assertThat(latestResponse.getBody(), isJson());
        JSONArray jsonArray = JsonPath.compile("$").read(new String(latestResponse.getBody()));
        if (compareOp.equals("at least"))
            assertThat(jsonArray.size() , greaterThanOrEqualTo(noOfEntities) );
        else if (compareOp.equals("at most"))
            assertThat(jsonArray.size() , lessThanOrEqualTo(noOfEntities) );
        else if  (compareOp.equals("less than"))
            assertThat(jsonArray.size() , lessThan(noOfEntities) );
        else if  (compareOp.equals("more than"))
            assertThat(jsonArray.size() , greaterThan(noOfEntities) );
        else if  (compareOp.equals("exactly"))
            assertThat(jsonArray.size() , equalTo(noOfEntities) );
    }

    @Then("^the response should contain \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void the_response_should_contain_with_value(String key, String value) throws Throwable {
        assertThat(latestResponse.getBody(), hasJsonPath("$."+key, equalTo(value)));
        with(latestResponse.getBody()).assertThat("$."+key,equalTo(value));
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @When("^the client makes a GET query to (.+) with \"([^\"]*)\" set to \"([^\"]*)\"$")
    public void the_client_calls_findByFirstNameLikeJSON_with_set_to(String call, String param, String value) throws Throwable {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LOCAL_HOST).port(port).query(call).queryParam(param, value);
            executeGet(builder.build().encode().toUri().toString());
        }
        catch (ResourceAccessException ex)
        {
            ex.printStackTrace();
        }
    }

    @Then("^the response entity at (\\d+) should contain \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void the_response_entity_at_should_contain_with_value(int location, String key, String value) throws Throwable {
        assertThat(latestResponse.getBody(), isJson());

        JSONArray jsonArray1 = JsonPath.compile("$").read(latestResponse.getBody());
        Map entity_at = (Map)jsonArray1.get(location);
        assertThat(entity_at.get(key), equalTo(value));
        //assertThat(entity_at, hasJsonPath("$."+key, equalTo(value))); // problems with escaping JSON

        with(latestResponse.getBody()).assertThat("["+location+"]."+key,equalTo(value));

        JsonNode json= new ObjectMapper().readTree(latestResponse.getBody());
        assertThat(json.get(location).get(key).textValue(), equalTo(value));
    }

    @Then("^the response should be an empty array$")
    public void the_response_should_be_an_empty_array() throws Throwable {
        //JSONArray jsonArray = JsonPath.compile("$").read(latestResponse.getBody());
        //assertThat(jsonArray.size(),equalTo(0));
        assertThat(latestResponse.getBody(), isJson(withJsonPath("$", hasSize(0))));
    }

}