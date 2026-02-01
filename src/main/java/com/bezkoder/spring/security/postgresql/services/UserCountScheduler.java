package com.bezkoder.spring.security.postgresql.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bezkoder.spring.security.postgresql.repository.UserRepository;

@Component
public class UserCountScheduler {

    private static final Logger logger = LoggerFactory.getLogger(UserCountScheduler.class);

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 60000)
    public void logUserCount() {
        long userCount = userRepository.count();
        logger.info("Количество пользователей: {}", userCount);
    }
}

