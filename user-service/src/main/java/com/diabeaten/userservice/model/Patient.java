package com.diabeaten.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Patient extends User {
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    private List<Monitor> monitors;

    public Patient() {
    }

    public Patient(String username, String password, String name) {
        super(username, password);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
    }
}
