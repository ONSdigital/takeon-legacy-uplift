package springTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import uk.gov.ons.collection.entity.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Person class tests")
class personTest {

    @ParameterizedTest
    @DisplayName("Should remove leading spaces from formatted names")
    @ValueSource( strings = {  "   Name  ","       Name     ","         Name"})
    void removeLeadingSpaces(String name){
        Person testPerson = new Person(1, name, name);
        assertAll("person",
                () -> assertEquals("Name", testPerson.getFormattedFirstname()),
                () -> assertEquals("Name", testPerson.getFormattedLastname()));
    }

    @ParameterizedTest
    @DisplayName("Should remove trailing spaces from formatted names")
    @ValueSource( strings = {  "Name","Name     ","Name       "})
    void removeTrailingSpaces(String name){
        Person testPerson = new Person(1, name, name);
        assertAll("person",
                () -> assertEquals("Name", testPerson.getFormattedFirstname()),
                () -> assertEquals("Name", testPerson.getFormattedLastname()));
    }

    @ParameterizedTest
    @DisplayName("Should remove trailing/leading tab characters from formatted names")
    @ValueSource( strings = {  "\tName\t","\t\tName\t","\t\t\t\t\t\t\tName\t\t\t\t\t\t\t\t\t\t\t"})
    void removeTabs(String name){
        Person testPerson = new Person(1, name, name);
        assertAll("person",
                () -> assertEquals("Name", testPerson.getFormattedFirstname()),
                () -> assertEquals("Name", testPerson.getFormattedLastname()));
    }

    @ParameterizedTest
    @DisplayName("Should remove trailing/leading newline characters from formatted names")
    @ValueSource( strings = {  "\nName\n","\rName\r","\n\rName\r\n"})
    void removeNewlines(String name){
        Person testPerson = new Person(1, name, name);
        assertAll("person",
                () -> assertEquals("Name", testPerson.getFormattedFirstname()),
                () -> assertEquals("Name", testPerson.getFormattedLastname()));
    }

    @Test
    @DisplayName("Should handle formatting blank names")
    void blankNames(){
        Person testPerson = new Person(1, "", "");
        assertAll("person",
                () -> assertEquals("", testPerson.getFormattedFirstname()),
                () -> assertEquals("", testPerson.getFormattedLastname()));
    }

    @Test
    @DisplayName("Should handle null names as blank")
    void nullNames(){
        Person testPerson = new Person(1, null, null);
        assertAll("person",
                () -> assertEquals("", testPerson.getFormattedFirstname()),
                () -> assertEquals("", testPerson.getFormattedLastname()));
    }

    @ParameterizedTest
    @DisplayName("Formatted names should have the first character uppercase, all others lower")
    @ValueSource( strings = {  "name","NAME","nAME","nAmE"})
    void formattedCapitaliseFirstCharacterOnly(String name){
        Person testPerson = new Person(1, name, name);
        assertAll("person",
                () -> assertEquals("Name", testPerson.getFormattedFirstname()),
                () -> assertEquals("Name", testPerson.getFormattedLastname()));
    }

    @Test
    @DisplayName("Formatted names should have the first character uppercase, all others lower")
    void formattedHandlesMiddleSpaces(){
        Person testPerson = new Person(1, "N ame", "N ame");
        assertAll("person",
                () -> assertEquals("N ame", testPerson.getFormattedFirstname()),
                () -> assertEquals("N ame", testPerson.getFormattedLastname()));
    }

}
