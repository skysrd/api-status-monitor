package com.example.apistatusmonitor.notification;

import com.example.apistatusmonitor.monitoring.MonitorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationDispatcher {
    public void dispatchNotification(MonitorResult monitorResult) {
        log.info("Notification dispatched for monitor result: {}", monitorResult);
    }
}
