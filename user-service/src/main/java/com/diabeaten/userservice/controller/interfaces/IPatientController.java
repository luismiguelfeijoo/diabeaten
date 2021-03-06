package com.diabeaten.userservice.controller.interfaces;

import com.diabeaten.userservice.controller.dto.NewUserDTO;
import com.diabeaten.userservice.controller.dto.UpdateUserDTO;
import com.diabeaten.userservice.model.Patient;

import java.util.List;

public interface IPatientController {
    public List<Patient> getPatients();
    public Patient getById(Long id);
    public Patient create(NewUserDTO newUserDTO);
    public Patient update(Long id, UpdateUserDTO updateUserDTO);
    public void delete(Long id);
}
