package io.ylab.walletservice.in;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.domain.entities.Transaction;

import java.util.List;

/**
 * This class represents the console interface for the player's personal area in the Wallet Service.
 * It allows players to perform transactions, check their current balance, view transaction history, and log out.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class ConsolePlayerMenu implements ConsoleInterface {
    private final ConsoleInterfaceManager interfaceManager;
    private final ServiceContainer serviceContainer;
    private Player authorizedPlayer;
    private final ConsoleUserInput consoleUserInput;

    /**
     * Initializes a new instance of the {@code ConsolePlayerMenu} class with the provided dependencies.
     *
     * @param interfaceManager   The manager for console interfaces.
     * @param serviceContainer  The container for various services.
     * @param authorizedPlayer  The player authorized to access this console.
     * @param consoleUserInput  The user input provider for the console.
     */
    public ConsolePlayerMenu(ConsoleInterfaceManager interfaceManager,
                             ServiceContainer serviceContainer,
                             Player authorizedPlayer,
                             ConsoleUserInput consoleUserInput) {
        this.interfaceManager = interfaceManager;
        this.serviceContainer = serviceContainer;
        this.authorizedPlayer = authorizedPlayer;
        this.consoleUserInput = consoleUserInput;
    }

    /**
     * Displays the main menu for the player's personal area.
     */
    public void showMainMenu() {
        System.out.println("Personal Area");
        System.out.println("1. Perform Transaction");
        System.out.println("2. Current balance");
        System.out.println("3. View Transaction History");
        System.out.println("4. Logout");
        System.out.print("Please select an option: ");
        System.out.println();
    }

    /**
     * Handles user input from the main menu.
     *
     * @param choice The user's choice from the main menu.
     */
    public void handleMainMenuInput(String choice) {
        switch (choice) {
            case "1" -> handleTransaction();
            case "2" -> handleCurrentBalance();
            case "3" -> handleTransactionHistory();
            case "4" -> logout();
            default -> System.out.println("Invalid choice. Please select a valid option.\n");
        }
    }

    /**
     * Handles the process of performing a transaction by the player.
     */
    private void handleTransaction() {
        String playerId = authorizedPlayer.getPlayerId();
        System.out.print("Enter 'debit' or 'credit': ");
        String transactionType = consoleUserInput.getNextLine().toLowerCase();

        if (transactionType.equals("debit") || transactionType.equals("credit")) {
            System.out.print("Enter transaction amount: ");
            double amount = Double.parseDouble(consoleUserInput.getNextLine());

            boolean success;
            if (transactionType.equals("debit")) {
                System.out.print("Enter a unique transaction ID: ");
                String transactionId = consoleUserInput.getNextLine();
                success = serviceContainer.getTransactionService().performDebitTransaction(playerId,
                        transactionId,
                        amount);
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
                String transactionId = consoleUserInput.getNextLine();
                success = serviceContainer.getTransactionService().performCreditTransaction(playerId,
                        transactionId,
                        amount);
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

    /**
     * Handles the process of viewing the transaction history for the player.
     */
    private void handleTransactionHistory() {
        String playerId = authorizedPlayer.getPlayerId();

        List<Transaction> transactionHistory = serviceContainer
                .getTransactionService()
                .getPlayerTransactionHistory(playerId);

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

    /**
     * Handles the process of checking and displaying the player's current balance.
     */
    private void handleCurrentBalance() {
        String playerId = authorizedPlayer.getPlayerId();
        double balance = serviceContainer.getPlayerService().getPlayerBalance(playerId);
        System.out.println("Your current balance: " + balance + "\n");
        serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                ActionType.PLAYER_BALANCE,
                ActionResult.SUCCESS);
    }

    /**
     * Logs out the authorized player and exits the personal area.
     */
    private void logout() {
        System.out.println("Logout");
        serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                ActionType.PLAYER_LOGOUT,
                ActionResult.SUCCESS);
        authorizedPlayer = null;
        interfaceManager.popInterface();
    }
}
