package ru.yandex.practicum.sleeptracker.result;


public class SleepAnalysisResult {
    private final String description;
    public final Object value;

    public SleepAnalysisResult(String description, Object value) {
        this.description = description;
        this.value = value;
    }

    @Override
    public String toString() {
        return description + ": " + value;
    }
}