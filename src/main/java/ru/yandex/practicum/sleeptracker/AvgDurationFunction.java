package ru.yandex.practicum.sleeptracker;

import java.util.List;


public class AvgDurationFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        double avg = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0.0);
        return new SleepAnalysisResult(AnalysisDescriptions.AVG_DURATION,
                String.format("%.2f", avg));
    }
}