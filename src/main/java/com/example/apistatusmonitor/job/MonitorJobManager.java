package com.example.apistatusmonitor.job;

import com.example.apistatusmonitor.monitoring.MonitorResult;
import com.example.apistatusmonitor.monitoring.MonitorResultAggregator;
import com.example.apistatusmonitor.monitoring.MonitoringService;
import com.example.apistatusmonitor.monitoring.config.MonitoringConfig;
import com.example.apistatusmonitor.scheduler.MonitoringScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MonitorJobManager {

    private final MonitoringScheduler scheduler;
    private final MonitoringService monitoringService;
    private final MonitorResultAggregator aggregator;


    /**
     * 단일 ApiConfig에 대해 모니터링 작업을 스케줄링합니다.
     * ApiConfig에는 monitorInterval (밀리초 단위) 필드가 있다고 가정합니다.
     */
    public void scheduleMonitoringJob(MonitoringConfig config) {
        Runnable job = () -> {
            MonitorResult result = monitoringService.doMonitor(config);
            aggregator.addResult(result);
        };
        scheduler.scheduleJob(config.getId(), job, config.getCheckInterval());
    }

    /**
     * 전체 ApiConfig 목록에 대해 모니터링 작업을 등록합니다.
     */
    public void scheduleAll(List<MonitoringConfig> configs) {
        for (MonitoringConfig config : configs) {
            scheduleMonitoringJob(config);
        }
    }

    /**
     * 특정 모니터링 작업을 취소합니다.
     */
    public void cancelMonitoringJob(Long configId) {
        scheduler.cancelJob(configId);
    }
}