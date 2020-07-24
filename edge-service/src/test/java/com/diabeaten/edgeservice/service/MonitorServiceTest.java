package com.diabeaten.edgeservice.service;

import com.diabeaten.edgeservice.client.UserClient;
import com.diabeaten.edgeservice.client.dto.NewMonitorDTO;
import com.diabeaten.edgeservice.model.User;
import com.diabeaten.edgeservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MonitorServiceTest {
    @Autowired
    private MonitorService monitorService;
    @MockBean
    private UserClient userClient;
    @Autowired
    private JwtUtil jwtUtil;

    String token;

    @BeforeEach
    public void setUp() {
        token = jwtUtil.generateToken("test");
    }

    @Test
    public void getAll() {
        when(userClient.getMonitors(Mockito.anyString())).thenReturn(new ArrayList<>());
        assertEquals(0, monitorService.getAll().size());
    }

    @Test
    public void getById() {
        when(userClient.getMonitorById(Mockito.anyString(), Mockito.anyLong())).thenReturn(new User());
        assertEquals(User.class, monitorService.getById((long) 2).getClass());
    }

    @Test
    public void create() {
        when(userClient.create(Mockito.anyString(), Mockito.any(NewMonitorDTO.class))).thenReturn(new User());
        assertEquals(User.class, monitorService.create(new NewMonitorDTO()).getClass());
    }



    @Test
    public void delete() {
        monitorService.delete((long) 2);
    }
}