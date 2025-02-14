package com.example.apistatusmonitor.notification.sender;

import com.example.apistatusmonitor.notification.config.SMSNotificationConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SMSNotificationSender implements NotificationSender {
    private final SMSNotificationConfig smsNotificationConfig;

    @Override
    public void sendNotification(String message) {
        System.out.println("SMS Notification: " + message);
    }
}
