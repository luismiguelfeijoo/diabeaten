package com.diabeaten.userservice.service;

import com.diabeaten.userservice.controller.dto.NewUserDTO;
import com.diabeaten.userservice.controller.dto.UpdateUserDTO;
import com.diabeaten.userservice.exceptions.DuplicatedUsernameException;
import com.diabeaten.userservice.exceptions.NoSuchUserException;
import com.diabeaten.userservice.model.Patient;
import com.diabeaten.userservice.model.Role;
import com.diabeaten.userservice.model.User;
import com.diabeaten.userservice.repository.PatientRepository;
import com.diabeaten.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new NoSuchUserException("There's no user with provided username"));
    }

    @Transactional
    public Patient create(NewUserDTO newUserDTO) {
        Patient newPatient = new Patient(newUserDTO.getUsername(), newUserDTO.getPassword(), newUserDTO.getName());
        Role role = new Role();
        role.setRole("ROLE_PATIENT");
        role.setUser(newPatient);
        Patient result = null;
        try {
            result = patientRepository.save(newPatient);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedUsernameException("There's already an user with the provided username");
        }
        roleRepository.save(role);
        return result;
    }

    public Patient update(Long id, UpdateUserDTO updateUserDTO) {
        Patient foundPatient = patientRepository.findById(id).orElseThrow(() -> new NoSuchUserException("There's no user with provided username"));
        foundPatient.setName(updateUserDTO.getName());
        foundPatient.setUsername(updateUserDTO.getUsername());
        return patientRepository.save(foundPatient);
    }
}
