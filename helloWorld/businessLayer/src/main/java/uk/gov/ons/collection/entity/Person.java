package uk.gov.ons.collection.entity;

public class Person{
    private final Integer id;
    private final String firstname;
    private final String surname;

    public Person(Integer id, String firstname, String surname) {
        this.id = id;
        this.firstname = (firstname == null) ? "" : firstname; // Assign empty string in case of a null
        this.surname = (surname == null) ? "" : surname;       // Assign empty string in case of a null
    }

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname(){
        return surname;
    }

    private String formatName(String name){
        if (name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getFormattedFirstname(){
        return formatName(firstname.trim());
    }

    public String getFormattedLastname(){
        return formatName(surname.trim());
    }

    @Override
    public String toString() {
        return this.id.toString() + " " + this.firstname + " " + this.surname;
    }
}
