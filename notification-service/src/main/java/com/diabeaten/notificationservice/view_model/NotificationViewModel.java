package com.diabeaten.notificationservice.view_model;

import com.diabeaten.notificationservice.model.User;

import java.util.List;

public class NotificationViewModel {
    public String message;
    public SenderViewModel sender;

    public NotificationViewModel(String message, SenderViewModel sender) {
        this.message = message;
        this.sender = sender;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SenderViewModel getSender() {
        return sender;
    }

    public void setSender(SenderViewModel sender) {
        this.sender = sender;
    }


}
