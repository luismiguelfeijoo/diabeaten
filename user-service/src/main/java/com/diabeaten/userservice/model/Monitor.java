package com.diabeaten.userservice.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Monitor extends User {
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    private Patient patient;

    public Monitor() {
    }

    public Monitor(String username, String password, String name) {
        super(username, password);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
