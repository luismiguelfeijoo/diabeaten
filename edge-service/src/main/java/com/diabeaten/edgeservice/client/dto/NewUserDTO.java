package com.diabeaten.edgeservice.client.dto;

import com.diabeaten.edgeservice.enums.UserType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class NewUserDTO {
    @NotNull(message = "Username must be valid")
    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "You've provided an invalid email address")
    String username;
    @NotNull(message = "Password must be valid")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "You must provide a valid password")
    String password;
    @NotNull
    String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
