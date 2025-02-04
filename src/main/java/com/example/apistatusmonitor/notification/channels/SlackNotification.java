package com.example.apistatusmonitor.notification.channels;

import com.example.apistatusmonitor.config.NotificationConfig;
import com.example.apistatusmonitor.config.NotificationConfigManager;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SlackNotification implements NotificationChannel {
    private final NotificationConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    public SlackNotification(NotificationConfigManager notificationConfigManager) {
        this.config = notificationConfigManager.getConfig("slack");
    }

    @Override
    public void sendNotification(String message) {

        String templateMessage = String.format(config.getNotificationTemplate(), message);

        // Send slack notification
        restTemplate.postForEntity(config.getNotificationEndpoint(), templateMessage, String.class);
    }
}
