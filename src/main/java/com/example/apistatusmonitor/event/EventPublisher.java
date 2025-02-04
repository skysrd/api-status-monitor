package com.example.apistatusmonitor.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    private final ApplicationEventPublisher publisher;

    public EventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishMonitorResultEvent(MonitorResultEvent event) {
        publisher.publishEvent(event);
    }
}