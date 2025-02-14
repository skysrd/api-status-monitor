package com.example.apistatusmonitor.event;

import org.springframework.context.ApplicationEventMulticaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableAsync
public class EventListenerConfig {

    /**
     * 비동기 이벤트 처리를 위한 TaskExecutor를 정의합니다.
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("event-exec-");
        executor.initialize();
        return executor;
    }

    /**
     * ApplicationEventMulticaster를 커스터마이징하여, 이벤트 리스너가 비동기로 실행되도록 설정합니다.
     */
    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster applicationEventMulticaster(TaskExecutor taskExecutor) {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        // 비동기 처리를 위해 TaskExecutor를 지정
        multicaster.setTaskExecutor(taskExecutor);
        return multicaster;
    }
}