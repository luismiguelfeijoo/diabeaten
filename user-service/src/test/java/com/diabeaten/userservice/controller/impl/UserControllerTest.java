package com.diabeaten.userservice.controller.impl;

import com.diabeaten.userservice.exceptions.NoSuchUserException;
import com.diabeaten.userservice.model.Monitor;
import com.diabeaten.userservice.model.User;
import com.diabeaten.userservice.service.MonitorService;
import com.diabeaten.userservice.service.UserService;
import com.diabeaten.userservice.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {
    @Autowired
    private UserController userController;
    @MockBean
    private UserService userService;

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
        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAll_NotUsers_EmptyList() throws Exception {
        when(userService.getAll()).thenReturn(new ArrayList<>());
        String response = mockMvc.perform(get("/users").header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals("[]", response);
    }

    @Test
    public void getAll_Monitors_List() throws Exception {
        User user1 = new User("test@test.com", "Test1234$");
        User user2 = new User("test2@test.com", "Test1234$");
        when(userService.getAll()).thenReturn(Stream.of(user1, user2).collect(Collectors.toList()));
        mockMvc.perform(get("/users").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("test@test.com"))
                .andExpect(jsonPath("$[1].username").value("test2@test.com"));
    }

    @Test
    public void getById_NotMonitor_NotFound() throws Exception {
        when(userService.getById(Mockito.anyLong())).thenThrow(NoSuchUserException.class);
        mockMvc.perform(get("/users/get-by").header("Authorization", token)
                .param("id", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getById_Monitors_Monitor() throws Exception {
        Monitor monitor2 = new Monitor("test2@test.com", "Test1234$", "test2");
        when(userService.getById((long) 2)).thenReturn(monitor2);
        mockMvc.perform(get("/users/get-by").header("Authorization", token).param("id", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test2@test.com"))
                .andExpect(jsonPath("$.name").value("test2"));
    }

    @Test
    public void getByUsername_NotMonitor_NotFound() throws Exception {
        when(userService.getByUsername(Mockito.anyString())).thenThrow(NoSuchUserException.class);
        mockMvc.perform(get("/users/get-by").header("Authorization", token)
                .param("username", "test"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getByUsername_Monitors_Monitor() throws Exception {
        Monitor monitor2 = new Monitor("test2@test.com", "Test1234$", "test2");
        when(userService.getByUsername(monitor2.getUsername())).thenReturn(monitor2);
        mockMvc.perform(get("/users/get-by").header("Authorization", token).param("username", "test2@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test2@test.com"))
                .andExpect(jsonPath("$.name").value("test2"));
    }

}