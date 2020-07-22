package com.diabeaten.edgeservice.controller.interfaces;

import com.diabeaten.edgeservice.client.dto.*;
import com.diabeaten.edgeservice.model.Bolus;
import com.diabeaten.edgeservice.model.Glucose;
import com.diabeaten.edgeservice.model.Patient;
import com.diabeaten.edgeservice.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IPatientController {
    // Must be replaced with Patien when created
    public List<User> getAll();
    public Patient getById(User user, Long id);
    public User create(NewPatientDTO newPatientDTO);
    public List<Glucose> getGlucose(User user, Long userId);
    public Glucose addGlucose(User user, Long userId, GlucoseDTO glucoseDTO);
    public List<Bolus> getBolus(User user, Long userId);
    public Bolus addBolus(User user, Long userId, BolusDTO bolusDTO);
    public Patient update(User user, Long id, UpdatePatientDTO updatePatientDTO );
}
