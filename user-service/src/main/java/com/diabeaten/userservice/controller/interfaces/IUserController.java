package com.diabeaten.userservice.controller.interfaces;

import com.diabeaten.userservice.model.User;

import java.util.List;

public interface IUserController {
    public List<User> getUsers();
    public User getByUsername(String username);
}
