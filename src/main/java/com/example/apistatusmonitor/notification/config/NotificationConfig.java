package com.example.apistatusmonitor.notification.config;

import com.example.apistatusmonitor.notification.sender.NotificationSender;
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
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class NotificationConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "send_interval")
    private Integer sendInterval;

    @Column(name = "enabled")
    private boolean enabled;

    public void updateConfig(NotificationConfig notificationConfig) {
        this.name = notificationConfig.getName();
        this.sendInterval = notificationConfig.getSendInterval();
        this.enabled = notificationConfig.isEnabled();
    }

    public abstract NotificationSender createSender();
}
