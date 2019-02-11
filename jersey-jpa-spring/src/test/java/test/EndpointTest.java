package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.web.util.UriComponentsBuilder;

import uk.gov.ons.collection.SpringBootDataJpaApplication;
import uk.gov.ons.collection.entity.Employee;
import uk.gov.ons.collection.controller.MainController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDataJpaApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndpointTest {

    private final String localhost = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MainController controller;

    @Test
    public void runBootShouldCheckIfBootIsRunning(){
        assert("test").equalsIgnoreCase("Test");
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port);

        assertThat(this.restTemplate.getForObject(builder.build().encode().toUri(),
                String.class)).contains("Test Insert");
    }

    @Test
    public void contextLoadsShouldCheckIfControllerIsRunning() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getEmployeesShouldReturnEmployeesWithSpecificName() throws InterruptedException {
        String empName = "Tom";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/findAll")
                .queryParam("empName", empName);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Employee[]> response = this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                entity, Employee[].class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Thread.sleep(2000L);

        Employee[] emps = response.getBody();

        Assert.assertNotEquals(0, emps.length);

    }
}