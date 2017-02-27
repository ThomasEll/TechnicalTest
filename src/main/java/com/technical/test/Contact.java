package com.technical.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Tom Longdon
 */

@Entity
public class Contact {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String telephoneNumber;
    private String address1;
    private String address2;
    private String city;
    private String postCode;

    protected Contact(){
    }

    public Contact(String firstName, String lastName, String emailAddress, String telephoneNumber, String address1,
                   String address2, String city, String postCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.telephoneNumber = telephoneNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postCode = postCode;
    }

    public Long getId() {
        return id;
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

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString(){
        return String.format("Contact[ID = %d, First Name = '%s', Last Name = '%s', Email Address = '%s', " +
                "Telephone Number = '%s', Address Line 1 = '%s', Address Line 2 = '%s', City = '%s', Post Code = '%s'",
                id, firstName, lastName, emailAddress, telephoneNumber, address1, address2, city, postCode);
    }
}
