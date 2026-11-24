package ru.gr0550x.pr8.analytics;

import ru.gr0550x.pr8.domain.Order;
import ru.gr0550x.pr8.domain.OrderItem;
import ru.gr0550x.pr8.domain.Student;

import java.util.Comparator;
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
    public static List<String> groupNumbers(List<Order> orders){
        return orders.stream()
                .map(o->o.customer().group())
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

    public static List<Order> listOrdersByGroup(List<Order> orders, String group){
        return orders.stream()
                .filter(o -> o.customer().group().equals(group))
                .toList();
    }

    public static List<Order> topNByTotal(List<Order> orders, int n){
        return orders.stream()
                .sorted(Comparator.comparingInt(Order::total).reversed().thenComparingLong(Order::id))
                .limit(n)
                .toList();
    }

    public static int quantityOf(List<Order> orders, String product){
        return orders.stream()
                .flatMap(o -> o.items().stream())
                .filter(item -> item.product().equals(product))
                .mapToInt(OrderItem::quantity)
                .sum();
    }
}
