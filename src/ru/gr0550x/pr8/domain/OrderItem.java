package ru.gr0550x.pr8.domain;

public record OrderItem(
        String product,
        int quantity,
        int priceRub
) {
    public int total(){
        return quantity * priceRub;
    }
}
