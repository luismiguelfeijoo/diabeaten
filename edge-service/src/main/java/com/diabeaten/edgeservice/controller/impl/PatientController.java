package com.diabeaten.edgeservice.controller.impl;

import com.diabeaten.edgeservice.client.dto.*;
import com.diabeaten.edgeservice.controller.interfaces.IPatientController;
import com.diabeaten.edgeservice.model.*;
import com.diabeaten.edgeservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PatientController implements IPatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return patientService.getAllUsers();
    }

    @GetMapping("/patients")
    @Override
    public List<User> getAll() {
        return patientService.getAll();
    }

    @PostMapping("/patients")
    @Override
    public User create(@RequestBody @Valid NewPatientDTO newPatientDTO) {
        return patientService.create(newPatientDTO);
    }

    @GetMapping("/patients/{id}")
    @Override
    public Patient getById(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long id ) {
        return patientService.getById(user, id);
    }

    @DeleteMapping("/patients/{id}")
    @Override
    public void delete(@PathVariable(name = "id") Long id) {
        patientService.delete(id);
    }

    @PutMapping("/patients/{id}")
    @Override
    public Patient update(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long id, @RequestBody UpdatePatientDTO updatePatientDTO ) {
        return patientService.update(user, id, updatePatientDTO);
    }

    @GetMapping("/patients/{id}/glucose")
    @Override
    public List<Glucose> getGlucose(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long id ) {
        return patientService.getGlucoseByUserId(user, id);
    }

    @PostMapping("/patients/{id}/glucose")
    @Override
    public Glucose addGlucose(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long id, @RequestBody GlucoseDTO glucoseDTO) {
        return patientService.addGlucose(user, id, glucoseDTO);
    }

    @GetMapping("/patients/{id}/bolus")
    @Override
    public List<Bolus> getBolus(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long id ) {
        return patientService.getBolusByUserId(user, id);
    }

    @PostMapping("/patients/{id}/bolus")
    @Override
    public Bolus addBolus(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long id, @RequestBody BolusDTO bolusDTO) {
        return patientService.addBolus(user, id, bolusDTO);
    }

    @PostMapping("/patients/{id}/bolus/calculate")
    public Bolus calculateBolus(@AuthenticationPrincipal User user, @PathVariable(name = "id") Long id, @RequestBody BolusParamsDTO bolusParamsDTO) {
        return patientService.calculateBolus(user, id, bolusParamsDTO);
    }

    /*
    @PostMapping("/patients")
    @Override
    public User create(@RequestBody @Valid NewUserDTO newUserDTO) {
        return patientService.create(newUserDTO);
    }


    @PostMapping("/patients/information")
    public Information create(@RequestBody @Valid InformationDTO informationDTO) {
        return patientService.addInformation(informationDTO);
    }
     */
}
