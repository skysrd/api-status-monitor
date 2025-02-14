package com.example.apistatusmonitor.notification.config;

import com.example.apistatusmonitor.notification.sender.NotificationSender;
import com.example.apistatusmonitor.notification.sender.SMSNotificationSender;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "sms_notification_config")
public class SMSNotificationConfig extends NotificationConfig {

    private String phoneNumber;
    private String messageTemplate;

    public void updateConfig(SMSNotificationConfig smsNotificationConfig) {
        this.setName(smsNotificationConfig.getName());
        this.phoneNumber = smsNotificationConfig.getPhoneNumber();
        this.messageTemplate = smsNotificationConfig.getMessageTemplate();
    }

    @Override
    public NotificationSender createSender() {
        return new SMSNotificationSender(this);
    }
}
