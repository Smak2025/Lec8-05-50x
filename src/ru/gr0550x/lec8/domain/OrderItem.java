package ru.gr0550x.lec8.domain;

public record OrderItem(
        String product,
        int quantity,
        int price
) {
    public int total(){
        return quantity * price;
    }
}
