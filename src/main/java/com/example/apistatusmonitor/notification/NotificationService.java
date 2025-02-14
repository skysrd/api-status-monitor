package com.example.apistatusmonitor.notification;

import com.example.apistatusmonitor.event.MonitoringAnomalyEvent;
import com.example.apistatusmonitor.monitoring.MonitorResult;
import com.example.apistatusmonitor.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationSender notificationSender;

    @EventListener
    public void handleMonitoringAnomaly(MonitoringAnomalyEvent event) {
        MonitorResult result = event.getMonitorResult();
        // 예시: 이벤트를 수신한 후 메시지를 구성
        String message = "Monitoring anomaly detected for config " + result.getConfigId() +
                " at " + event.getTimestamp() +
                "\nStatus: " + result.getStatus() +
                ", Response Time: " + result.getResponseTime() + "ms" +
                (result.getErrorMessage() != null ? ", Error: " + result.getErrorMessage() : "");
        // 실제 알림 발송 로직 (예: Slack, SMS 등) 호출
        // notificationDispatcher.dispatch(message, result.getApiConfigId());
        System.out.println("Dispatching notification: " + message);
    }
}
