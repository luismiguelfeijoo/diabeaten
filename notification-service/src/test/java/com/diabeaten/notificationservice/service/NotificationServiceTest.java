package com.diabeaten.notificationservice.service;

import com.diabeaten.notificationservice.client.UserClient;
import com.diabeaten.notificationservice.model.Notification;
import com.diabeaten.notificationservice.model.User;
import com.diabeaten.notificationservice.repository.NotificationRepository;
import com.diabeaten.notificationservice.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;
    @MockBean
    private NotificationRepository notificationRepository;
    @MockBean
    private UserClient userClient;
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void addNotification() {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");

        User user = new User();
        user.setId((long)1);
        user.setMonitors(Stream.of(new User()).collect(Collectors.toList()));
        user.setName("test");
        user.setUsername("test@test.com");

        Notification notification = new Notification();
        notification.setSender(user);
        notification.setMessage("TEST");

        when(userClient.getPatientById(userToken, (long) 1)).thenReturn(user);
        when(notificationRepository.save(Mockito.any(Notification.class))).thenReturn(notification);

        Notification createdNotification = notificationService.addNotification("TEST", "1");

        assertEquals(notification.getMessage(), createdNotification.getMessage());

    }

}