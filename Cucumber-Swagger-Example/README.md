# helloWord JPA/Persistence Layer

The persistence layer is what does the communication with the database, the layer is made up of four components:
1. Controller
2. Entity
3. Repository
4. NameSelectApp

We go through each in turn.

## Repository

The Repository holds our methods that will be used for interfacing with SQL Server. These methods sit inside an interface which inherits from Spring Boot's CrudRepoistory. In the present case the Repository
contains three methods:
1. findByNameID(String NameID)
2. findByFirstNameLike(String firstName)
3. findBySurNameLike(String surName)

## Entity

The Entity is the model of our database, it contains:
1. The full table name, including schema
2. The individual columns
3. As well as the data types and their lengths.

## Controller

The Controller handles the calls from the business layer and maps them to individual methods in defined in the Repository.

## NameSelectApp

The actual name of the NameSelectApp is arbitrary, it's just a java file that contains the main method. The placement of this file is important, it needs to be in the Java folder and no deeper.

## Operation of the Application
Once the Repository has been cloned, do the following:
1. Get the username and password from Keepass.
2. Add the environment variable "DATASOURCE_NAME", along with the corresponding username.
3. Add the environment variable "DATASOURCE_PASSWORD", along with the corresponding password.
4. Open inteliJ and import the project
5. Select "Import project from external model" and then choose Maven
6. Check the box "Search for projects recursively"
7. Keep clicking "Next"
8. To start up the servers, right click on the top level directory
9. Select "Run Maven Project"
10. Navigate to plugins
11. Then select "Run" under "Spring-boot-maven-plugin"
12. The persistence layer should now be running on localhost:8080
