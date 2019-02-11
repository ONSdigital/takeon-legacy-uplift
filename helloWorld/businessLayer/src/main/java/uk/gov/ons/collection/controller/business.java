package uk.gov.ons.collection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uk.gov.ons.collection.service.PersonService;

@RestController
@RequestMapping(value = "/Person")
public class business{

    @Autowired
    PersonService personService;

    private final String homeApiString;

    public business(){
        this.homeApiString = "<ul>"
                + "<li><a href='/Person/returnAll'>Get all persons</a></li>"
                + "<li><a href='/Person/FindPersonByFirstName'>Find persons by name</a></li>";
    }

    @GetMapping("/")
    public String home(){
        return homeApiString;
    }

    @GetMapping("/help")
    public String help(){
        return homeApiString;
    }

    //As above, but addition of params = "surName" lets us add a URI Parameter in the form /URI?surName=someName
    //Produces = {"application/json"} takes a Java iterable and outputs something in JSON format
    @GetMapping(value="/returnAll")
     public String printJSON(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/doodles/findAll", String.class);
    }

    @GetMapping(value="/FindPersonByFirstName/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable findByFirstNameLikeJSON(@PathVariable("firstName") String firstName){
        return personService.findByFirstNameLikeJSON(firstName);
    }

}