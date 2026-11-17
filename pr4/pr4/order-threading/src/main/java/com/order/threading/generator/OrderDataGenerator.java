package com.order.threading.generator;

import com.order.processing.model.Clothing;
import com.order.processing.model.Electronics;
import com.order.processing.model.Order;
import com.order.processing.model.Product;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class OrderDataGenerator {

    private final Faker faker;

    public OrderDataGenerator() {
        this.faker = new Faker(Locale.ENGLISH);
    }

    public List<Order<? extends Product>> generateOrders(int count) {
        List<Order<? extends Product>> orders = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            if (i % 2 == 0) {
                orders.add(createRandomElectronicsOrder("ORD-EL-" + i));
            } else {
                orders.add(createRandomClothingOrder("ORD-CL-" + i));
            }
        }

        return orders;
    }

    public Order<Electronics> createRandomElectronicsOrder(String orderId) {
        String deviceName = faker.commerce().productName();
        String brand = faker.company().name();
        double price = faker.number().randomDouble(2, 400, 25000);
        int warrantyMonths = faker.number().numberBetween(6, 36);
        int quantity = faker.number().numberBetween(1, 3);

        Electronics electronics = Electronics.builder()
                .id(UUID.randomUUID().toString().substring(0, 8))
                .name(deviceName)
                .price(price)
                .brand(brand)
                .warrantyMonths(warrantyMonths)
                .build();

        return Order.<Electronics>builder()
                .orderId(orderId)
                .product(electronics)
                .quantity(quantity)
                .build();
    }

    public Order<Clothing> createRandomClothingOrder(String orderId) {
        String[] clothingTypes = {"T-Shirt", "Jeans", "Hoodie", "Winter Jacket", "Sport Pants"};
        String[] sizes = {"S", "M", "L", "XL", "XXL"};
        String[] materials = {"Cotton", "Wool", "Denim", "Linen", "Polyester"};

        String name = clothingTypes[faker.number().numberBetween(0, clothingTypes.length)];
        String size = sizes[faker.number().numberBetween(0, sizes.length)];
        String material = materials[faker.number().numberBetween(0, materials.length)];
        double price = faker.number().randomDouble(2, 150, 3500);
        int quantity = faker.number().numberBetween(1, 4);

        Clothing clothing = Clothing.builder()
                .id(UUID.randomUUID().toString().substring(0, 8))
                .name(name + " (" + material + ")")
                .price(price)
                .size(size)
                .material(material)
                .build();

        return Order.<Clothing>builder()
                .orderId(orderId)
                .product(clothing)
                .quantity(quantity)
                .build();
    }

    public Order<Electronics> createInvalidOrder() {
        Electronics invalidProduct = Electronics.builder()
                .id("ERR-001")
                .name("Defective Device")
                .price(-199.99)
                .brand("Unknown")
                .warrantyMonths(0)
                .build();

        return Order.<Electronics>builder()
                .orderId("ORD-FAIL-999")
                .product(invalidProduct)
                .quantity(0)
                .build();
    }
}
