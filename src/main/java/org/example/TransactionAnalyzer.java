package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("[dd-MM-yyyy][dd.MM.yyyy]");

    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate(), DATE_FORMATTER);
            String transactionMonthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static Transaction[] findMinMaxExpensesInPeriod(List<Transaction> transactions, String startStr, String endStr) {
        LocalDate start = LocalDate.parse(startStr, DATE_FORMATTER);
        LocalDate end = LocalDate.parse(endStr, DATE_FORMATTER);

        List<Transaction> periodExpenses = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate d = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                    return !d.isBefore(start) && !d.isAfter(end);
                })
                .sorted(Comparator.comparing(Transaction::getAmount))
                .collect(Collectors.toList());

        if (periodExpenses.isEmpty()) return new Transaction[]{null, null};
        return new Transaction[]{periodExpenses.get(0), periodExpenses.get(periodExpenses.size() - 1)};
    }
}