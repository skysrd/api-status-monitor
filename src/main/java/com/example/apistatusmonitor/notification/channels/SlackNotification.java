package com.example.apistatusmonitor.notification.channels;

import com.example.apistatusmonitor.config.NotificationConfig;
import com.example.apistatusmonitor.config.NotificationConfigManager;
import com.slack.api.Slack;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
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

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(config.getSlackChannelId())
                .text(message)
                .build();

        String templateMessage = String.format(config.getNotificationTemplate(), message);

        //TODO: Implement sending message to slack
    }
}