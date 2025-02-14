package com.example.apistatusmonitor.notification;

import com.example.apistatusmonitor.monitoring.MonitorResult;
import com.example.apistatusmonitor.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationSender notificationSender;

    public void processEvent(MonitorResult monitorResult) {
    }
}
