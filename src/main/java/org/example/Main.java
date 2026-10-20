package org.example;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);
        TransactionReportGenerator.printBalanceReport(totalBalance);

        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        Transaction[] minMax = TransactionAnalyzer.findMinMaxExpensesInPeriod(transactions, "01-12-2023", "31-12-2023");
        System.out.println("\nНайбільша витрата за грудень: " + minMax[0]);
        System.out.println("Найменша витрата за грудень: " + minMax[1]);

        TransactionReportGenerator.printSummaryExpensesReports(transactions);
    }
}