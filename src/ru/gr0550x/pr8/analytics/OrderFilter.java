package ru.gr0550x.pr8.analytics;
public record OrderFilter(String group,
                          String namePart,
                          int minTotal,
                          boolean onlyPaid,
                          boolean excludeCancelled) {

    public static OrderFilter none() {
        return new OrderFilter("", "", 0, false, false);
    }
}
