package com.diabeaten.edgeservice.service;

import com.diabeaten.edgeservice.client.GlucoseBolusClient;
import com.diabeaten.edgeservice.client.InformationClient;
import com.diabeaten.edgeservice.client.UserClient;
import com.diabeaten.edgeservice.client.dto.*;
import com.diabeaten.edgeservice.enums.UserType;
import com.diabeaten.edgeservice.exception.AccessNotAllowedException;
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
        return userClient.getPatients(userToken);
    }

    public Patient getById(User user, Long id) {
        if (!user.hasAccess(id)) throw new AccessNotAllowedException("You can't access this route");
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        String informationToken = "Bearer " + jwtUtil.generateToken("information-service");
        User foundUser = userClient.getPatientById(userToken, id);
        Information userInformation = informationClient.getById(informationToken, id);
        Patient patient = new Patient();
        patient.setId(foundUser.getId());
        patient.setUsername(foundUser.getUsername());
        patient.setTotalBasal(userInformation.getTotalBasal());
        patient.setSensibilities(userInformation.getSensibilities());
        patient.setCarbRatios(userInformation.getCarbRatios());
        patient.setDIA(userInformation.getDIA());
        return patient;
    }

    public User create(NewPatientDTO newPatientDTO) {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        String informationToken = "Bearer " + jwtUtil.generateToken("information-service");
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername(newPatientDTO.getUsername());
        newUserDTO.setPassword(newPatientDTO.getPassword());
        newUserDTO.setName(newPatientDTO.getName());
        User createdUser = userClient.createPatient(userToken, newUserDTO);
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

    /*
    public Information addInformation(InformationDTO informationDTO) {
        String informationToken = "Bearer " + jwtUtil.generateToken("information-service");
        System.out.println(informationDTO.getRatios().get(0).getEndHour());
        return informationClient.create(informationToken, informationDTO);
    }
     */

    public List<Glucose> getGlucoseByUserId(User user, Long id) {
        if (!user.hasAccess(id)) throw new AccessNotAllowedException("You can't access this route");
        String gcToken = "Bearer " + jwtUtil.generateToken("glucose-bolus-service");
        return glucoseBolusClient.getByUserId(gcToken, id);
    }

    public Glucose addGlucose(User user, Long id, GlucoseDTO glucoseDTO) {
        if (!user.hasAccess(id)) throw new AccessNotAllowedException("You can't access this route");
        String gcToken = "Bearer " + jwtUtil.generateToken("glucose-bolus-service");
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        User foundUser = userClient.getPatientById(userToken, id);
        glucoseDTO.setUserId(foundUser.getId());
        return glucoseBolusClient.create(gcToken, glucoseDTO);
    }

    public List<Bolus> getBolusByUserId(User user, Long id) {
        if (!user.hasAccess(id)) throw new AccessNotAllowedException("You can't access this route");
        String gcToken = "Bearer " + jwtUtil.generateToken("glucose-bolus-service");
        return glucoseBolusClient.getBy(gcToken, id, null);
    }

    public Bolus addBolus(User user, Long id, BolusDTO bolusDTO) {
        if (!user.hasAccess(id)) throw new AccessNotAllowedException("You can't access this route");
        String gcToken = "Bearer " + jwtUtil.generateToken("glucose-bolus-service");
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        User foundUser = userClient.getPatientById(userToken, id);
        bolusDTO.setUserId(foundUser.getId());
        return glucoseBolusClient.create(gcToken, bolusDTO);
    }

    public List<User> getAllUsers() {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        //filter by patients
        return userClient.getAll(userToken);
    }
}
