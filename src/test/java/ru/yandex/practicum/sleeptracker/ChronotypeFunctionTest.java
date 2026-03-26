package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.functions.ChronotypeFunction;
import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.Quality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.result.SleepAnalysisResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeFunctionTest {

    private final ChronotypeFunction function = new ChronotypeFunction();

    @Test
    void noNightSessions_returnsPigeon() {
        List<SleepingSession> sessions = Collections.singletonList(
                TestUtils.session("01.10.25 14:30", "01.10.25 15:20", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(Chronotype.PIGEON, result.value);
    }

    @Test
    void allOwl_returnsOwl() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 23:30", "02.10.25 09:30", Quality.NORMAL),
                TestUtils.session("02.10.25 23:45", "03.10.25 10:00", Quality.GOOD)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(Chronotype.OWL, result.value);
    }

    @Test
    void allLark_returnsLark() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 21:30", "02.10.25 06:30", Quality.NORMAL),
                TestUtils.session("02.10.25 21:00", "03.10.25 06:00", Quality.GOOD)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(Chronotype.LARK, result.value);
    }

    @Test
    void mixedWithTie_returnsPigeon() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 23:30", "02.10.25 09:30", Quality.NORMAL),
                TestUtils.session("02.10.25 21:30", "03.10.25 06:30", Quality.NORMAL),
                TestUtils.session("03.10.25 22:30", "04.10.25 08:00", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(Chronotype.PIGEON, result.value);
    }

    @Test
    void mixedWithClearLeader_returnsLeader() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 23:30", "02.10.25 09:30", Quality.NORMAL),
                TestUtils.session("02.10.25 23:45", "03.10.25 09:30", Quality.NORMAL),
                TestUtils.session("03.10.25 21:30", "04.10.25 06:30", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(Chronotype.OWL, result.value);
    }

    @Test
    void ignoreDaySessionsAndSleeplessNights() {
        List<SleepingSession> sessions = List.of(
                TestUtils.session("01.10.25 23:30", "02.10.25 09:30", Quality.NORMAL),
                TestUtils.session("02.10.25 14:00", "02.10.25 15:00", Quality.NORMAL),
                TestUtils.session("02.10.25 21:30", "03.10.25 06:30", Quality.NORMAL),
                TestUtils.session("03.10.25 10:00", "03.10.25 12:00", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(Chronotype.PIGEON, result.value);
    }

    @Test
    void edgeTimes() {
        List<SleepingSession> sessions = Collections.singletonList(
                TestUtils.session("01.10.25 23:00", "02.10.25 09:00", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(Chronotype.PIGEON, result.value);
    }

    @Test
    void nightSessionCrossingMidnightButNotCoveringFullNightInterval() {
        List<SleepingSession> sessions = Collections.singletonList(
                TestUtils.session("01.10.25 23:30", "02.10.25 03:00", Quality.NORMAL)
        );
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(Chronotype.PIGEON, result.value);
    }
}