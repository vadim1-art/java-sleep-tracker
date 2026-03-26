package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.result.ConstantsDescriptions;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;


public class SleeplessNightsFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(ConstantsDescriptions.SLEEPLESS_NIGHTS, 0);
        }

        LocalDateTime firstStart = sessions.stream()
                .map(SleepingSession::getStart)
                .min(LocalDateTime::compareTo)
                .orElseThrow();
        LocalDateTime lastEnd = sessions.stream()
                .map(SleepingSession::getEnd)
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDate firstNightDate = firstStart.toLocalDate();
        if (firstStart.toLocalTime().isAfter(LocalTime.NOON)) {
            firstNightDate = firstNightDate.plusDays(1);
        }
        LocalDate lastNightDate = lastEnd.toLocalDate();

        Stream<LocalDate> nightDates = firstNightDate.datesUntil(lastNightDate.plusDays(1));

        long sleeplessNights = nightDates
                .filter(nightDate -> {
                    LocalDateTime nightStart = nightDate.atStartOfDay();
                    LocalDateTime nightEnd = nightDate.atStartOfDay().plusHours(6);
                    return sessions.stream()
                            .noneMatch(s -> s.getStart().isBefore(nightEnd) && s.getEnd().isAfter(nightStart));
                })
                .count();

        return new SleepAnalysisResult(ConstantsDescriptions.SLEEPLESS_NIGHTS, sleeplessNights);
    }
}