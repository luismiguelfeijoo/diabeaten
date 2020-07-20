package com.diabeaten.edgeservice.service;

import com.diabeaten.edgeservice.client.InformationClient;
import com.diabeaten.edgeservice.client.UserClient;
import com.diabeaten.edgeservice.client.dto.InformationDTO;
import com.diabeaten.edgeservice.client.dto.NewPatientDTO;
import com.diabeaten.edgeservice.client.dto.NewUserDTO;
import com.diabeaten.edgeservice.enums.UserType;
import com.diabeaten.edgeservice.model.Information;
import com.diabeaten.edgeservice.model.Patient;
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

    public Patient getById(Long id) {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        String informationToken = "Bearer " + jwtUtil.generateToken("information-service");
        User user = userClient.getBy(informationToken, null, id);
        Information userInformation = informationClient.getById(informationToken, id);
        Patient patient = new Patient();
        patient.setId(user.getId());
        patient.setUsername(user.getUsername());
        patient.setTotalBasal(userInformation.getTotalBasal());
        patient.setSensibilities(userInformation.getSensibilities());
        patient.setCarbRatios(userInformation.getCarbRatios());
        return patient;
    }

    public User create(NewPatientDTO newPatientDTO) {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        String informationToken = "Bearer " + jwtUtil.generateToken("information-service");
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername(newPatientDTO.getUsername());
        newUserDTO.setPassword(newPatientDTO.getPassword());
        newUserDTO.setType(UserType.PATIENT);
        User createdUser = userClient.createUser(userToken, newUserDTO);
        InformationDTO informationDTO = new InformationDTO();
        informationDTO.setTotalBasal(newPatientDTO.getTotalBasal());
        informationDTO.setSensibilities(newPatientDTO.getSensibilities());
        informationDTO.setRatios(newPatientDTO.getRatios());
        informationDTO.setUserId(createdUser.getId());
        informationClient.create(informationToken, informationDTO);
        // see what to return
        return createdUser;
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
