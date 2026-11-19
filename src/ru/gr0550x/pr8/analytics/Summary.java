package ru.gr0550x.pr8.analytics;

public record Summary(long count, int total, double average) {
    public boolean isEmpty() {
        return count == 0;
    }
}
