package com.example.apistatusmonitor.scheduler;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Component
public class MonitoringScheduler implements DisposableBean {

    @Value("${monitoring.scheduler.poolSize:10}")
    private int POOL_SIZE;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(POOL_SIZE);
    // 각 작업의 식별자(ID)를 key로 하고, 스케줄된 작업(ScheduledFuture)을 value로 관리
    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();


    /**
     * 주어진 jobId와 Runnable 작업을, interval (밀리초 단위) 간격으로 스케줄합니다.
     */
    public void scheduleJob(Long jobId, Runnable task, long interval) {
        // 기존 작업이 있다면 취소
        ScheduledFuture<?> existing = scheduledTasks.get(jobId);
        if (existing != null) {
            existing.cancel(false);
        }
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, 0, interval, TimeUnit.SECONDS);
        scheduledTasks.put(jobId, future);
    }

    /**
     * 특정 jobId에 대해 스케줄된 작업을 취소합니다.
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