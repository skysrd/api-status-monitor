package com.example.apistatusmonitor.event;

import com.example.apistatusmonitor.monitoring.MonitorResult;
import java.time.LocalDateTime;

public class MonitoringAnomalyEvent {
    private final MonitorResult monitorResult;
    private final LocalDateTime timestamp;

    public MonitoringAnomalyEvent(MonitorResult monitorResult) {
        this.monitorResult = monitorResult;
        this.timestamp = LocalDateTime.now();
    }

    public MonitorResult getMonitorResult() {
        return monitorResult;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}