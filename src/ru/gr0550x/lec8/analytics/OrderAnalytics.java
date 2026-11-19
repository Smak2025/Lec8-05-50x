package ru.gr0550x.lec8.analytics;

import ru.gr0550x.lec8.domain.Order;
import ru.gr0550x.lec8.domain.OrderItem;
import ru.gr0550x.lec8.domain.Student;

import java.util.List;

public final class OrderAnalytics {
    private OrderAnalytics(){}

    public static List<String> customerNames(List<Order> orders){
        return orders.stream()
                .map(Order::customer)
                .map(Student::name)
                .distinct()
                .sorted()
                .toList();
    }

    public static List<String> products(List<Order> orders){
        return orders.stream()
                .flatMap(o -> o.items().stream())
                .map(OrderItem::product)
                .distinct()
                .sorted()
                .toList();
    }
}
