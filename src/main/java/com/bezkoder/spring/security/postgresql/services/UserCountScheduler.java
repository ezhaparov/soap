package com.bezkoder.spring.security.postgresql.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bezkoder.spring.security.postgresql.repository.UserRepository;

public class UserCountScheduler {

    private static final Logger logger = LoggerFactory.getLogger(UserCountScheduler.class);

    private final UserRepository userRepository;

    public UserCountScheduler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void logUserCount() {
        try {
            long userCount = userRepository.count();
            logger.info("Количество пользователей: {}", userCount);
        } catch (Exception e) {
            logger.error("Ошибка при получении количества пользователей", e);
        }
    }
}

