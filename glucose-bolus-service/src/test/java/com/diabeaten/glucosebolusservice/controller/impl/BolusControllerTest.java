package com.diabeaten.glucosebolusservice.controller.impl;

import com.diabeaten.glucosebolusservice.controller.dto.BolusDTO;
import com.diabeaten.glucosebolusservice.model.Bolus;
import com.diabeaten.glucosebolusservice.service.BolusService;
import com.diabeaten.glucosebolusservice.util.JwtUtil;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BolusControllerTest {
    @Autowired
    private BolusController bolusController;
    @MockBean
    private BolusService bolusService;

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
        mockMvc.perform(get("/bolus/by"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getByUserId_NotMonitor_NotFound() throws Exception {
        when(bolusService.getByUserId(Mockito.anyLong())).thenReturn(new ArrayList<>());
        String response = mockMvc.perform(get("/bolus/by").header("Authorization", token)
                .param("userId", "1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals("[]", response);
    }

    @Test
    public void create_validBolus() throws Exception {

        BolusDTO bolusDTO = new BolusDTO();
        bolusDTO.setChBolus(BigDecimal.ZERO);
        bolusDTO.setCorrectionBolus(BigDecimal.ZERO);
        bolusDTO.setDate(new Date());
        bolusDTO.setGlucose(BigDecimal.TEN);
        bolusDTO.setUserId((long) 1);
        when(bolusService.create(bolusDTO)).thenReturn(new Bolus());
        mockMvc.perform(post("/bolus").header("Authorization", token)
                .content(objectMapper.writeValueAsString(bolusDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}