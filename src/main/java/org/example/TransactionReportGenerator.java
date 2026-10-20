package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TransactionReportGenerator {

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public static void printSummaryExpensesReports(List<Transaction> transactions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MM-yyyy][dd.MM.yyyy]");

        System.out.println("\nВитрати по категоріях:");
        Map<String, Double> byCategory = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(Transaction::getDescription,
                        Collectors.summingDouble(t -> Math.abs(t.getAmount()))));

        byCategory.forEach((category, sum) ->
                System.out.println(category + ": " + sum + " грн " + "*".repeat((int) (sum / 1000))));

        System.out.println("\nВитрати по місяцях:");
        Map<String, Double> byMonth = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> LocalDate.parse(t.getDate(), formatter).format(DateTimeFormatter.ofPattern("MM-yyyy")),
                        Collectors.summingDouble(t -> Math.abs(t.getAmount()))));

        byMonth.forEach((month, sum) ->
                System.out.println(month + ": " + sum + " грн " + "*".repeat((int) (sum / 1000))));
    }
}