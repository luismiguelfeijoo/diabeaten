package com.diabeaten.edgeservice.controller.impl;

import com.diabeaten.edgeservice.controller.interfaces.IUserController;
import com.diabeaten.edgeservice.model.User;
import com.diabeaten.edgeservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements IUserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Override
    public UserDetails login(@AuthenticationPrincipal User user) {
        return userService.loadUserByUsername(user.getUsername());
    }
}
