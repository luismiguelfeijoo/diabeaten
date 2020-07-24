package com.diabeaten.glucosebolusservice.controller.impl;

import com.diabeaten.glucosebolusservice.controller.dto.BolusDTO;
import com.diabeaten.glucosebolusservice.controller.dto.GlucoseDTO;
import com.diabeaten.glucosebolusservice.model.Bolus;
import com.diabeaten.glucosebolusservice.model.Glucose;
import com.diabeaten.glucosebolusservice.service.BolusService;
import com.diabeaten.glucosebolusservice.service.GlucoseService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class GlucoseControllerTest {
    @Autowired
    private GlucoseController glucoseController;
    @MockBean
    private GlucoseService glucoseService;

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
        when(glucoseService.getByUserId(Mockito.anyLong())).thenReturn(new ArrayList<>());
        String response = mockMvc.perform(get("/glucose/2").header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals("[]", response);
    }

    @Test
    public void create_validBolus() throws Exception {
        GlucoseDTO glucoseDTO = new GlucoseDTO();
        glucoseDTO.setDate(new Date());
        glucoseDTO.setGlucose(BigDecimal.TEN);
        glucoseDTO.setUserId((long) 1);
        when(glucoseService.create(glucoseDTO)).thenReturn(new Glucose());
        mockMvc.perform(post("/glucose").header("Authorization", token)
                .content(objectMapper.writeValueAsString(glucoseDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}