package com.diabeaten.notificationservice.controller;

import com.diabeaten.notificationservice.model.Notification;
import com.diabeaten.notificationservice.model.User;
import com.diabeaten.notificationservice.service.NotificationService;
import com.diabeaten.notificationservice.view_model.NotificationViewModel;
import com.diabeaten.notificationservice.view_model.SenderViewModel;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Controller
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private NotificationService notificationService;

    @MessageMapping("/notification/glucose")
    public void notifyGlucose(@Payload String message, @Header("senderId") String senderId) throws Exception {
        System.out.println(senderId);
        Notification newNotification = notificationService.addNotification(message, senderId);
        NotificationViewModel notificationViewModel = new NotificationViewModel(newNotification.getSender().getName() +" has reported a new blood glucose level of " + message, new SenderViewModel(newNotification.getSender().getName(), newNotification.getSender().getUsername()));
        newNotification.getSender().getMonitors().forEach(monitor -> {
            System.out.println(monitor.getName());
            this.simpMessagingTemplate.convertAndSend("/topic/user-" + monitor.getId(), notificationViewModel);
        });
        this.simpMessagingTemplate.convertAndSend("/topic/user-" + senderId, new NotificationViewModel("Your contacts have been notified of your current glucose level",  new SenderViewModel(newNotification.getSender().getName(), newNotification.getSender().getUsername())));
        this.simpMessagingTemplate.convertAndSend("/topic/admin", notificationViewModel);
    }

    @MessageMapping("/notification/bolus")
    public void notifyBolus(@Payload String message, @Header("senderId") String senderId) throws Exception {
        Notification newNotification = notificationService.addNotification(message, senderId);
        NotificationViewModel notificationViewModel = new NotificationViewModel(newNotification.getSender().getName() +" has reported a new bolus of " + message, new SenderViewModel(newNotification.getSender().getName(), newNotification.getSender().getUsername()));
        newNotification.getSender().getMonitors().forEach(monitor -> {
            System.out.println(monitor.getName());
            this.simpMessagingTemplate.convertAndSend("/topic/user-" + monitor.getId(), notificationViewModel);
        });
        this.simpMessagingTemplate.convertAndSend("/topic/user-" + senderId, new NotificationViewModel("Your contacts have been notified of your new bolus",  new SenderViewModel(newNotification.getSender().getName(), newNotification.getSender().getUsername())));
        this.simpMessagingTemplate.convertAndSend("/topic/admin", notificationViewModel);
    }
}
