package uk.gov.ons.collection.entity;
/*
    Name: DoodleEntity.java

    Description: DoodleEntity.java provides the model for the table we're connecting to

*/
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.search.annotations.*;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
//The @Table decorator here takes the arguments for table DoodleEntity and the schema
//Spring Boot has an interesting property whereby it splits camel case lettering with an underscore
//This means that the schema as written in the database, CollectionTest, will internally be converted and used as
//Collection_Test

@Table(name = "doodle", schema = "collectiontest")
@Indexed
@ApiModel(value = "Doodle", description = "A Doodle entity")
public class DoodleEntity {

    //@Id points out where the id column is located
    @ApiModelProperty(value = "The primary key in the table for this entity")
    @Id
    //@Column points out columns, it takes arguments of the column DoodleEntity, length (if it's a string) and weather it nullable
    @Column(name = "doodle_id", nullable = false)
    private String nameID;

    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
    @Column(name = "firstname", length = 32, nullable = false)
    private String firstName;

    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
    @Column(name = "surname", length = 32, nullable = false)
    private String surName;

    //getter
    public String getNameID() {
        return nameID;
    }

    //setter
    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String setFullName(String firstName, String surName){
        return firstName + " " + surName;
    }

    //method to concatinate some strings together
    public String setReturnValues(String nameID, String firstName, String surName){
        return nameID + ", " + firstName + ", " + surName;
    }

    //override the built in Object.toString method
    @Override
    public String toString() {
        //return the string in the format "ID, FIRSTNAME, LASTNAME"
        return this.setReturnValues(this.nameID, this.firstName, this.surName);
    }
}
