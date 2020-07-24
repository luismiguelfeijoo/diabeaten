package com.diabeaten.userservice.controller.impl;

import com.diabeaten.userservice.controller.dto.NewMonitorDTO;
import com.diabeaten.userservice.controller.dto.NewUserDTO;
import com.diabeaten.userservice.controller.dto.UpdateUserDTO;
import com.diabeaten.userservice.exceptions.NoSuchUserException;
import com.diabeaten.userservice.model.Monitor;
import com.diabeaten.userservice.model.Patient;
import com.diabeaten.userservice.service.MonitorService;
import com.diabeaten.userservice.service.PatientService;
import com.diabeaten.userservice.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PatientControllerTest {
    @Autowired
    private PatientController patientController;
    @MockBean
    private PatientService patientService;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private JwtUtil jwtUtil;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String token;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        token = "Bearer " + jwtUtil.generateToken("test");
    }

    @Test
    public void connectionTry_NoTokenSent_Forbidden() throws Exception {
        mockMvc.perform(get("/patients"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAll_NotUsers_EmptyList() throws Exception {
        when(patientService.getAll()).thenReturn(new ArrayList<>());
        String response = mockMvc.perform(get("/patients").header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals("[]", response);
    }

    @Test
    public void getAll_Monitors_List() throws Exception {
        Patient patient1 = new Patient("test@test.com", "Test1234$", "test");
        Patient patient2 = new Patient("test2@test.com", "Test1234$", "test2");
        when(patientService.getAll()).thenReturn(Stream.of(patient1, patient2).collect(Collectors.toList()));
        mockMvc.perform(get("/patients").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("test@test.com"))
                .andExpect(jsonPath("$[1].username").value("test2@test.com"));
    }

    @Test
    public void getById_NotMonitor_NotFound() throws Exception {
        when(patientService.getById(Mockito.anyLong())).thenThrow(NoSuchUserException.class);
        mockMvc.perform(get("/patients/1").header("Authorization", token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getById_PatientsId_Patient() throws Exception {
        Patient patient2 = new Patient("test2@test.com", "Test1234$", "test2");
        when(patientService.getById((long) 2)).thenReturn(patient2);
        mockMvc.perform(get("/patients/2").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test2@test.com"))
                .andExpect(jsonPath("$.name").value("test2"));
    }

    @Test
    public void create_validPatient() throws Exception {
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setUsername("test@gmail.com");
        newUserDTO.setName("test");
        newUserDTO.setPassword("Test1234$");
        when(patientService.create(newUserDTO)).thenReturn(new Patient(newUserDTO.getUsername(), newUserDTO.getPassword(), newUserDTO.getName()));
        mockMvc.perform(post("/patients").header("Authorization", token)
                .content(objectMapper.writeValueAsString(newUserDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update_validPatient() throws Exception {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUsername("test@gmail.com");
        updateUserDTO.setName("test");
        when(patientService.update((long) 1, updateUserDTO)).thenReturn(new Patient(updateUserDTO.getUsername(), "+++++++", updateUserDTO.getName()));
        mockMvc.perform(put("/patients/1").header("Authorization", token)
                .content(objectMapper.writeValueAsString(updateUserDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_MonitorsId_Void() throws Exception {
        mockMvc.perform(delete("/patients/2").header("Authorization", token))
                .andExpect(status().isOk());
    }
}