# helloWord Business Layer

The business layer sits between the UI and JPA layers. This layer contains all the business logic and handles the communications between UI layer and JPA layer. This layer only contains two classes
1. business
2. Person

We go through each in turn.

## business

Contains methods that map to the corresponding URIs in the persistence layer

## Person

The Person class formats the names, removing the extraneous whitespace from the firstname and converting lastname to all lowercase, except for the first character which remains upper case.


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
12. The persistence layer should now be running on localhost:8090
