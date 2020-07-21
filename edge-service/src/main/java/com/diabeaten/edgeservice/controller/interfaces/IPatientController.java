package com.diabeaten.edgeservice.controller.interfaces;

import com.diabeaten.edgeservice.client.dto.BolusDTO;
import com.diabeaten.edgeservice.client.dto.GlucoseDTO;
import com.diabeaten.edgeservice.client.dto.NewPatientDTO;
import com.diabeaten.edgeservice.client.dto.NewUserDTO;
import com.diabeaten.edgeservice.model.Bolus;
import com.diabeaten.edgeservice.model.Glucose;
import com.diabeaten.edgeservice.model.Patient;
import com.diabeaten.edgeservice.model.User;

import java.util.List;

public interface IPatientController {
    // Must be replaced with Patien when created
    public List<User> getAll();
    public Patient getById(Long id);
    public User create(NewPatientDTO newPatientDTO);
    public List<Glucose> getGlucose(Long userId);
    public Glucose addGlucose(Long userId, GlucoseDTO glucoseDTO);
    public List<Bolus> getBolus(Long userId);
    public Bolus addBolus(Long userId, BolusDTO bolusDTO);
}
