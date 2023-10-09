package com.ylab.walletservice.in;

import com.ylab.walletservice.domain.entities.ActionResult;
import com.ylab.walletservice.domain.entities.ActionType;
import com.ylab.walletservice.domain.entities.Transaction;
import com.ylab.walletservice.infrastructure.services.AdminService;
import com.ylab.walletservice.infrastructure.services.AuditLogService;
import com.ylab.walletservice.infrastructure.services.PlayerService;
import com.ylab.walletservice.infrastructure.services.TransactionService;
import com.ylab.walletservice.domain.entities.Player;

import java.util.List;
import java.util.Scanner;

public class ConsoleUserInterface {
    private final Scanner scanner;
    private final PlayerService playerService;
    private final TransactionService transactionService;
    private final AdminService adminService;
    private final AuditLogService auditLogService;
    private Player authorizedPlayer;

    public ConsoleUserInterface(PlayerService playerService, TransactionService transactionService, AdminService adminService, AuditLogService auditLogService) {
        this.scanner = new Scanner(System.in);
        this.playerService = playerService;
        this.transactionService = transactionService;
        this.adminService = adminService;
        this.auditLogService = auditLogService;
    }

    public void showMainMenu() {
        System.out.println("Welcome to the Wallet Service Console App");
        System.out.println("1. Register Player");
        System.out.println("2. Authorize Player");
        System.out.println("3. Admin panel");
        System.out.println("4. Exit");
        System.out.print("Please select an option: ");
        System.out.println();
    }

    public void showPlayerMenu() {
        System.out.println("Personal Area");
        System.out.println("1. Perform Transaction");
        System.out.println("2. Current balance");
        System.out.println("3. View Transaction History");
        System.out.println("4. Logout");
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
                    adminInterface();
                    break;
                case 4:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 4);
    }

    public void playerMenu() {
        int choice;
        do {
            showPlayerMenu();
            choice = getUserChoice();
            switch (choice) {
                case 1:
                    handleTransaction();
                    break;
                case 2:
                    handleCurrentBalance();
                    break;
                case 3:
                    handleTransactionHistory();
                    break;
                case 4:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 4);
    }

    public int getUserChoice() {
        return scanner.nextInt();
    }

    private void adminInterface() {
        ConsoleAdminInterface consoleAdminInterface = new ConsoleAdminInterface(adminService, auditLogService, playerService);
        consoleAdminInterface.start();
    }

    private int logout() {
        System.out.println("Logout");
        auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                ActionType.PLAYER_LOGOUT,
                ActionResult.SUCCESS);
        authorizedPlayer = null;
        return 4;
    }
    private void handlePlayerRegistration() {
        scanner.nextLine();
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        Player registeredPlayer = playerService.registerPlayer(username, password);
        if (registeredPlayer != null) {
            System.out.println("Player registered successfully.");
            System.out.println("Player ID: " + registeredPlayer.getPlayerId() + "\n");
            auditLogService.addAuditLog(registeredPlayer.getPlayerId(),
                    ActionType.PLAYER_REGISTRATION,
                    ActionResult.SUCCESS);
        } else {
            System.out.println("Registration failed. Please try again.\n");
            auditLogService.addAuditLog("Unknown",
                    ActionType.PLAYER_REGISTRATION,
                    ActionResult.FAILURE);
        }
    }

    private void handlePlayerAuthorization() {
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        authorizedPlayer = playerService.authorizePlayer(username, password);
        if (authorizedPlayer != null) {
            System.out.println("Authorization successful.");
            System.out.println("Player ID: " + authorizedPlayer.getPlayerId() + "\n");
            auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                    ActionType.PLAYER_AUTHORIZATION,
                    ActionResult.SUCCESS);
            playerMenu();
        } else {
            System.out.println("Authorization failed. Please check your credentials.\n");
            auditLogService.addAuditLog("Unknown",
                    ActionType.PLAYER_AUTHORIZATION,
                    ActionResult.FAILURE);
        }
    }

    private void handleTransaction() {
        String playerId = authorizedPlayer.getPlayerId();

        scanner.nextLine();
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
                if(success) {
                    auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                            ActionType.DEBIT_TRANSACTION,
                            ActionResult.SUCCESS);
                } else {
                    auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                            ActionType.DEBIT_TRANSACTION,
                            ActionResult.FAILURE);
                }
            } else {
                System.out.print("Enter a unique transaction ID: ");
                scanner.nextLine();
                String transactionId = scanner.nextLine();
                success = transactionService.performCreditTransaction(playerId, transactionId, amount);
                if(success) {
                    auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                            ActionType.CREDIT_TRANSACTION,
                            ActionResult.SUCCESS);
                } else {
                    auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                            ActionType.CREDIT_TRANSACTION,
                            ActionResult.FAILURE);
                }
            }

            if (success) {
                System.out.println("Transaction completed successfully. \n");
                auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                        ActionType.TRANSACTION,
                        ActionResult.SUCCESS);
            } else {
                System.out.println("Transaction failed. Please check your inputs. \n");
                auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                        ActionType.TRANSACTION,
                        ActionResult.FAILURE);
            }
        } else {
            System.out.println("Invalid transaction type. Please enter 'debit' or 'credit'. \n");
            auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                    ActionType.TRANSACTION,
                    ActionResult.FAILURE);
        }
    }

    private void handleTransactionHistory() {
        String playerId = authorizedPlayer.getPlayerId();

        List<Transaction> transactionHistory = transactionService.getPlayerTransactionHistory(playerId);
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
        auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                ActionType.TRANSACTION_HISTORY,
                ActionResult.SUCCESS);
    }

    private void handleCurrentBalance() {
        String playerId = authorizedPlayer.getPlayerId();
        double balance = playerService.getPlayerBalance(playerId);
        System.out.println("Your current balance: " + balance + "\n");
        auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                ActionType.PLAYER_BALANCE,
                ActionResult.SUCCESS);
    }
}
