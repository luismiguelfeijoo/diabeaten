package com.diabeaten.userservice.service;

import com.diabeaten.userservice.controller.dto.NewMonitorDTO;
import com.diabeaten.userservice.controller.dto.NewUserDTO;
import com.diabeaten.userservice.controller.dto.UpdateUserDTO;
import com.diabeaten.userservice.exceptions.NoSuchUserException;
import com.diabeaten.userservice.model.Monitor;
import com.diabeaten.userservice.model.Patient;
import com.diabeaten.userservice.model.Role;
import com.diabeaten.userservice.repository.PatientRepository;
import com.diabeaten.userservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientServiceTest {
    @Autowired
    private PatientService patientService;
    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void getAll() {
        when(patientRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, patientService.getAll().size());
    }

    @Test
    public void getById_NoUser_Exception() {
        assertThrows(NoSuchUserException.class, () -> { patientService.getById((long) 2);});
    }

    @Test
    public void getById() {
        Patient patient = new Patient("test@test.com", "test", "test");
        when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(patient));
        Patient foundPatient = patientService.getById((long)2);
        assertEquals(patient.getName(), foundPatient.getName());
        assertEquals(patient.getPassword(), foundPatient.getPassword());
        assertEquals(patient.getUsername(), foundPatient.getUsername());
    }

    @Test
    public void create() {
        //Monitor monitor = new Monitor("test@test.com", "test", "test");
        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(new Patient());
        when(roleRepository.save(Mockito.any(Role.class))).thenReturn(new Role());
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setName("test");
        newUserDTO.setUsername("test@test.com");
        newUserDTO.setPassword("Test1234$");
        Patient createdPatient = patientService.create(newUserDTO);
        assertEquals(Patient.class, createdPatient.getClass());
    }

    @Test
    public void update() {
        Patient patient = new Patient("test", "test", "test");
        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setName("test1");
        updateUserDTO.setUsername("test1");
        Patient newPatient = new Patient(updateUserDTO.getUsername(), patient.getPassword(), updateUserDTO.getName());
        when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(patient));
        Patient foundPatient = patientService.update((long) 2, updateUserDTO);
        assertEquals(updateUserDTO.getName(), foundPatient.getName());
        assertEquals(updateUserDTO.getUsername(), foundPatient.getUsername());

    }

    @Test
    public void update_noPatient_Exception() {
        assertThrows(NoSuchUserException.class, () -> { patientService.update((long) 2, new UpdateUserDTO());});}

    @Test
    public void delete() {
        Patient patient = new Patient("test@test.com", "test", "test");
        patient.setMonitors(Stream.of(new Monitor(), new Monitor()).collect(Collectors.toList()));
        when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(patient));
        patientService.delete((long) 1);
    }

    @Test
    public void delete_noMonitors() {
        Patient patient = new Patient("test@test.com", "test", "test");
        patient.setMonitors(new ArrayList<>());
        when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(patient));
        patientService.delete((long) 1);
    }

    @Test
    public void delete_notPatient_NothingHappens() {
        patientService.delete((long) 1);
    }

}