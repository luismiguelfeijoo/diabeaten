package com.diabeaten.userservice.service;

import com.diabeaten.userservice.exceptions.NoSuchUserException;
import com.diabeaten.userservice.model.Monitor;
import com.diabeaten.userservice.model.User;
import com.diabeaten.userservice.repository.RoleRepository;
import com.diabeaten.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void getAll() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, userService.getAll().size());
    }

    @Test
    public void getById_NoUser_Exception() {
        assertThrows(NoSuchUserException.class, () -> { userService.getById((long) 2);});
    }

    @Test
    public void getById() {
        User user = new User();
        user.setPassword("test");
        user.setUsername("test");
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        User foundUser = userService.getById((long)2);
        assertEquals(user.getPassword(), foundUser.getPassword());
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    public void getByUsername_NoUser_Exception() {
        assertThrows(NoSuchUserException.class, () -> { userService.getByUsername("test");});
    }

    @Test
    public void getByUsername() {
        User user = new User();
        user.setPassword("test");
        user.setUsername("test");
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        User foundUser = userService.getByUsername("test");
        assertEquals(user.getPassword(), foundUser.getPassword());
        assertEquals(user.getUsername(), foundUser.getUsername());
    }
}