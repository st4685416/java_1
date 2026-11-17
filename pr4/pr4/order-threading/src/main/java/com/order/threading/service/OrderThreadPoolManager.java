package com.order.threading.service;

import com.order.processing.model.Order;
import com.order.processing.model.Product;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class OrderThreadPoolManager {

    private final ExecutorService executor;

    public OrderThreadPoolManager(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }

    public void submitOrder(Order<? extends Product> order, Consumer<Order<? extends Product>> processor) {
        executor.execute(() -> processor.accept(order));
    }

    public void stop(long timeoutSeconds) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(timeoutSeconds, TimeUnit.SECONDS)) {
                System.err.println("Час очікування сплив, зупиняємо потоки примусово...");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
