package com.tech.tastykingdom.Models;

public class TableBoking {

    String id;
    String name;
    String contact;
    String idNo;
    String noOfPerson;
    String date;

    public TableBoking() {
    }

    public TableBoking(String id, String name, String contact, String idNo, String noOfPerson, String date) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.idNo = idNo;
        this.noOfPerson = noOfPerson;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getNoOfPerson() {
        return noOfPerson;
    }

    public void setNoOfPerson(String noOfPerson) {
        this.noOfPerson = noOfPerson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
