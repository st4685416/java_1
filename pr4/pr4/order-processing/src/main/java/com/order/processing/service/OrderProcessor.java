package com.order.processing.service;

import com.order.processing.exception.InvalidOrderException;
import com.order.processing.internal.OrderValidator;
import com.order.processing.model.Order;
import com.order.processing.model.Product;

public class OrderProcessor<T extends Product> {

    public void process(Order<T> order) throws InvalidOrderException {
        OrderValidator.validate(order);

        String threadName = Thread.currentThread().getName();
        T product = order.getProduct();

        System.out.printf("[%s] [ОБРОБКА] Замовлення #%s: %s (К-сть: %d, Сума: %.2f грн)%n",
                threadName,
                order.getOrderId(),
                product.getName(),
                order.getQuantity(),
                order.getTotalAmount());

        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.printf("[%s] Потік перервано під час обробки замовлення #%s%n", threadName, order.getOrderId());
        }
    }

    public static void processOrder(Order<? extends Product> order) {
        String threadName = Thread.currentThread().getName();
        try {
            OrderValidator.validate(order);

            System.out.printf("[%s] [СТАРТ] #%s: %-25s | Разом: %8.2f грн%n",
                    threadName,
                    order.getOrderId(),
                    order.getProduct().getName(),
                    order.getTotalAmount());

            Thread.sleep(100);

            System.out.printf("[%s] [ГОТОВО] #%s успішно збережено та відправлено!%n",
                    threadName,
                    order.getOrderId());

        } catch (InvalidOrderException e) {
            System.err.printf("[%s] [ПОМИЛКА ВАЛІДАЦІЇ] #%s: %s%n",
                    threadName,
                    order != null ? order.getOrderId() : "null",
                    e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
