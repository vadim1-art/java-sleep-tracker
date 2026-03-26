package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;
import java.util.function.Function;


@FunctionalInterface
public interface SleepAnalysisFunction extends Function<List<SleepingSession>, SleepAnalysisResult> {
}