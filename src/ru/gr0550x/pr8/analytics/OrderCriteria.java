package ru.gr0550x.pr8.analytics;

import ru.gr0550x.pr8.domain.Order;
import ru.gr0550x.pr8.domain.OrderStatus;

import java.util.Locale;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Критерии отбора заказов (FR-3).
 * Значения приходят «сырыми» из представления: null / пустая строка / OptionalInt.empty()
 * означают «фильтр не задан». Никаких if по значениям фильтров — только Optional и булева логика.
 */
public final class OrderCriteria {

    private OrderCriteria() {}

    /** Нейтральный элемент для Predicate.and: пропускает всё. */
    public static Predicate<Order> any() {
        return o -> true;
    }

    /** Все критерии соединяются через and; незаданные ничего не отсекают. */
    public static Predicate<Order> compose(String group, OrderStatus status, OptionalInt minTotal,
                                           String namePart, boolean excludeCancelled) {
        return Stream.of(
                        byGroup(group),
                        byStatus(status),
                        byMinTotal(minTotal),
                        byName(namePart),
                        byNotCancelled(excludeCancelled))
                .reduce(any(), Predicate::and);
    }

    public static Predicate<Order> byGroup(String group) {
        return Optional.ofNullable(group)
                .filter(g -> !g.isBlank())
                .<Predicate<Order>>map(g -> o -> o.customer().group().equals(g))
                .orElseGet(OrderCriteria::any);
    }

    public static Predicate<Order> byStatus(OrderStatus status) {
        return Optional.ofNullable(status)
                .<Predicate<Order>>map(s -> o -> o.status() == s)
                .orElseGet(OrderCriteria::any);
    }

    /** Короткое замыкание: если порог не задан, сравнение не выполняется. */
    public static Predicate<Order> byMinTotal(OptionalInt minTotal) {
        return o -> minTotal.isEmpty() || o.total() >= minTotal.getAsInt();
    }

    public static Predicate<Order> byName(String namePart) {
        return Optional.ofNullable(namePart)
                .map(String::strip)
                .filter(s -> !s.isEmpty())
                .map(s -> s.toLowerCase(Locale.ROOT))
                .<Predicate<Order>>map(part ->
                        o -> o.customer().name().toLowerCase(Locale.ROOT).contains(part))
                .orElseGet(OrderCriteria::any);
    }

    /** Флажок выключен -> условие истинно для любого заказа. */
    public static Predicate<Order> byNotCancelled(boolean enabled) {
        return o -> !enabled || o.status() != OrderStatus.CANCELLED;
    }
}