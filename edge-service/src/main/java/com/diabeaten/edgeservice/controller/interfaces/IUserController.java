package com.diabeaten.edgeservice.controller.interfaces;

import com.diabeaten.edgeservice.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserController {
    public UserDetails login(User user);
}
