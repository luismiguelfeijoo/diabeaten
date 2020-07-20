package com.diabeaten.edgeservice.controller.impl;

import com.diabeaten.edgeservice.client.dto.NewUserDTO;
import com.diabeaten.edgeservice.controller.interfaces.IPatientController;
import com.diabeaten.edgeservice.model.dto.User;
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

    @GetMapping("/patients/{id}")
    @Override
    public User getById(@PathVariable(name = "id") Long id ) {
        return patientService.getById(id);
    }

    @PostMapping("/patients")
    @Override
    public User create(@RequestBody @Valid NewUserDTO newUserDTO) {
        return patientService.create(newUserDTO);
    }
}
