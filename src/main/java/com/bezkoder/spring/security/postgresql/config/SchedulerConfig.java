package com.bezkoder.spring.security.postgresql.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bezkoder.spring.security.postgresql.repository.UserRepository;
import com.bezkoder.spring.security.postgresql.services.UserCountScheduler;

@Configuration
public class SchedulerConfig {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);
    private static final long FIXED_RATE_MS = 60000;

    @Bean(destroyMethod = "shutdown")
    public ScheduledExecutorService scheduledExecutorService() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        logger.info("ScheduledExecutorService создан");
        return executor;
    }

    @Bean(destroyMethod = "destroy")
    public ScheduledTaskManager userCountScheduledTask(
            ScheduledExecutorService scheduledExecutorService,
            UserRepository userRepository) {
        
        UserCountScheduler scheduler = new UserCountScheduler(userRepository);
        
        ScheduledFuture<?> task = scheduledExecutorService.scheduleAtFixedRate(
            scheduler::logUserCount,
            0,
            FIXED_RATE_MS,
            TimeUnit.MILLISECONDS
        );
        
        logger.info("Планировщик задач инициализирован. Задача будет выполняться каждые {} мс", FIXED_RATE_MS);
        return new ScheduledTaskManager(task, scheduledExecutorService);
    }

    public static class ScheduledTaskManager {
        private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskManager.class);
        private final ScheduledFuture<?> task;
        private final ScheduledExecutorService executor;

        public ScheduledTaskManager(ScheduledFuture<?> task, ScheduledExecutorService executor) {
            this.task = task;
            this.executor = executor;
        }

        public void destroy() {
            if (task != null) {
                task.cancel(false);
            }
            if (executor != null) {
                executor.shutdown();
                try {
                    if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                        logger.warn("Планировщик был принудительно остановлен");
                    } else {
                        logger.info("Планировщик задач успешно остановлен");
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                    Thread.currentThread().interrupt();
                    logger.error("Ошибка при остановке планировщика", e);
                }
            }
        }
    }
}

