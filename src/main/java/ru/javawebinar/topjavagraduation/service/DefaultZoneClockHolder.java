package ru.javawebinar.topjavagraduation.service;

import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class DefaultZoneClockHolder implements ClockHolder {

    private final Clock clock = Clock.systemDefaultZone();

    @Override
    public Clock getClock() {
        return clock;
    }
}
