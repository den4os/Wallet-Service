package io.ylab.walletservice.in;

import io.ylab.walletservice.domain.entities.AuditLog;

import java.util.List;
import java.util.Scanner;

public class ConsoleAdminMenu implements ConsoleInterface {
    private ConsoleInterfaceManager interfaceManager;
    private ServiceContainer serviceContainer;
    private final Scanner scanner;

    public ConsoleAdminMenu(ConsoleInterfaceManager interfaceManager,
                            ServiceContainer serviceContainer) {
        this.interfaceManager = interfaceManager;
        this.serviceContainer = serviceContainer;
        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        System.out.println("Admin menu");
        System.out.println("1. See logs of all players");
        System.out.println("2. View player logs");
        System.out.println("3. Logout");
        System.out.print("Please select an option: ");
        System.out.println();
    }

    public void handleMainMenuInput(String choice) {
        switch (choice) {
            case "1" -> handleAuditLog();
            case "2" -> handlePlayerAuditLog();
            case "3" -> interfaceManager.popInterface();
            default -> {
                showMainMenu();
                System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    public void handleAuditLog() {
        printAuditLog(serviceContainer.getAuditLogService().getAllAuditLogs());
    }

    public void handlePlayerAuditLog() {
        System.out.print("Enter player ID: ");
        String playerId = scanner.nextLine();
        List<AuditLog> auditLogs = serviceContainer.getAuditLogService().getPlayerAuditLogs(playerId);
        if (auditLogs == null) {
            System.out.println("There is no user with this ID");
        } else {
            printAuditLog(auditLogs);
        }
    }

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
