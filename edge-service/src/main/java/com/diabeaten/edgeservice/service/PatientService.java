package com.diabeaten.edgeservice.service;

import com.diabeaten.edgeservice.client.UserClient;
import com.diabeaten.edgeservice.client.dto.NewUserDTO;
import com.diabeaten.edgeservice.model.dto.User;
import com.diabeaten.edgeservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private UserClient userClient;

    @Autowired
    JwtUtil jwtUtil;


    public List<User> getAll() {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        //filter by patients
        return userClient.getAll(userToken);
    }

    public User getById(Long id) {
        // return userClient.getById(id)
        return null;
    }

    public User create(NewUserDTO newUserDTO) {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        return userClient.createUser(userToken, newUserDTO);
    }
}
