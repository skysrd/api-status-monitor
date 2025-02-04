package com.example.apistatusmonitor.event;

import com.example.apistatusmonitor.monitoring.MonitorResult;
import org.springframework.context.ApplicationEvent;

public class MonitorResultEvent extends ApplicationEvent {
    private final MonitorResult monitorResult;

    public MonitorResultEvent(Object source, MonitorResult monitorResult) {
        super(source);
        this.monitorResult = monitorResult;
    }

    public MonitorResult getMonitorResult() {
        return monitorResult;
    }
}