package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Order {
    private final List<Product> products;
    private final double totalPrice;

    @Setter
    private String status;

    public Order(Cart cart) {
        this.products = new ArrayList<>(cart.getProducts());
        this.totalPrice = cart.getTotalPrice();
        this.status = "Нове";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("=== Замовлення ===\n");
        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }
        sb.append(String.format("Загальна сума: %.2f грн\n", totalPrice));
        sb.append("Статус: ").append(status).append("\n");
        return sb.toString();
    }
}