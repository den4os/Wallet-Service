package com.ylab.walletservice.in;

import com.ylab.walletservice.domain.entities.Transaction;
import com.ylab.walletservice.infrastructure.services.PlayerService;
import com.ylab.walletservice.infrastructure.services.TransactionService;
import com.ylab.walletservice.domain.entities.Player;

import java.util.List;
import java.util.Scanner;

public class ConsoleUserInterface {
    private final Scanner scanner;
    private final PlayerService playerService;
    private final TransactionService transactionService;

    public ConsoleUserInterface(PlayerService playerService, TransactionService transactionService) {
        this.scanner = new Scanner(System.in);
        this.playerService = playerService;
        this.transactionService = transactionService;
    }

    public void showMainMenu() {
        System.out.println("Welcome to the Wallet Service Console App");
        System.out.println("1. Register Player");
        System.out.println("2. Authorize Player");
        System.out.println("3. Perform Transaction");
        System.out.println("4. Current balance");
        System.out.println("5. View Transaction History");
        System.out.println("6. Exit");
        System.out.print("Please select an option: ");
        System.out.println();
    }

    public void start() {
        int choice;
        do {
            showMainMenu();
            choice = getUserChoice();
            switch (choice) {
                case 1:
                    handlePlayerRegistration();
                    break;
                case 2:
                    handlePlayerAuthorization();
                    break;
                case 3:
                    handleTransaction();
                    break;
                case 4:
                    handleCurrentBalance();
                    break;
                case 5:
                    handleTransactionHistory();
                    break;
                case 6:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 6);
    }

    public int getUserChoice() {
        return scanner.nextInt();
    }

    private void handlePlayerRegistration() {
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        Player registeredPlayer = playerService.registerPlayer(username, password);
        if (registeredPlayer != null) {
            System.out.println("Player registered successfully.");
            System.out.println("Player ID: " + registeredPlayer.getPlayerId());
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }

    private void handlePlayerAuthorization() {
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Player authorizedPlayer = playerService.authorizePlayer(username, password);
        if (authorizedPlayer != null) {
            System.out.println("Authorization successful.");
            System.out.println("Player ID: " + authorizedPlayer.getPlayerId());
        } else {
            System.out.println("Authorization failed. Please check your credentials.");
        }
    }

    private void handleTransaction() {
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter your Player ID: ");
        String playerId = scanner.nextLine();

        System.out.print("Enter 'debit' or 'credit': ");
        String transactionType = scanner.nextLine().toLowerCase();

        if (transactionType.equals("debit") || transactionType.equals("credit")) {
            System.out.print("Enter transaction amount: ");
            double amount = scanner.nextDouble();

            boolean success;
            if (transactionType.equals("debit")) {
                System.out.print("Enter a unique transaction ID: ");
                scanner.nextLine();  // Consume the newline character
                String transactionId = scanner.nextLine();
                success = transactionService.performDebitTransaction(playerId, transactionId, amount);
            } else {
                System.out.print("Enter a unique transaction ID: ");
                scanner.nextLine();  // Consume the newline character
                String transactionId = scanner.nextLine();
                success = transactionService.performCreditTransaction(playerId, transactionId, amount);
            }

            if (success) {
                System.out.println("Transaction completed successfully.");
            } else {
                System.out.println("Transaction failed. Please check your inputs.");
            }
        } else {
            System.out.println("Invalid transaction type. Please enter 'debit' or 'credit'.");
        }
    }

    private void handleTransactionHistory() {
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter your Player ID: ");
        String playerId = scanner.nextLine();

        List<Transaction> transactionHistory = transactionService.getTransactionHistory(playerId);
        if (transactionHistory.isEmpty()) {
            System.out.println("No transaction history found for this player.");
        } else {
            System.out.println("Transaction History for Player ID: " + playerId);
            for (Transaction transaction : transactionHistory) {
                System.out.println("Transaction ID: " + transaction.getTransactionId());
                System.out.println("Type: " + transaction.getType());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Timestamp: " + transaction.getTimestamp());
                System.out.println("-----------------------");
            }
        }
    }

    private void handleCurrentBalance() {
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter your Player ID: ");
        String playerId = scanner.nextLine();
        double balance = playerService.getPlayerBalance(playerId);
        System.out.println("Your current balance: " + balance);
    }
}
