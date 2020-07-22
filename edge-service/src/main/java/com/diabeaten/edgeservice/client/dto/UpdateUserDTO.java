package com.diabeaten.edgeservice.client.dto;

import javax.validation.constraints.NotNull;

public class UpdateUserDTO {
    @NotNull
    private String name;
    @NotNull
    private String username;

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
