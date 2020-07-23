package com.diabeaten.userservice.service;

import com.diabeaten.userservice.controller.dto.NewMonitorDTO;
import com.diabeaten.userservice.controller.dto.NewUserDTO;
import com.diabeaten.userservice.exceptions.DuplicatedUsernameException;
import com.diabeaten.userservice.exceptions.NoSuchUserException;
import com.diabeaten.userservice.model.Monitor;
import com.diabeaten.userservice.model.Patient;
import com.diabeaten.userservice.model.Role;
import com.diabeaten.userservice.repository.MonitorRepository;
import com.diabeaten.userservice.repository.PatientRepository;
import com.diabeaten.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MonitorService {
    @Autowired
    private MonitorRepository monitorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<Monitor> getAll() {
        return monitorRepository.findAll();
    }

    public Monitor getById(Long id) {
        return monitorRepository.findById(id).orElseThrow(() -> new NoSuchUserException("There's no user with provided username"));
    }

    public Monitor create(NewMonitorDTO newMonitorDTO) {
        Patient patient = patientRepository.findById(newMonitorDTO.getPatientId()).orElseThrow(() -> new NoSuchUserException("There's no user with provided username"));
        Monitor monitor = new Monitor(newMonitorDTO.getUsername(), newMonitorDTO.getPassword(), newMonitorDTO.getName());
        monitor.setPatient(patient);
        Role role = new Role();
        role.setRole("ROLE_MONITOR");
        role.setUser(monitor);
        Monitor result = null;
        try {
            result = monitorRepository.save(monitor);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedUsernameException("There's already an user with the provided username");
        }
        roleRepository.save(role);
        return result;
    }

    /*
    @Transactional
    public Monitor create(NewUserDTO newUserDTO) {
        Monitor newMonitor = new Patient(newUserDTO.getUsername(), newUserDTO.getPassword(), newUserDTO.getName());
        Role role = new Role();
        role.setRole("ROLE_MONITOR");
        role.setUser(newPatient);
        Patient result = null;
        try {
            result = monitorRepository.save(newPatient);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedUsernameException("There's already an user with the provided username");
        }
        roleRepository.save(role);
        return result;
    }
     */
}
