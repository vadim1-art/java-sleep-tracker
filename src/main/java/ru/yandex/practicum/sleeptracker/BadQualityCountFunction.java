package ru.yandex.practicum.sleeptracker;

import java.util.List;


public class BadQualityCountFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long count = sessions.stream()
                .filter(s -> s.getQuality() == Quality.BAD)
                .count();
        return new SleepAnalysisResult(AnalysisDescriptions.BAD_QUALITY_COUNT, count);
    }
}