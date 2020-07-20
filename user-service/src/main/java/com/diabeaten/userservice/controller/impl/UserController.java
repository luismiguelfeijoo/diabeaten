package com.diabeaten.userservice.controller.impl;

import com.diabeaten.userservice.controller.dto.NewUserDTO;
import com.diabeaten.userservice.controller.interfaces.IUserController;
import com.diabeaten.userservice.model.User;
import com.diabeaten.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController implements IUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @Override
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/{username}")
    @Override
    public User getByUsername(@PathVariable(name = "username") String username) {
        return userService.getByUsername(username);
    }

    // create new users

    @PostMapping("/users")
    @Override
    public User createUser(@RequestBody @Valid NewUserDTO newUser) {
        return userService.create(newUser);
    }
}
