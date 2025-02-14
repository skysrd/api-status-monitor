package com.example.apistatusmonitor.notification.channels;

import com.example.apistatusmonitor.notification.config.NotificationConfig;
import com.example.apistatusmonitor.notification.manager.NotificationConfigManager;
import com.slack.api.Slack;
import org.springframework.stereotype.Component;


@Component
public class SlackNotification implements NotificationChannel {
    private final NotificationConfig config;

    public SlackNotification(NotificationConfigManager notificationConfigManager) {
        this.config = null; // notificationConfigManager.getSlackConfig();
    }

    @Override
    public void sendNotification(String message) {
        Slack slack = Slack.getInstance();

        //TODO: Implement sending message to slack
    }
}