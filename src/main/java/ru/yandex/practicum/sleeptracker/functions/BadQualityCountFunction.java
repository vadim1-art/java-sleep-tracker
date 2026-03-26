package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.Quality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.result.ConstantsDescriptions;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;

import java.util.List;


public class BadQualityCountFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long count = sessions.stream()
                .filter(s -> s.getQuality() == Quality.BAD)
                .count();
        return new SleepAnalysisResult(ConstantsDescriptions.BAD_QUALITY_COUNT, count);
    }
}