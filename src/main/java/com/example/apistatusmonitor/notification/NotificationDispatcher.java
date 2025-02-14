package com.example.apistatusmonitor.notification;

import com.example.apistatusmonitor.notification.config.NotificationConfig;
import com.example.apistatusmonitor.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationDispatcher {
    private final List<NotificationConfig> configList;

    public void dispatch(String message, Long configId) {
        Optional<NotificationConfig> optConfig = configList.stream()
                .filter(config -> config.getId().equals(configId))
                .findFirst();
        if (optConfig.isPresent()) {
            NotificationSender sender = optConfig.get().createSender();
            sender.sendNotification(message);
        } else {
            System.err.println("No NotificationConfig found for configId: " + configId);
        }
    }
}
