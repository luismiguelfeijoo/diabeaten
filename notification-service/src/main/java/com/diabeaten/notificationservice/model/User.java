package com.diabeaten.notificationservice.model;

import java.util.List;

public class User {
    private String name;
    private String username;
    private Long id;
    private List<User> monitors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<User> monitors) {
        this.monitors = monitors;
    }
}
