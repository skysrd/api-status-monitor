package com.example.apistatusmonitor.notification;

import com.example.apistatusmonitor.monitoring.MonitorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationDispatcher notificationDispatcher;

    public void processEvent(MonitorResult monitorResult) {
        notificationDispatcher.dispatchNotification(monitorResult);
    }


}
