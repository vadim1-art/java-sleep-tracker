package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.BadQualityCountFunction;
import ru.yandex.practicum.sleeptracker.model.Quality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadQualityCountFunctionTest {

    private final BadQualityCountFunction function = new BadQualityCountFunction();

    @Test
    void emptyList_returnsZero() {
        List<SleepingSession> sessions = Collections.emptyList();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.value);
    }

    @Test
    void noBadSessions_returnsZero() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 22:15", "02.10.25 08:00", Quality.GOOD),
                TestUtils.session("02.10.25 23:00", "03.10.25 08:00", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.value);
    }

    @Test
    void someBadSessions_returnsCount() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 22:15", "02.10.25 08:00", Quality.GOOD),
                TestUtils.session("02.10.25 23:00", "03.10.25 08:00", Quality.BAD),
                TestUtils.session("03.10.25 14:30", "03.10.25 15:20", Quality.NORMAL),
                TestUtils.session("04.10.25 23:00", "05.10.25 07:00", Quality.BAD)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(2L, result.value);
    }

    @Test
    void allBadSessions_returnsTotalCount() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 22:15", "02.10.25 08:00", Quality.BAD),
                TestUtils.session("02.10.25 23:00", "03.10.25 08:00", Quality.BAD)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(2L, result.value);
    }
}