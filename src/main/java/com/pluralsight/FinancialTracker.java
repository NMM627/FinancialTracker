package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    //scanner.close();
    public static void loadTransactions(String fileName) {
        File file = new File(fileName);
        // If the file does not exist, it should be created
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0].trim(), DATE_FORMATTER);
                    LocalTime time = LocalTime.parse(parts[1].trim(), TIME_FORMATTER);
                    String description = parts[2].trim();
                    String vendor = parts[3].trim();
                    double amount = Double.parseDouble(parts[4]);
                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("error loading");
        }
    }

    private static void addDeposit(Scanner scanner) {
        // This method should prompt the user to enter the date, time, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Deposit` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.

        System.out.println("Add Deposit");
        System.out.println("Enter the date in this format(yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
        System.out.println("Enter the time in this format(HH:mm:ss): ");
        LocalTime time = LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);
        System.out.println("Enter the vendor name in this format");
        String vendor = scanner.nextLine();
        System.out.println("Enter the description");
        String description = scanner.nextLine();
        System.out.println("Enter deposit amount");
        double amount = 0;


        transactions.add(new Transaction(date, time, description, vendor, amount));

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true));
            writer.write(date+"|"+time+"|"+description+"|"+vendor+"|"+amount);
        } catch (IOException e) {
            System.out.println("error");
        }

    }

    private static void addPayment(Scanner scanner) {

        System.out.println("Add Deposit");
        System.out.println("Enter the date in this format(yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
        System.out.println("Enter the time in this format(HH:mm:ss): ");
        LocalTime time = LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);
        System.out.println("Enter the vendor name in this format");
        String vendor = scanner.nextLine();
        System.out.println("Enter the description");
        String description = scanner.nextLine();
        System.out.println("Enter deposit amount");
        double amount = 0;

        amount=amount*-1;
        transactions.add(new Transaction(date, time, description, vendor, amount));

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true));
            writer.write(date+"|"+time+"|"+description+"|"+vendor+"|"+amount);
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) A`ll");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        System.out.println("Ledger");
        System.out.println("=============================================================================================");
        System.out.printf("%-15s %-10s %-20s %-20s %s\n", "date", "time", "description", "vendor", "amount");
        System.out.println("=============================================================================================");
        for (Transaction transaction : transactions) {
            System.out.printf("%-15s %-10s %-20s %-20s $%.2f\n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
        }
    }

    private static void displayDeposits() {
        System.out.println("Deposits");
        System.out.println("=================================================================================================");
        System.out.printf("%-15s %-10s %-20s %-20s %s\n", "date", "time", "description", "vendor", "amount");
        System.out.println("=================================================================================================");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.printf("%-15s %-10s %-20s %-20s $%.2f\n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
    }

    private static void displayPayments() {
        System.out.println("Payments");
        System.out.println("=================================================================================================");
        System.out.printf("%-15s %-10s %-20s %-20s %s\n", "date", "time", "description", "vendor", "amount");
        System.out.println("=================================================================================================");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.printf("%-15s %-10s %-20s %-20s $%.2f\n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    // Generate a report for all transactions within the current month,
                    // including the date, vendor, and amount for each transaction.
                    filterTransactionsByDate(
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024,2,1));
                    break;
                case "2":
                    // Generate a report for all transactions within the previous month,
                    // including the date, vendor, and amount for each transaction.
                    filterTransactionsByDate(
                            LocalDate.of(2024,2,1),
                            LocalDate.of(2024,3,1)
                    );
                    break;
                case "3":
                    // Generate a report for all transactions within the current year,
                    // including the date, vendor, and amount for each transaction.
                    filterTransactionsByDate(
                            LocalDate.of(2024,1,1),
                            LocalDate.of(2024,12,31)

                    );
                    break;

                case "4":
                    // Generate a report for all transactions within the previous year,
                    // including the date, vendor, and amount for each transaction.
                    filterTransactionsByDate(
                            LocalDate.of(2023,12,31),
                            LocalDate.of(2024,12,31)
                    );
                    break;

                case "5":
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, vendor, and amount for each transaction.
                    System.out.println("enter Vendor");
                    filterTransactionsByVendor(scanner.nextLine());
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {


        for(Transaction transaction : transactions){

            if(startDate.isBefore(endDate)){
                System.out.println(startDate);
            }
        }
    }

    private static void filterTransactionsByVendor(String vendor) {

        for(Transaction transaction : transactions){

            if(vendor.equals (transaction.getVendor())) {
                System.out.println(vendor);
            }
        }
    }

}
