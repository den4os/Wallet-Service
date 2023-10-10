package io.ylab.walletservice.in;

import io.ylab.walletservice.domain.entities.AuditLog;

import java.util.List;

/**
 * This class represents the console interface for the admin menu in the Wallet Service.
 * It allows administrators to view and manage audit logs for players, as well as log out from the admin panel.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class ConsoleAdminMenu implements ConsoleInterface {
    private final ConsoleInterfaceManager interfaceManager;
    private final ServiceContainer serviceContainer;
    private final ConsoleUserInput consoleUserInput;

    /**
     * Initializes a new instance of the {@code ConsoleAdminMenu} class with the provided dependencies.
     *
     * @param interfaceManager   The manager for console interfaces.
     * @param serviceContainer  The container for various services.
     * @param consoleUserInput  The user input provider for the console.
     */
    public ConsoleAdminMenu(ConsoleInterfaceManager interfaceManager,
                            ServiceContainer serviceContainer,
                            ConsoleUserInput consoleUserInput) {
        this.interfaceManager = interfaceManager;
        this.serviceContainer = serviceContainer;
        this.consoleUserInput = consoleUserInput;
    }

    /**
     * Displays the main menu for the admin panel.
     */
    public void showMainMenu() {
        System.out.println("Admin menu");
        System.out.println("1. See logs of all players");
        System.out.println("2. View player logs");
        System.out.println("3. Logout");
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
            case "1" -> handleAuditLog();
            case "2" -> handlePlayerAuditLog();
            case "3" -> interfaceManager.popInterface();
            default -> {
                System.out.println("Invalid choice. Please select a valid option.\n");
            }
        }
    }

    /**
     * Handles viewing the audit log for all players.
     */
    public void handleAuditLog() {
        printAuditLog(serviceContainer.getAuditLogService().getAllAuditLogs());
    }

    /**
     * Handles viewing the audit log for a specific player.
     */
    public void handlePlayerAuditLog() {
        System.out.print("Enter player ID: ");
        String playerId = consoleUserInput.getNextLine();
        List<AuditLog> auditLogs = serviceContainer.getAuditLogService().getPlayerAuditLogs(playerId);
        if (auditLogs == null) {
            System.out.println("There is no user with this ID");
        } else {
            printAuditLog(auditLogs);
        }
    }

    /**
     * Prints the provided list of audit logs to the console.
     *
     * @param auditLogs The list of audit logs to be printed.
     */
    public void printAuditLog(List<AuditLog> auditLogs) {
        for (AuditLog auditlog : auditLogs) {
            System.out.println("Player ID: " + auditlog.getPlayerId());
            System.out.println("Action: " + auditlog.getActionType());
            System.out.println("Result: " + auditlog.getResult());
            System.out.println("Timestamp: " + auditlog.getTimestamp());
            System.out.println("-----------------------");
        }
        System.out.println();
    }
}
