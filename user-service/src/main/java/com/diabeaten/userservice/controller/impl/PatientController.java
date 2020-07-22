package com.diabeaten.userservice.controller.impl;

import com.diabeaten.userservice.controller.dto.NewUserDTO;
import com.diabeaten.userservice.controller.dto.UpdateUserDTO;
import com.diabeaten.userservice.controller.interfaces.IPatientController;
import com.diabeaten.userservice.model.Patient;
import com.diabeaten.userservice.service.PatientService;
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
    public List<Patient> getPatients() {
        return patientService.getAll();
    }

    @GetMapping("/patients/{id}")
    @Override
    public Patient getById(@PathVariable(name = "id") Long id) {
        return patientService.getById(id);
    }

    @PutMapping("/patients/{id}")
    @Override
    public Patient update(@PathVariable(name = "id") Long id, @RequestBody @Valid UpdateUserDTO updateUserDTO) {
        return patientService.update(id, updateUserDTO);
    }

    @PostMapping("/patients")
    @Override
    public Patient create(@RequestBody @Valid NewUserDTO newUserDTO) {
        return patientService.create(newUserDTO);
    }
}
