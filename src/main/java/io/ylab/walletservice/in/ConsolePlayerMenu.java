package io.ylab.walletservice.in;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.domain.entities.Transaction;
import io.ylab.walletservice.infrastructure.services.AuditLogService;
import io.ylab.walletservice.infrastructure.services.PlayerService;
import io.ylab.walletservice.infrastructure.services.TransactionService;

import java.util.List;
import java.util.Scanner;

public class ConsolePlayerMenu implements ConsoleInterface {
    private ConsoleInterfaceManager interfaceManager;
    private ServiceContainer serviceContainer;
    private Player authorizedPlayer;
    private final Scanner scanner;

    public ConsolePlayerMenu(ConsoleInterfaceManager interfaceManager,
                             ServiceContainer serviceContainer,
                             Player authorizedPlayer) {
        this.interfaceManager = interfaceManager;
        this.serviceContainer = serviceContainer;
        this.authorizedPlayer = authorizedPlayer;
        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        System.out.println("Personal Area");
        System.out.println("1. Perform Transaction");
        System.out.println("2. Current balance");
        System.out.println("3. View Transaction History");
        System.out.println("4. Logout");
        System.out.print("Please select an option: ");
        System.out.println();
    }

    public void handleMainMenuInput(String choice) {
        switch (choice) {
            case "1" -> handleTransaction();
            case "2" -> handleCurrentBalance();
            case "3" -> handleTransactionHistory();
            case "4" -> logout();
            default -> {
                showMainMenu();
                System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void handleTransaction() {
        String playerId = authorizedPlayer.getPlayerId();
        System.out.print("Enter 'debit' or 'credit': ");
        String transactionType = scanner.nextLine().toLowerCase();

        if (transactionType.equals("debit") || transactionType.equals("credit")) {
            System.out.print("Enter transaction amount: ");
            double amount = scanner.nextDouble();

            boolean success;
            if (transactionType.equals("debit")) {
                System.out.print("Enter a unique transaction ID: ");
                scanner.nextLine();
                String transactionId = scanner.nextLine();
                success = serviceContainer.getTransactionService().performDebitTransaction(playerId, transactionId, amount);
                if(success) {
                    serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                            ActionType.DEBIT_TRANSACTION,
                            ActionResult.SUCCESS);
                } else {
                    serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                            ActionType.DEBIT_TRANSACTION,
                            ActionResult.FAIL);
                }
            } else {
                System.out.print("Enter a unique transaction ID: ");
                scanner.nextLine();
                String transactionId = scanner.nextLine();
                success = serviceContainer.getTransactionService().performCreditTransaction(playerId, transactionId, amount);
                if(success) {
                    serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                            ActionType.CREDIT_TRANSACTION,
                            ActionResult.SUCCESS);
                } else {
                    serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                            ActionType.CREDIT_TRANSACTION,
                            ActionResult.FAIL);
                }
            }

            if (success) {
                System.out.println("Transaction completed successfully. \n");
                serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                        ActionType.TRANSACTION,
                        ActionResult.SUCCESS);
            } else {
                System.out.println("Transaction failed. Please check your inputs. \n");
                serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                        ActionType.TRANSACTION,
                        ActionResult.FAIL);
            }
        } else {
            System.out.println("Invalid transaction type. Please enter 'debit' or 'credit'. \n");
            serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                    ActionType.TRANSACTION,
                    ActionResult.FAIL);
        }
    }

    private void handleTransactionHistory() {
        String playerId = authorizedPlayer.getPlayerId();

        List<Transaction> transactionHistory = serviceContainer.getTransactionService().getPlayerTransactionHistory(playerId);
        if (transactionHistory.isEmpty()) {
            System.out.println("No transaction history found for this player. \n");
        } else {
            System.out.println("Transaction History for Player ID: " + playerId);
            for (Transaction transaction : transactionHistory) {
                System.out.println("Transaction ID: " + transaction.getTransactionId());
                System.out.println("Type: " + transaction.getType());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Timestamp: " + transaction.getTimestamp());
                System.out.println("-----------------------");
            }
            System.out.println();
        }
        serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                ActionType.TRANSACTION_HISTORY,
                ActionResult.SUCCESS);
    }

    private void handleCurrentBalance() {
        String playerId = authorizedPlayer.getPlayerId();
        double balance = serviceContainer.getPlayerService().getPlayerBalance(playerId);
        System.out.println("Your current balance: " + balance + "\n");
        serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                ActionType.PLAYER_BALANCE,
                ActionResult.SUCCESS);
    }

    private void logout() {
        System.out.println("Logout");
        serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                ActionType.PLAYER_LOGOUT,
                ActionResult.SUCCESS);
        authorizedPlayer = null;
    }
}
