package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.result.ConstantsDescriptions;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;


public class TotalSessionsFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult(ConstantsDescriptions.TOTAL_SESSIONS, sessions.size());
    }
}