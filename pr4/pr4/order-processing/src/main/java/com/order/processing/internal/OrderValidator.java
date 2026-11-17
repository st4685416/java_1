package com.order.processing.internal;

import com.order.processing.exception.InvalidOrderException;
import com.order.processing.model.Order;
import com.order.processing.model.Product;

public class OrderValidator {

    private OrderValidator() {}

    public static <T extends Product> void validate(Order<T> order) throws InvalidOrderException {
        if (order == null) {
            throw new InvalidOrderException("Замовлення не може бути null!");
        }
        if (order.getOrderId() == null || order.getOrderId().isBlank()) {
            throw new InvalidOrderException("Ідентифікатор замовлення порожній!");
        }
        if (order.getProduct() == null) {
            throw new InvalidOrderException("Замовлення [" + order.getOrderId() + "] не містить товару!");
        }
        if (order.getQuantity() <= 0) {
            throw new InvalidOrderException("Замовлення [" + order.getOrderId() + "]: кількість повинна бути більшою за 0 (отримано: " + order.getQuantity() + ")!");
        }
        if (order.getProduct().getPrice() <= 0) {
            throw new InvalidOrderException("Замовлення [" + order.getOrderId() + "]: ціна товару повинна бути додатною (отримано: " + order.getProduct().getPrice() + " грн)!");
        }
    }
}
