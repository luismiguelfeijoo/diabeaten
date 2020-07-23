package com.diabeaten.notificationservice.controller;

import com.diabeaten.notificationservice.model.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @MessageMapping("/hello")
    @SendTo("/topic/notification")
    public Notification greeting(@Payload String message) throws Exception {
        return new Notification(message);
    }
}
