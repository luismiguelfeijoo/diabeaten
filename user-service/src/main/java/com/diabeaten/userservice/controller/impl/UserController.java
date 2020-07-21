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

    @GetMapping("/users/get-by")
    @Override
    public User getByUsername(@RequestParam(name = "username", required = false) String username, @RequestParam(name = "id", required = false) Long id) {
        if (username != null) {
            return userService.getByUsername(username);
        } else if (id != null) {
            return userService.getById(id);
        }
        return null;
    }

    // create new users

    @PostMapping("/users")
    @Override
    public User createUser(@RequestBody @Valid NewUserDTO newUser) {
        return userService.create(newUser);
    }
}
