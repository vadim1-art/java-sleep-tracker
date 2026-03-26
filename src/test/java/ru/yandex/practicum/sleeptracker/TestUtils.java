package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.model.Quality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static SleepingSession session(String start, String end, Quality quality) {
        return new SleepingSession(
                LocalDateTime.parse(start, formatter),
                LocalDateTime.parse(end, formatter),
                quality
        );
    }
}