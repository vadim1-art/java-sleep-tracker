package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    private static final List<SleepAnalysisFunction> ANALYSIS_FUNCTIONS = Arrays.asList(
            new TotalSessionsFunction(),
            new MinDurationFunction(),
            new MaxDurationFunction(),
            new AvgDurationFunction(),
            new BadQualityCountFunction(),
            new SleeplessNightsFunction(),
            new ChronotypeFunction()
    );

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Использование: java SleepTrackerApp <файл_лога_сна>");
            System.exit(1);
        }

        Path filePath = Path.of(args[0]);
        List<SleepingSession> sessions;
        try {
            sessions = Files.lines(filePath)
                    .filter(line -> !line.trim().isEmpty())
                    .map(SleepTrackerApp::parseSession)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        ANALYSIS_FUNCTIONS.stream()
                .map(f -> f.apply(sessions))
                .forEach(System.out::println);
    }

    private static SleepingSession parseSession(String line) {
        String[] parts = line.split(";");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат строки: " + line);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
        LocalDateTime end = LocalDateTime.parse(parts[1], formatter);
        Quality quality = Quality.valueOf(parts[2]);
        return new SleepingSession(start, end, quality);
    }
}