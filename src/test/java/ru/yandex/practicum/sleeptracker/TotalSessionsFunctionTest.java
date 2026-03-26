package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.TotalSessionsFunction;
import ru.yandex.practicum.sleeptracker.model.Quality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalSessionsFunctionTest {

    private final TotalSessionsFunction function = new TotalSessionsFunction();

    @Test
    void emptyList_returnsZero() {
        List<SleepingSession> sessions = Collections.emptyList();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0, result.value);
    }

    @Test
    void singleSession_returnsOne() {
        List<SleepingSession> sessions = Collections.singletonList(
                TestUtils.session("01.10.25 22:15", "02.10.25 08:00", Quality.GOOD)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(1, result.value);
    }

    @Test
    void multipleSessions_returnsCorrectCount() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 22:15", "02.10.25 08:00", Quality.GOOD),
                TestUtils.session("02.10.25 23:00", "03.10.25 08:00", Quality.NORMAL),
                TestUtils.session("03.10.25 14:30", "03.10.25 15:20", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(3, result.value);
    }
}