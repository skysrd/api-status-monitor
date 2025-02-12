package com.example.apistatusmonitor.config;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "notification_config")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NotificationConfig {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "channel")
    private String channel; //SLACK, EMAIL, SMS, etc

    @Column(name = "notification_endpoint")
    private String endpoint;

    @Column(name = "slack_channel_id")
    private String slackChannelId;

    @Column(name = "notification_template")
    private String notificationTemplate;

    public void updateConfig(NotificationConfig notificationConfig) {
        this.name = notificationConfig.getName();
        this.channel = notificationConfig.getChannel();
        this.endpoint = notificationConfig.getEndpoint();
        this.slackChannelId = notificationConfig.getSlackChannelId();
        this.notificationTemplate = notificationConfig.getNotificationTemplate();
    }
}
