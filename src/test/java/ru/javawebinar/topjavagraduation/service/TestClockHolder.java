package ru.javawebinar.topjavagraduation.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@Primary
public class TestClockHolder implements ClockHolder {

    private Clock clock = Clock.systemDefaultZone();

    @Override
    public Clock getClock() {
        return clock;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.clock = Clock.fixed(dateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    }
}
