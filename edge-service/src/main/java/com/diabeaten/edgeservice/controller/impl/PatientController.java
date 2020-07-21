package com.diabeaten.edgeservice.controller.impl;

import com.diabeaten.edgeservice.client.dto.*;
import com.diabeaten.edgeservice.controller.interfaces.IPatientController;
import com.diabeaten.edgeservice.model.*;
import com.diabeaten.edgeservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PatientController implements IPatientController {
    @Autowired
    private PatientService patientService;

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
    public Patient getById(@PathVariable(name = "id") Long id ) {
        return patientService.getById(id);
    }

    @GetMapping("/patients/{id}/glucose")
    @Override
    public List<Glucose> getGlucose(@PathVariable(name = "id") Long id ) {
        return patientService.getGlucoseByUserId(id);
    }

    @PostMapping("/patients/{id}/glucose")
    @Override
    public Glucose addGlucose(@PathVariable(name = "id") Long id, @RequestBody GlucoseDTO glucoseDTO) {
        return patientService.addGlucose(id, glucoseDTO);
    }

    @GetMapping("/patients/{id}/bolus")
    @Override
    public List<Bolus> getBolus(@PathVariable(name = "id") Long id ) {
        return patientService.getBolusByUserId(id);
    }

    @PostMapping("/patients/{id}/bolus")
    @Override
    public Bolus addBolus(@PathVariable(name = "id") Long id, @RequestBody BolusDTO bolusDTO) {
        return patientService.addBolus(id, bolusDTO);
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
