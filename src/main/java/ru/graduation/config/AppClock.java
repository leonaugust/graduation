package ru.graduation.config;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class AppClock extends Clock {
    private static Clock clock = Clock.systemDefaultZone();

    @Override
    public ZoneId getZone() {
        return clock.getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return clock.withZone(ZoneId.systemDefault());
    }

    @Override
    public Instant instant() {
        return clock.instant();
    }

    public static LocalTime now() {
        return LocalTime.now(clock);
    }

    public static Clock getClock() {
        return AppClock.clock;
    }

    public void setClock(Clock clock) {
        AppClock.clock = clock;
    }
}
