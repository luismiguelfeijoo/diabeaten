package com.diabeaten.userservice.service;

import com.diabeaten.userservice.controller.dto.NewUserDTO;
import com.diabeaten.userservice.exceptions.DuplicatedUsernameException;
import com.diabeaten.userservice.exceptions.NoSuchUserException;
import com.diabeaten.userservice.model.Monitor;
import com.diabeaten.userservice.model.Patient;
import com.diabeaten.userservice.model.Role;
import com.diabeaten.userservice.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MonitorService {
    @Autowired
    private MonitorRepository monitorRepository;

    public List<Monitor> getAll() {
        return monitorRepository.findAll();
    }

    public Monitor getById(Long id) {
        return monitorRepository.findById(id).orElseThrow(() -> new NoSuchUserException("There's no user with provided username"));
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
