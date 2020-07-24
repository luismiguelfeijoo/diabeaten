package com.diabeaten.edgeservice.service;

import com.diabeaten.edgeservice.client.UserClient;
import com.diabeaten.edgeservice.exception.UserClientNotWorkingException;
import com.diabeaten.edgeservice.model.User;
import com.diabeaten.edgeservice.security.CustomSecuredUser;
import com.diabeaten.edgeservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    UserClient userClient;

    @Autowired
    JwtUtil jwtUtil;

    String token;

    @BeforeEach
    public void setUp() {
        token = jwtUtil.generateToken("test");
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void getAll() {
        when(userClient.getAll(Mockito.anyString())).thenReturn(new ArrayList<>());
        assertEquals(0, userService.getAllUsers().size());
    }

    @Test
    public void getById() {
        User user = new User();
        user.setId((long) 1);
        user.setName("test");
        user.setPassword("gv4gv4rgv");
        when(userClient.getBy(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong())).thenReturn(new CustomSecuredUser(user));
        assertThrows(UserClientNotWorkingException.class, () -> userService.loadUserByUsername("test"));
    }

}