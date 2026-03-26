package ru.yandex.practicum.sleeptracker.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class SleepingSession {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Quality quality;

    public SleepingSession(LocalDateTime start, LocalDateTime end, Quality quality) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Начало сессии не может быть позже окончания");
        }
        this.start = start;
        this.end = end;
        this.quality = quality;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public Quality getQuality() {
        return quality;
    }

    public long getDurationMinutes() {
        return ChronoUnit.MINUTES.between(start, end);
    }
}