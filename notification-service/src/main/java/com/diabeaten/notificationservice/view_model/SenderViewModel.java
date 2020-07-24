package com.diabeaten.notificationservice.view_model;

public class SenderViewModel {
    private String name;
    private String username;

    public SenderViewModel(String name, String username) {
        setName(name);
        setUsername(username);
    }

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
}
