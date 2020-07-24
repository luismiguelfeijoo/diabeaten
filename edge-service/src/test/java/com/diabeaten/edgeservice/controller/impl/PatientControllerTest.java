package com.diabeaten.edgeservice.controller.impl;

import com.diabeaten.edgeservice.client.dto.*;
import com.diabeaten.edgeservice.model.*;
import com.diabeaten.edgeservice.security.CustomSecuredUser;
import com.diabeaten.edgeservice.service.MonitorService;
import com.diabeaten.edgeservice.service.PatientService;
import com.diabeaten.edgeservice.service.UserService;
import com.diabeaten.edgeservice.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PatientControllerTest {
    @MockBean
    private PatientService patientService;
    @MockBean
    private UserService userService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private JwtUtil jwtUtil;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private CustomSecuredUser user;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        User newUser = new User();
        newUser.setId((long) 1);
        newUser.setUsername("user");
        Role role = new Role("ROLE_ADMIN");
        newUser.getRoles().add(role);
        newUser.setPassword("test");
        user = new CustomSecuredUser(newUser);
        when(userService.loadUserByUsername("user")).thenReturn(user);
    }

    @Test
    public void getAllUsers_Users_getList() throws Exception {
        String result = mockMvc.perform(get("/users").with(user(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(result.equals("[]"));
    }

    @Test
    public void getAllPatients_Patients_getList() throws Exception {
        String result = mockMvc.perform(get("/patients").with(user(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(result.equals("[]"));
    }

    @Test
    public void getById_Patient_Patient() throws Exception {
        when(patientService.getById(user, (long) 2)).thenReturn(new Patient());
        mockMvc.perform(get("/patients/2").with(user(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void create_validPatient() throws Exception {
        NewPatientDTO patientDTO = new NewPatientDTO();
        patientDTO.setUsername("test@gmail.com");
        patientDTO.setName("test");
        patientDTO.setPassword("Test1234$");
        patientDTO.setRatios(new ArrayList<>());
        patientDTO.setSensibilities(new ArrayList<>());
        patientDTO.setDIA(BigDecimal.ZERO);
        patientDTO.setTotalBasal(BigDecimal.ZERO);
        when(patientService.create(patientDTO)).thenReturn(new User());
        mockMvc.perform(post("/patients").with(user(user))
                .content(objectMapper.writeValueAsString(patientDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update_validPatient() throws Exception {
        UpdatePatientDTO updatePatientDTO = new UpdatePatientDTO();
        updatePatientDTO.setUsername("test@gmail.com");
        updatePatientDTO.setName("test");
        updatePatientDTO.setRatios(new ArrayList<>());
        updatePatientDTO.setSensibilities(new ArrayList<>());
        updatePatientDTO.setDia(BigDecimal.ZERO);
        updatePatientDTO.setTotalBasal(BigDecimal.ZERO);
        when(patientService.update(user, (long) 1, updatePatientDTO)).thenReturn(new Patient());
        mockMvc.perform(put("/patients/1").with(user(user))
                .content(objectMapper.writeValueAsString(updatePatientDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_Authorized_Void() throws Exception {
        mockMvc.perform(delete("/patients/2").with(user(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_Unauthorized_Error() throws Exception {
        mockMvc.perform(delete("/patients/2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAllGlucose_getList() throws Exception {
        String result = mockMvc.perform(get("/patients/1/glucose").with(user(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(result.equals("[]"));
    }

    @Test
    public void addGlucose_validGlucose() throws Exception {
        GlucoseDTO glucoseDTO = new GlucoseDTO();
        glucoseDTO.setUserId((long) 1);
        glucoseDTO.setGlucose(BigDecimal.TEN);
        glucoseDTO.setDate(new Date());
        when(patientService.addGlucose(user, (long) 1, glucoseDTO)).thenReturn(new Glucose());
        mockMvc.perform(post("/patients/1/glucose").with(user(user))
                .content(objectMapper.writeValueAsString(glucoseDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllBolus_getList() throws Exception {
        String result = mockMvc.perform(get("/patients/1/bolus").with(user(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(result.equals("[]"));
    }

    @Test
    public void addBolus_validBolus() throws Exception {
        BolusDTO bolusDTO = new BolusDTO();
        bolusDTO.setUserId((long) 1);
        bolusDTO.setGlucose(BigDecimal.TEN);
        bolusDTO.setDate(new Date());
        bolusDTO.setCorrectionBolus(BigDecimal.ZERO);
        bolusDTO.setChBolus(BigDecimal.ZERO);
        when(patientService.addBolus(user, (long) 1, bolusDTO)).thenReturn(new Bolus());
        mockMvc.perform(post("/patients/1/bolus").with(user(user))
                .content(objectMapper.writeValueAsString(bolusDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void calculateBolus_validBolus() throws Exception {
        BolusParamsDTO bolusDTO = new BolusParamsDTO();
        bolusDTO.setGlucose(BigDecimal.TEN);
        bolusDTO.setDate(new Date());
        bolusDTO.setCarbs(BigDecimal.ZERO);
        when(patientService.calculateBolus(user, (long) 1, bolusDTO)).thenReturn(new Bolus());
        mockMvc.perform(post("/patients/1/bolus/calculate").with(user(user))
                .content(objectMapper.writeValueAsString(bolusDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}