package com.example.apistatusmonitor.config;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification_config")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationConfig {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel")
    private String channel; //SLACK, EMAIL, SMS, etc

    @Column(name = "notification_endpoint")
    private String notificationEndpoint;

    @Column(name = "notification_template")
    private String notificationTemplate;

    public void updateConfig(NotificationConfig notificationConfig) {
        this.channel = notificationConfig.getChannel();
        this.notificationEndpoint = notificationConfig.getNotificationEndpoint();
        this.notificationTemplate = notificationConfig.getNotificationTemplate();
    }
}
