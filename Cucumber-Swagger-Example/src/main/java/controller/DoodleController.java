package uk.gov.ons.collection.controller;

/*
    Name: mainControl.java

    Description: mainControl.java implements the methods that were defined in DoodleRepo. On top of this
                 it also provides our web interface.

*/

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

//Declare the class as a controller class
@Api(value = "Doodle Entity", description = "offers CRUD operations for the Doodle entity")
@RestController
@RequestMapping(value = "/doodles")
public class DoodleController {

    //Use the autowired annotation. This automatically passes the values to the repo
    @Autowired
    //Create the object
    private uk.gov.ons.collection.repository.DoodleRepo DoodleRepo;
    //Bind the return to the current webpage
    @ApiOperation(value = "Get list of links", response = String.class)
    @GetMapping("/help")
    public String apiHelp(){
        String outputString ="";
        outputString += "<ul>";
        outputString += "<li><a href='/doodles/findBySurNameLike'>Find by surname -> returns JSON!</a></li>";
        outputString += "<li><a href='/doodles/findByFirstNameLike'>Find by first name -> returns JSON!</a></li>";
        outputString += "<li><a href='/doodles/findAll'>Select all -> returns JSON!</a></li>";
        return outputString;
    }

    //the additional /{firstName} allows us to specify an arbitrary part of the URI
    @ApiOperation(value = "Find a doodle by passing {firstName} which allows us to specify an arbitrary part of the URI", response = Iterable.class, notes = "The parameter is required")
    @GetMapping(value = "/findByFirstNameLike/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<uk.gov.ons.collection.entity.DoodleEntity> findbyFirstName(@PathVariable("firstName") @ApiParam(name = "firstName", value = "The first name you want to find", type = "String", required = true, defaultValue = "Nidal") String firstName){
        String searchBy = "%" + firstName + "%";
        Iterable<uk.gov.ons.collection.entity.DoodleEntity> doodleEntities = this.DoodleRepo.findByFirstNameLike(searchBy);
        return doodleEntities;
    }

    @GetMapping(value = "/findBySurNameLike/{surName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<uk.gov.ons.collection.entity.DoodleEntity> findBySurNameLike(@PathVariable("surName") String surName){
        String searchBy = "%" + surName + "%";
        Iterable<uk.gov.ons.collection.entity.DoodleEntity> doodleEntities = this.DoodleRepo.findBySurNameLike(searchBy);
        return doodleEntities;
    }


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public  Iterable<uk.gov.ons.collection.entity.DoodleEntity> getAllJSON(){
        return this.DoodleRepo.findAll();
    }

    // http://localhost:8080/doodles/getRec/ref=A1A;period=9;survey=964
    @GetMapping("/getRec/{pk}")
    public String osDetails(@MatrixVariable String ref,
                            @MatrixVariable String period,
                            @MatrixVariable String survey
    ) {

        return ref + " " + period + " " + survey;
    }


    // http://localhost:8080/doodles/PK/ref=A1A;period=9;survey=964
    @GetMapping(value = "/PK/{pk}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getSearch(@MatrixVariable Map<String, String> matrixVars) {
        //Map.Entry<String,String> entry = matrixVars.entrySet().iterator().next();
        //return "hello "+entry.getValue();

        return new ResponseEntity<>(matrixVars, HttpStatus.OK);
    }


    // Get a Single Note
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public uk.gov.ons.collection.entity.DoodleEntity getDoodleById(@PathVariable(value = "id") String noteId) {
        return DoodleRepo.findById(noteId).orElseThrow(() -> new IllegalArgumentException("Doodle not found"));
    }


}


