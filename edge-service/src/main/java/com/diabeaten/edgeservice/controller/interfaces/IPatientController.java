package com.diabeaten.edgeservice.controller.interfaces;

import com.diabeaten.edgeservice.client.dto.NewUserDTO;
import com.diabeaten.edgeservice.model.User;

import java.util.List;

public interface IPatientController {
    // Must be replaced with Patien when created
    public List<User> getAll();
    //public User getById(Long id);
    public User create(NewUserDTO newUserDTO);
}
