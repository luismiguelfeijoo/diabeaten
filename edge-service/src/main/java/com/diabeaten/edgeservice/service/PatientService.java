package com.diabeaten.edgeservice.service;

import com.diabeaten.edgeservice.client.InformationClient;
import com.diabeaten.edgeservice.client.UserClient;
import com.diabeaten.edgeservice.client.dto.InformationDTO;
import com.diabeaten.edgeservice.client.dto.NewUserDTO;
import com.diabeaten.edgeservice.model.Information;
import com.diabeaten.edgeservice.model.User;
import com.diabeaten.edgeservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private InformationClient informationClient;

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

    public Information addInformation(InformationDTO informationDTO) {
        String informationToken = "Bearer " + jwtUtil.generateToken("information-service");
        System.out.println(informationDTO.getRatios().get(0).getEndHour());
        return informationClient.create(informationToken, informationDTO);
    }
}
