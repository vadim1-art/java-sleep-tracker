package ru.yandex.practicum.sleeptracker;

import java.util.List;


public class TotalSessionsFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult(AnalysisDescriptions.TOTAL_SESSIONS, sessions.size());
    }
}