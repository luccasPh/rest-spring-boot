package com.lucas.restspringboot.models.dto;

import java.io.Serializable;

import com.lucas.restspringboot.models.Person;

public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;

    private String lastName;

    private String address;

    private String gender;

    public PersonDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return String.format("Person[firstName='%s', lastName='%s', address='%s', gender='%s']",
                firstName,
                lastName,
                address, gender);
    }

    public Person toModel() {
        Person person = new Person();
        person.setFirstName(this.getFirstName());
        person.setLastName(this.getLastName());
        person.setAddress(this.getAddress());
        person.setGender(this.getGender());

        return person;
    }
}
