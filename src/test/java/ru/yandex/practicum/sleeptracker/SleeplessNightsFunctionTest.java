package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.SleeplessNightsFunction;
import ru.yandex.practicum.sleeptracker.model.Quality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightsFunctionTest {

    private final SleeplessNightsFunction function = new SleeplessNightsFunction();

    @Test
    void emptyList_returnsZero() {
        List<SleepingSession> sessions = Collections.emptyList();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.value);
    }

    @Test
    void allNightsCovered_returnsZero() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 23:00", "02.10.25 08:00", Quality.NORMAL),
                TestUtils.session("02.10.25 23:30", "03.10.25 06:20", Quality.BAD),
                TestUtils.session("04.10.25 00:30", "04.10.25 07:00", Quality.GOOD)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.value);
    }

    @Test
    void oneSleeplessNight_returnsOne() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 23:00", "02.10.25 08:00", Quality.NORMAL),
                TestUtils.session("03.10.25 23:30", "04.10.25 07:00", Quality.GOOD)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(1L, result.value);
    }

    @Test
    void partialCoverageAtEdges() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 23:30", "02.10.25 02:00", Quality.NORMAL),
                TestUtils.session("02.10.25 10:00", "02.10.25 12:00", Quality.GOOD)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.value);
    }

    @Test
    void exactBoundaryCoverage() {
        List<SleepingSession> sessions = Collections.singletonList(
                TestUtils.session("01.10.25 23:00", "02.10.25 06:00", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.value);
    }

    @Test
    void sleeplessNightWhenSessionStartsAfter6am() {
        List<SleepingSession> sessions = Collections.singletonList(
                TestUtils.session("02.10.25 13:00", "02.10.25 14:00", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.value);
    }

    @Test
    void sleeplessNightAtStartBeforeNoon() {
        List<SleepingSession> sessions = Collections.singletonList(
                TestUtils.session("02.10.25 09:00", "02.10.25 10:00", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(2L, result.value);
    }

    @Test
    void sleeplessNightAcrossMonths() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("30.09.25 23:00", "01.10.25 08:00", Quality.NORMAL),
                TestUtils.session("01.10.25 23:00", "02.10.25 08:00", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.value);
    }
}