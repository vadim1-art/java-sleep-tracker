package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ChronotypeFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        List<SleepingSession> nightSessions = sessions.stream()
                .filter(s -> {
                    LocalDate nightDate = s.getEnd().toLocalDate();
                    LocalDateTime nightStart = nightDate.atStartOfDay();
                    LocalDateTime nightEnd = nightDate.atStartOfDay().plusHours(6);
                    return s.getStart().isBefore(nightEnd) && s.getEnd().isAfter(nightStart);
                })
                .collect(Collectors.toList());

        Map<Chronotype, Long> counts = nightSessions.stream()
                .collect(Collectors.groupingBy(this::classify, Collectors.counting()));

        long maxCount = counts.values().stream().max(Long::compareTo).orElse(0L);
        List<Chronotype> maxTypes = counts.entrySet().stream()
                .filter(e -> e.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Chronotype result = maxTypes.size() == 1 ? maxTypes.get(0) : Chronotype.PIGEON;

        return new SleepAnalysisResult("Хронотип пользователя", result);
    }


    private Chronotype classify(SleepingSession session) {
        LocalTime startTime = session.getStart().toLocalTime();
        LocalTime endTime = session.getEnd().toLocalTime();

        boolean isOwl = startTime.isAfter(LocalTime.of(23, 0)) && endTime.isAfter(LocalTime.of(9, 0));
        boolean isLark = startTime.isBefore(LocalTime.of(22, 0)) && endTime.isBefore(LocalTime.of(7, 0));

        if (isOwl) return Chronotype.OWL;
        if (isLark) return Chronotype.LARK;
        return Chronotype.PIGEON;
    }
}