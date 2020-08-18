package ru.graduation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
class AppClock {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
