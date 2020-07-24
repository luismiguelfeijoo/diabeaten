package com.diabeaten.informationservice.controller.impl;

import com.diabeaten.informationservice.controller.dto.InformationDTO;
import com.diabeaten.informationservice.controller.dto.RatioDTO;
import com.diabeaten.informationservice.controller.dto.SensibilityDTO;
import com.diabeaten.informationservice.controller.dto.UpdateInformationDTO;
import com.diabeaten.informationservice.exceptions.InformationNotFoundException;
import com.diabeaten.informationservice.model.Information;
import com.diabeaten.informationservice.service.InformationService;
import com.diabeaten.informationservice.util.JwtUtil;
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
import java.sql.Time;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class InformationControllerTest {
    @Autowired
    private InformationController informationController;
    @MockBean
    private InformationService informationService;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private JwtUtil jwtUtil;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String token;
    RatioDTO ratio;
    SensibilityDTO sensibility;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        token = "Bearer " + jwtUtil.generateToken("test");
        ratio = new RatioDTO();
        ratio.setEndHour(Time.valueOf("23:00:00"));
        ratio.setStartHour(Time.valueOf("00:00:00"));
        ratio.setRatioInGrams(new BigDecimal("5"));

        sensibility = new SensibilityDTO();
        sensibility.setEndHour(Time.valueOf("23:00:00"));
        sensibility.setStartHour(Time.valueOf("00:00:00"));
        sensibility.setSensibility(new BigDecimal("50"));
    }

    @Test
    public void connectionTry_NoTokenSent_Forbidden() throws Exception {
        mockMvc.perform(get("/information/2"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getById_NotMonitor_NotFound() throws Exception {
        when(informationService.getById(Mockito.anyLong())).thenThrow(InformationNotFoundException.class);
        mockMvc.perform(get("/information/1").header("Authorization", token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getById_InfoId_Information() throws Exception {
        Information information = new Information((long) 1, new BigDecimal("20"), new BigDecimal("3"));
        when(informationService.getById((long) 1)).thenReturn(information);
        mockMvc.perform(get("/information/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dia").value("3"))
                .andExpect(jsonPath("$.totalBasal").value("20"));
    }

    @Test
    public void create_validInformation() throws Exception {

        InformationDTO newInformation = new InformationDTO();
        newInformation.setUserId((long) 1);
        newInformation.setDIA(new BigDecimal("3"));
        newInformation.setTotalBasal(new BigDecimal("10"));
        newInformation.setRatios(Stream.of(ratio).collect(Collectors.toList()));
        newInformation.setSensibilities(Stream.of(sensibility).collect(Collectors.toList()));
        when(informationService.create(newInformation)).thenReturn(new Information());
        mockMvc.perform(post("/information").header("Authorization", token)
                .content(objectMapper.writeValueAsString(newInformation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update_validPatient() throws Exception {

        UpdateInformationDTO newInformation = new UpdateInformationDTO();
        newInformation.setDia(new BigDecimal("3"));
        newInformation.setTotalBasal(new BigDecimal("10"));
        newInformation.setRatios(Stream.of(ratio).collect(Collectors.toList()));
        newInformation.setSensibilities(Stream.of(sensibility).collect(Collectors.toList()));

        when(informationService.update((long) 1, newInformation)).thenReturn(new Information());
        mockMvc.perform(put("/information/1").header("Authorization", token)
                .content(objectMapper.writeValueAsString(newInformation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}