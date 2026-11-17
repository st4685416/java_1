package com.order.storage;

import com.order.processing.model.Order;
import com.order.processing.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderStorage {

    private final BlockingQueue<Order<? extends Product>> orderQueue;

    public OrderStorage() {
        this.orderQueue = new LinkedBlockingQueue<>();
    }

    public void addOrder(Order<? extends Product> order) {
        if (order != null) {
            orderQueue.offer(order);
        }
    }

    public Optional<Order<? extends Product>> pollOrder() {
        return Optional.ofNullable(orderQueue.poll());
    }

    public List<Order<? extends Product>> getAllOrders() {
        return new ArrayList<>(orderQueue);
    }

    public int size() {
        return orderQueue.size();
    }

    public boolean isEmpty() {
        return orderQueue.isEmpty();
    }
}
