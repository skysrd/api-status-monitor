package com.example.apistatusmonitor.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
public class NotificationScheduler implements DisposableBean {
    @Value("${notification.scheduler.poolSize:5}")
    private int POOL_SIZE;
    // 알림 작업 실행을 위한 스케줄러 풀 (필요에 따라 풀 크기 조정 가능)
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(POOL_SIZE);
    // 각 NotificationConfig의 작업을 관리 (configId -> ScheduledFuture)
    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    /**
     * 주어진 jobId와 Runnable 작업을, interval (밀리초 단위) 간격으로 스케줄합니다.
     */
    public void scheduleJob(Long jobId, Runnable task, long interval) {
        // 기존 작업이 있다면 취소
        ScheduledFuture<?> existingTask = scheduledTasks.get(jobId);
        if (existingTask != null) {
            existingTask.cancel(false);
        }
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, 0, interval, TimeUnit.MILLISECONDS);
        scheduledTasks.put(jobId, future);
    }

    /**
     * 특정 jobId에 대한 작업을 취소합니다.
     */
    public void cancelJob(Long jobId) {
        ScheduledFuture<?> future = scheduledTasks.remove(jobId);
        if (future != null) {
            future.cancel(false);
        }
    }

    @Override
    public void destroy() throws Exception {
        // 모든 스케줄된 작업을 취소하고 스케줄러를 종료
        scheduledTasks.values().forEach(task -> task.cancel(false));
        scheduler.shutdown();
    }
}