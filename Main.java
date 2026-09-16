package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");

    static void main() {
        Scanner scanner = new Scanner(System.in);

        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        List<Product> catalog = new ArrayList<>();
        catalog.add(new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics));
        catalog.add(new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном та високою автономністю", smartphones));
        catalog.add(new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories));

        Cart cart = new Cart();
        List<Order> orderHistory = new ArrayList<>();

        while (true) {
            clearConsole();
            System.out.println("========== МЕНЮ МАГАЗИНУ ==========");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Видалити товар з кошика");
            System.out.println("4 - Переглянути кошик");
            System.out.println("5 - Зробити замовлення");
            System.out.println("6 - Історія замовлень");
            System.out.println("7 - Пошук товарів за назвою або категорією");
            System.out.println("0 - Вийти");
            System.out.print("Виберіть опцію: ");

            int choice = readInt(scanner);

            if (choice == 0) {
                clearConsole();
                System.out.println("Дякуємо, що скористалися нашим магазином! До побачення!");
                return;
            }

            clearConsole();

            switch (choice) {
                case 1:
                    System.out.println("--- Каталог товарів ---");
                    for (Product product : catalog) {
                        System.out.println(product);
                    }
                    pause(scanner);
                    break;

                case 2:
                    System.out.println("--- Додавання товару ---");
                    for (Product product : catalog) {
                        System.out.println(product.getId() + ". " + product.getName() + " — " + product.getPrice() + " грн");
                    }
                    System.out.print("\nВведіть ID товару для додавання: ");
                    int addId = readInt(scanner);
                    Product foundProduct = findProductById(catalog, addId);
                    if (foundProduct != null) {
                        cart.addProduct(foundProduct);
                        System.out.println("\nТовар \"" + foundProduct.getName() + "\" успішно додано до кошика!");
                    } else {
                        System.out.println("\nТовар з ID " + addId + " не знайдено.");
                    }
                    pause(scanner);
                    break;

                case 3:
                    System.out.println("--- Видалення товару з кошика ---");
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній.");
                    } else {
                        System.out.println(cart);
                        System.out.print("\nВведіть ID товару для видалення: ");
                        int removeId = readInt(scanner);

                        Product toRemove = null;
                        for (Product p : cart.getProducts()) {
                            if (p.getId() == removeId) {
                                toRemove = p;
                                break;
                            }
                        }

                        if (toRemove != null) {
                            cart.removeProduct(toRemove);
                            System.out.println("\nТовар \"" + toRemove.getName() + "\" видалено.");
                        } else {
                            System.out.println("\nТовар з ID " + removeId + " не знайдено у кошику.");
                        }
                    }
                    pause(scanner);
                    break;

                case 4:
                    System.out.println("--- Вміст кошика ---");
                    System.out.println(cart);
                    pause(scanner);
                    break;

                case 5:
                    System.out.println("--- Оформлення замовлення ---");
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній. Спочатку додайте товари.");
                    } else {
                        Order order = new Order(cart);
                        orderHistory.add(order);
                        System.out.println("Замовлення успішно створено:\n");
                        System.out.println(order);
                        cart.clear();
                    }
                    pause(scanner);
                    break;

                case 6:
                    System.out.println("--- Історія замовлень ---");
                    if (orderHistory.isEmpty()) {
                        System.out.println("У вас ще немає оформлених замовлень.");
                    } else {
                        int index = 1;
                        for (Order order : orderHistory) {
                            System.out.println("Замовлення #" + index++);
                            System.out.println(order);
                        }
                    }
                    pause(scanner);
                    break;

                case 7:
                    System.out.println("--- Пошук товарів ---");
                    System.out.print("Введіть назву або категорію для пошуку: ");
                    String keyword = scanner.nextLine().trim().toLowerCase();
                    boolean foundAny = false;

                    System.out.println("\nРезультати пошуку:");
                    for (Product product : catalog) {
                        boolean matchName = product.getName().toLowerCase().contains(keyword);
                        boolean matchCategory = product.getCategory() != null &&
                                product.getCategory().getName().toLowerCase().contains(keyword);

                        if (matchName || matchCategory) {
                            System.out.println(product);
                            foundAny = true;
                        }
                    }
                    if (!foundAny) {
                        System.out.println("Нічого не знайдено за запитом: \"" + keyword + "\".");
                    }
                    pause(scanner);
                    break;

                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                    pause(scanner);
                    break;
            }
        }
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void pause(Scanner scanner) {
        System.out.print("\nНатисніть Enter, щоб повернутися в головне меню...");
        scanner.nextLine();
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (DIGIT_PATTERN.matcher(input).matches()) {
                return Integer.parseInt(input);
            }
            System.out.print("Помилка: введіть число: ");
        }
    }

    private static Product findProductById(List<Product> catalog, int id) {
        for (Product product : catalog) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}