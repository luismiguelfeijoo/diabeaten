package com.diabeaten.edgeservice.controller.impl;

import com.diabeaten.edgeservice.model.Role;
import com.diabeaten.edgeservice.model.User;
import com.diabeaten.edgeservice.security.CustomSecuredUser;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void getAllLeads_leads_getList() throws Exception {
        mockMvc.perform(post("/login").with(user(user)))
                .andExpect(status().isOk());

    }

}