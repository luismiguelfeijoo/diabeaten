package com.diabeaten.userservice.service;

import com.diabeaten.userservice.controller.dto.NewMonitorDTO;
import com.diabeaten.userservice.exceptions.NoSuchUserException;
import com.diabeaten.userservice.model.Monitor;
import com.diabeaten.userservice.model.Patient;
import com.diabeaten.userservice.model.Role;
import com.diabeaten.userservice.model.User;
import com.diabeaten.userservice.repository.MonitorRepository;
import com.diabeaten.userservice.repository.PatientRepository;
import com.diabeaten.userservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MonitorServiceTest {
    @Autowired
    private MonitorService monitorService;
    @MockBean
    private MonitorRepository monitorRepository;
    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void getAll() {
        when(monitorRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, monitorService.getAll().size());
    }

    @Test
    public void getById_NoUser_Exception() {
        assertThrows(NoSuchUserException.class, () -> { monitorService.getById((long) 2);});
    }

    @Test
    public void getById() {
        Monitor monitor = new Monitor("test@test.com", "test", "test");
        when(monitorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(monitor));
        Monitor foundMonitor = monitorService.getById((long)2);
        assertEquals(monitor.getName(), foundMonitor.getName());
        assertEquals(monitor.getPassword(), foundMonitor.getPassword());
        assertEquals(monitor.getUsername(), foundMonitor.getUsername());
    }

    @Test
    public void create() {
        //Monitor monitor = new Monitor("test@test.com", "test", "test");
        Patient patient = new Patient();
        patient.setId((long) 1);
        when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(patient));
        when(monitorRepository.save(Mockito.any(Monitor.class))).thenReturn(new Monitor());
        when(roleRepository.save(Mockito.any(Role.class))).thenReturn(new Role());
        NewMonitorDTO newMonitorDTO = new NewMonitorDTO();
        newMonitorDTO.setName("test");
        newMonitorDTO.setPatientId(patient.getId());
        newMonitorDTO.setUsername("test@test.com");
        newMonitorDTO.setPassword("Test1234$");
        Monitor createdMonitor = monitorService.create(newMonitorDTO);
        assertEquals(Monitor.class, createdMonitor.getClass());
    }

    @Test
    public void create_notPatient_Exception() {
        //Monitor monitor = new Monitor("test@test.com", "test", "test");
        // when(patientRepository.findById(Mockito.anyLong())).thenReturn(null);
        when(monitorRepository.save(Mockito.any(Monitor.class))).thenReturn(new Monitor());
        when(roleRepository.save(Mockito.any(Role.class))).thenReturn(new Role());
        NewMonitorDTO newMonitorDTO = new NewMonitorDTO();
        newMonitorDTO.setName("test");
        newMonitorDTO.setPatientId((long) 1);
        newMonitorDTO.setUsername("test@test.com");
        newMonitorDTO.setPassword("Test1234$");
        assertThrows(NoSuchUserException.class, () -> {monitorService.create(newMonitorDTO);});
    }

    @Test
    public void delete() {
        when(monitorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Monitor()));
        monitorService.delete((long) 1);
    }

    @Test
    public void delete_notMonitor_NothingHappens() {
        when(monitorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Monitor()));
        monitorService.delete((long) 1);

    }
}