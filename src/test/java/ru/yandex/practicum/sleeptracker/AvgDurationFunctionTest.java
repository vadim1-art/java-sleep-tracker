package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.AvgDurationFunction;
import ru.yandex.practicum.sleeptracker.model.Quality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvgDurationFunctionTest {

    private final AvgDurationFunction function = new AvgDurationFunction();

    @Test
    void emptyList_returnsZero() {
        List<SleepingSession> sessions = Collections.emptyList();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals("0.00", result.value);
    }

    @Test
    void singleSession_returnsItsDuration() {
        List<SleepingSession> sessions = Collections.singletonList(
                TestUtils.session("01.10.25 22:15", "02.10.25 08:00", Quality.GOOD)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals("585.00", result.value);
    }

    @Test
    void multipleSessions_returnsCorrectAverage() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 22:15", "02.10.25 08:00", Quality.GOOD),
                TestUtils.session("02.10.25 23:00", "03.10.25 08:00", Quality.NORMAL),
                TestUtils.session("03.10.25 14:30", "03.10.25 15:20", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals("391.67", result.value);
    }

    @Test
    void averageWithZeroDuration_handlesCorrectly() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 22:15", "02.10.25 08:00", Quality.GOOD),
                TestUtils.session("02.10.25 23:00", "02.10.25 23:00", Quality.BAD)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals("292.50", result.value);
    }
}