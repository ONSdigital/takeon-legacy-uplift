package uk.gov.ons.collection.controller;

/*
    Name: mainControl.java

    Description: mainControl.java implements the methods that were defined in DoodleRepo. On top of this
                 it also provides our web interface.

*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

// Declare the class as a controller class
@RestController
@RequestMapping(value = "/doodles")
public class DoodleController {

    // Use the autowired annotation. This automatically passes the values to the repo
    @Autowired
    //Create the object
    private uk.gov.ons.collection.repository.DoodleRepo DoodleRepo;
    // Bind the return to the current webpage
    @RequestMapping("/help")
    public String home(){
        String outputString ="";
        outputString += "<ul>";
        outputString += "<li><a href='/doodles/findBySurNameLike'>Find by surname -> returns JSON!</a></li>";
        outputString += "<li><a href='/doodles/findByFirstNameLike'>Find by first name -> returns JSON!</a></li>";
        outputString += "<li><a href='/doodles/findAll'>Select all -> returns JSON!</a></li>";
        return outputString;
    }

    //the additional /{firstName} allows us to specify an arbitrary part of the URI
    @GetMapping(value = "/findByFirstNameLike/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<uk.gov.ons.collection.entity.DoodleEntity> findbyFirstName(@PathVariable("firstName") String firstName){
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


    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public  Iterable<uk.gov.ons.collection.entity.DoodleEntity> getAllJSON(){
        Iterable<uk.gov.ons.collection.entity.DoodleEntity> doodleEntities = this.DoodleRepo.findAll();
        return doodleEntities;

    }

}


