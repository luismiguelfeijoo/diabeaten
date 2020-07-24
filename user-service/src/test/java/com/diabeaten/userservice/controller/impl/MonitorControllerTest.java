package com.diabeaten.userservice.controller.impl;

import com.diabeaten.userservice.controller.dto.NewMonitorDTO;
import com.diabeaten.userservice.exceptions.NoSuchUserException;
import com.diabeaten.userservice.model.Monitor;
import com.diabeaten.userservice.service.MonitorService;
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
class MonitorControllerTest {
    @Autowired
    private MonitorController monitorController;
    @MockBean
    private MonitorService monitorService;

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
        mockMvc.perform(get("/monitors"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAll_NotUsers_EmptyList() throws Exception {
        when(monitorService.getAll()).thenReturn(new ArrayList<>());
        String response = mockMvc.perform(get("/monitors").header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals("[]", response);
    }

    @Test
    public void getAll_Monitors_List() throws Exception {
        Monitor monitor1 = new Monitor("test@test.com", "Test1234$", "test");
        Monitor monitor2 = new Monitor("test2@test.com", "Test1234$", "test2");
        when(monitorService.getAll()).thenReturn(Stream.of(monitor1, monitor2).collect(Collectors.toList()));
        mockMvc.perform(get("/monitors").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("test@test.com"))
                .andExpect(jsonPath("$[1].username").value("test2@test.com"));
    }

    @Test
    public void getById_NotMonitor_NotFound() throws Exception {
        when(monitorService.getById(Mockito.anyLong())).thenThrow(NoSuchUserException.class);
        mockMvc.perform(get("/monitors/1").header("Authorization", token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getById_Monitors_Monitor() throws Exception {
        Monitor monitor2 = new Monitor("test2@test.com", "Test1234$", "test2");
        when(monitorService.getById((long) 2)).thenReturn(monitor2);
        mockMvc.perform(get("/monitors/2").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test2@test.com"))
                .andExpect(jsonPath("$.name").value("test2"));
    }

    @Test
    public void create_validMonitor() throws Exception {
        NewMonitorDTO monitorDTO = new NewMonitorDTO();
        monitorDTO.setUsername("test@gmail.com");
        monitorDTO.setName("test");
        monitorDTO.setPassword("Test1234$");
        monitorDTO.setPatientId((long) 1);
        when(monitorService.create(monitorDTO)).thenReturn(new Monitor(monitorDTO.getUsername(), monitorDTO.getPassword(), monitorDTO.getName()));
        mockMvc.perform(post("/monitors").header("Authorization", token)
                .content(objectMapper.writeValueAsString(monitorDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_MonitorsId_Void() throws Exception {
        mockMvc.perform(delete("/monitors/2").header("Authorization", token))
                .andExpect(status().isOk());
    }
}