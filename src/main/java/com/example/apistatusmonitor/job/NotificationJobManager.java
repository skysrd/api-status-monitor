package com.example.apistatusmonitor.job;

import com.example.apistatusmonitor.monitoring.MonitorResult;
import com.example.apistatusmonitor.monitoring.MonitorResultAggregator;
import com.example.apistatusmonitor.notification.NotificationDispatcher;
import com.example.apistatusmonitor.notification.config.NotificationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
public class NotificationJobManager implements DisposableBean {

    // 알림 전송 작업을 위한 스케줄러 풀
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    // 각 NotificationConfig의 알림 작업 관리 (configId -> ScheduledFuture)
    private final Map<Long, ScheduledFuture<?>> scheduledJobs = new ConcurrentHashMap<>();

    private final MonitorResultAggregator aggregator;
    private final NotificationDispatcher notificationDispatcher;

    /**
     * 각 NotificationConfig에 대해 알림 발송 작업을 스케줄링합니다.
     * NotificationConfig에는 알림 전송 주기(notificationInterval, 밀리초 단위) 필드가 있다고 가정합니다.
     */
    public void scheduleNotificationJob(NotificationConfig config) {
        long notificationInterval = config.getSendInterval(); // NotificationConfig에 이 필드가 필요
        // 기존 작업 취소
        ScheduledFuture<?> existingJob = scheduledJobs.get(config.getId());
        if (existingJob != null) {
            existingJob.cancel(false);
        }

        Runnable jobTask = () -> {
            // 해당 config에 대한 모니터링 결과 집계 (각 config별 결과 리스트)
            List<MonitorResult> results = aggregator.getAndClearResultsForConfig(config.getId());
            if (!results.isEmpty()) {
                // 결과들을 하나의 메시지로 집계
                StringBuilder message = new StringBuilder("Monitoring results for configId " + config.getId() + ":\n");
                results.forEach(result -> message.append("Status: ").append(result.getStatus())
                        .append(", ResponseTime: ").append(result.getResponseTime()).append("ms\n"));
                // 알림 전송 (NotificationDispatcher는 NotificationConfig에 따라 sender를 생성)
                notificationDispatcher.dispatch(message.toString(), config.getId());
            }
        };

        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(jobTask, 0, notificationInterval, TimeUnit.MILLISECONDS);
        scheduledJobs.put(config.getId(), future);
    }

    /**
     * 전체 NotificationConfig 목록에 대해 알림 작업을 스케줄링합니다.
     */
    public void scheduleAll(List<NotificationConfig> configs) {
        scheduledJobs.values().forEach(job -> job.cancel(false));
        scheduledJobs.clear();

        for (NotificationConfig config : configs) {
            scheduleNotificationJob(config);
        }
    }

    @Override
    public void destroy() throws Exception {
        scheduler.shutdown();
    }
}