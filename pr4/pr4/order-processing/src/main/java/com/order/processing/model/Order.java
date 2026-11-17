package com.order.processing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order<T extends Product> {
    private String orderId;
    private T product;
    private int quantity;

    public double getTotalAmount() {
        return product != null ? product.getPrice() * quantity : 0.0;
    }
}
