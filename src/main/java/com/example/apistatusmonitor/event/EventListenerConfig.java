package com.example.apistatusmonitor.event;

import com.example.apistatusmonitor.notification.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventListenerConfig {
    private final NotificationService notificationService;

    public EventListenerConfig(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleMonitorResultEvent(MonitorResultEvent event) {
        notificationService.processEvent(event.getMonitorResult());
    }
}
