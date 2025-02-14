package com.example.apistatusmonitor.notification.sender;

import com.example.apistatusmonitor.notification.config.SlackNotificationConfig;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SlackNotificationSender implements NotificationSender {
    private final SlackNotificationConfig config;
    private final Slack slack = Slack.getInstance();

    public void sendNotification(String message) {
        try {
            slack.methods()
                .chatPostMessage(req -> req
                        .token(config.getToken())
                        .channel(config.getChannel())
                        .text(String.format(config.getText(), config.getName(), message)));
        } catch (IOException | SlackApiException exception) {
            log.error("Failed to send slack notification", exception);
        }

        System.out.println("Sending slack notification: " + message);
    }

}
