package com.diabeaten.edgeservice.service;

import com.diabeaten.edgeservice.client.GlucoseBolusClient;
import com.diabeaten.edgeservice.client.InformationClient;
import com.diabeaten.edgeservice.client.UserClient;
import com.diabeaten.edgeservice.client.dto.*;
import com.diabeaten.edgeservice.enums.UserType;
import com.diabeaten.edgeservice.model.*;
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
    private GlucoseBolusClient glucoseBolusClient;

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
        User user = userClient.getBy(userToken, null, id);
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
        informationDTO.setDIA(newPatientDTO.getDIA());
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

    public List<Glucose> getGlucoseByUserId(Long id) {
        String gcToken = "Bearer " + jwtUtil.generateToken("glucose-bolus-service");
        return glucoseBolusClient.getByUserId(gcToken, id);
    }

    public Glucose addGlucose(Long id, GlucoseDTO glucoseDTO) {
        String gcToken = "Bearer " + jwtUtil.generateToken("glucose-bolus-service");
        glucoseDTO.setUserId(id);
        return glucoseBolusClient.create(gcToken, glucoseDTO);
    }

    public List<Bolus> getBolusByUserId(Long id) {
        String gcToken = "Bearer " + jwtUtil.generateToken("glucose-bolus-service");
        return glucoseBolusClient.getBy(gcToken, id, null);
    }

    public Bolus addBolus(Long id, BolusDTO bolusDTO) {
        String gcToken = "Bearer " + jwtUtil.generateToken("glucose-bolus-service");
        bolusDTO.setUserId(id);
        return glucoseBolusClient.create(gcToken, bolusDTO);
    }
}
