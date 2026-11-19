package ru.gr0550x.pr8.domain;

/** Статус заказа. title() — человекочитаемое имя для таблицы и списка (без Swing). */
public enum OrderStatus {
    NEW("Новый"),
    PAID("Оплачен"),
    SHIPPED("Отгружен"),
    CANCELLED("Отменён");

    private final String title;

    OrderStatus(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }
}
