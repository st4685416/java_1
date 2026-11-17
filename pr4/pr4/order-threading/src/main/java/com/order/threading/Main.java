package com.order.threading;

import com.order.processing.exception.InvalidOrderException;
import com.order.processing.model.Clothing;
import com.order.processing.model.Electronics;
import com.order.processing.model.Order;
import com.order.processing.model.Product;
import com.order.processing.service.OrderProcessor;
import com.order.storage.OrderStorage;
import com.order.threading.generator.OrderDataGenerator;
import com.order.threading.service.OrderThreadPoolManager;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("   БАГАТОПОТОКОВА СИСТЕМА УПРАВЛІННЯ ЗАМОВЛЕННЯМИ (JPMS + STREAMS + THREADS)    ");
        System.out.println("================================================================================\n");

        System.out.println("--- [1] ГЕНЕРАЦІЯ ТА ЗБЕРЕЖЕННЯ ЗАМОВЛЕНЬ ---");
        OrderDataGenerator generator = new OrderDataGenerator();
        OrderStorage storage = new OrderStorage();

        List<Order<? extends Product>> generatedOrders = generator.generateOrders(6);
        generatedOrders.forEach(storage::addOrder);

        Order<Electronics> invalidOrder = generator.createInvalidOrder();
        storage.addOrder(invalidOrder);

        System.out.printf("Успішно згенеровано та поміщено в чергу сховища: %d замовлень.%n%n", storage.size());

        System.out.println("--- [2] ФІЛЬТРАЦІЯ ЗА ДОПОМОГОЮ STREAM API ТА ПОСИЛАННЯ НА МЕТОДИ ---");
        List<Order<? extends Product>> allOrders = storage.getAllOrders();

        System.out.println(">> Відфільтровані замовлення ЕЛЕКТРОНІКИ:");
        List<Order<? extends Product>> electronicsOrders = allOrders.stream()
                .filter(order -> order.getProduct() instanceof Electronics)
                .toList();

        electronicsOrders.forEach(System.out::println);

        System.out.println("\n>> Відфільтровані замовлення ОДЯГУ:");
        List<Order<? extends Product>> clothingOrders = allOrders.stream()
                .filter(order -> order.getProduct() instanceof Clothing)
                .toList();

        clothingOrders.forEach(System.out::println);
        System.out.println();

        System.out.println("--- [3] ТИПІЗОВАНИЙ ОБРОБНИК GENERICS (OrderProcessor<Electronics>) ---");
        OrderProcessor<Electronics> electronicsProcessor = new OrderProcessor<>();
        Order<Electronics> singleElectronics = generator.createRandomElectronicsOrder("ORD-SINGLE-01");

        try {
            electronicsProcessor.process(singleElectronics);
        } catch (InvalidOrderException e) {
            System.err.println("Помилка обробки: " + e.getMessage());
        }
        System.out.println();

        System.out.println("--- [4] ПАРАЛЕЛЬНА ОБРОБКА В ПУЛІ ПОТОКІВ (3 РОБОЧИХ ПОТОКИ) ---");
        int threadPoolSize = 3;
        OrderThreadPoolManager poolManager = new OrderThreadPoolManager(threadPoolSize);

        List<Order<? extends Product>> ordersToProcess = storage.getAllOrders();

        for (Order<? extends Product> order : ordersToProcess) {
            poolManager.submitOrder(order, OrderProcessor::processOrder);
        }

        poolManager.stop(5);

        System.out.println("\n================================================================================");
        System.out.println("   УСІ ЗАДАЧІ ЗАВЕРШЕНО! ДЯКУЄМО ЗА РОБОТУ З СИСТЕМОЮ ЗАМОВЛЕНЬ!               ");
        System.out.println("================================================================================");
    }
}
