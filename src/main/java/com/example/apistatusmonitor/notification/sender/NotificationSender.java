package com.example.apistatusmonitor.notification.sender;

import org.springframework.stereotype.Component;

@Component
public interface NotificationSender {
    void sendNotification(String message);
}
