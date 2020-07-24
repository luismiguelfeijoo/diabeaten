package com.diabeaten.edgeservice.controller.impl;

import com.diabeaten.edgeservice.client.dto.NewMonitorDTO;
import com.diabeaten.edgeservice.model.Role;
import com.diabeaten.edgeservice.model.User;
import com.diabeaten.edgeservice.security.CustomSecuredUser;
import com.diabeaten.edgeservice.service.MonitorService;
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

import javax.management.monitor.Monitor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
    public void getAllMonitors_Monitors_getList() throws Exception {
        String result = mockMvc.perform(get("/monitors").with(user(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(result.equals("[]"));
    }

    @Test
    public void getById_Monitors_Monitor() throws Exception {
        when(monitorService.getById((long) 2)).thenReturn(new User());
        mockMvc.perform(get("/monitors/2").with(user(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void create_validMonitor() throws Exception {
        NewMonitorDTO monitorDTO = new NewMonitorDTO();
        monitorDTO.setUsername("test@gmail.com");
        monitorDTO.setName("test");
        monitorDTO.setPassword("Test1234$");
        monitorDTO.setPatientId((long) 1);
        when(monitorService.create(monitorDTO)).thenReturn(new User());
        mockMvc.perform(post("/monitors").with(user(user))
                .content(objectMapper.writeValueAsString(monitorDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_Authorized_Void() throws Exception {
        mockMvc.perform(delete("/monitors/2").with(user(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_Unauthorized_Error() throws Exception {
        mockMvc.perform(delete("/monitors/2"))
                .andExpect(status().isUnauthorized());
    }

}