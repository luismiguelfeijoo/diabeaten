package com.diabeaten.notificationservice.service;

import com.diabeaten.notificationservice.client.UserClient;
import com.diabeaten.notificationservice.model.Notification;
import com.diabeaten.notificationservice.model.User;
import com.diabeaten.notificationservice.repository.NotificationRepository;
import com.diabeaten.notificationservice.util.JwtUtil;
import com.diabeaten.notificationservice.view_model.NotificationViewModel;
import com.diabeaten.notificationservice.view_model.SenderViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtUtil jwtUtil;

    public Notification addNotification(String message, String senderId) {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        User sender = userClient.getPatientById(userToken, Long.parseLong(senderId));
        return notificationRepository.save(new Notification(message, sender));
    }

    public Notification addBolusNotification(String message, String senderId) {
        String userToken = "Bearer " + jwtUtil.generateToken("user-service");
        User sender = userClient.getPatientById(userToken, Long.parseLong(senderId));
        return notificationRepository.save(new Notification(message, sender));
    }
}
