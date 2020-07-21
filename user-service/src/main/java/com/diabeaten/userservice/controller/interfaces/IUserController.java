package com.diabeaten.userservice.controller.interfaces;

import com.diabeaten.userservice.controller.dto.NewUserDTO;
import com.diabeaten.userservice.model.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IUserController {
    // public User createUser(@RequestBody NewUserDTO newUser);
    public List<User> getUsers();
    public User getBy(String username, Long id);
}
