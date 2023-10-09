package com.ylab.walletservice.in;

import com.ylab.walletservice.domain.entities.Admin;
import com.ylab.walletservice.domain.entities.AuditLog;
import com.ylab.walletservice.domain.entities.Player;
import com.ylab.walletservice.infrastructure.services.AdminService;
import com.ylab.walletservice.infrastructure.services.AuditLogService;
import com.ylab.walletservice.infrastructure.services.PlayerService;

import java.util.List;
import java.util.Scanner;

public class ConsoleAdminInterface {
    private final Scanner scanner;
    private final AdminService adminService;
    private final AuditLogService auditLogService;
    private final PlayerService playerService;

    private Admin authorizedAdmin;

    public ConsoleAdminInterface(AdminService adminService, AuditLogService auditLogService, PlayerService playerService) {
        this.scanner = new Scanner(System.in);
        this.adminService = adminService;
        this.auditLogService = auditLogService;
        this.playerService = playerService;
    }

    public void showMainMenu() {
        System.out.println("Administrator authorization");
        System.out.println("1. Register Admin");
        System.out.println("2. Authorize Admin");
        System.out.println("3. Go back to the main page");
        System.out.print("Please select an option: ");
        System.out.println();
    }

    public void showAdminMenu() {
        System.out.println("Admin menu");
        System.out.println("1. See logs of all players");
        System.out.println("2. View player logs");
        System.out.println("3. Logout");
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
                    handleAdminRegistration();
                    break;
                case 2:
                    handleAdminAuthorization();
                    break;
                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 3);
    }

    public void adminMenu() {
        int choice;
        do {
            showAdminMenu();
            choice = getUserChoice();
            switch (choice) {
                case 1:
                    handleAuditLog();
                    break;
                case 2:
                    handlePlayerAuditLog();
                    break;
                case 3:
                    System.out.println("Logout");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 3);
    }

    public int getUserChoice() {
        return scanner.nextInt();
    }

    private void handleAdminRegistration() {
        scanner.nextLine();
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        Admin registeredAdmin = adminService.registerAdmin(username, password);
        if (registeredAdmin != null) {
            System.out.println("Admin registered successfully.");
            System.out.println("Admin ID: " + registeredAdmin.getAdminId() + "\n");
        } else {
            System.out.println("Registration failed. Please try again.\n");
        }
    }

    private void handleAdminAuthorization() {
        scanner.nextLine();
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        authorizedAdmin = adminService.authorizeAdmin(username, password);
        if (authorizedAdmin != null) {
            System.out.println("Authorization successful.");
            System.out.println("Player ID: " + authorizedAdmin.getAdminId() + "\n");
            adminMenu();
        } else {
            System.out.println("Authorization failed. Please check your credentials.\n");
        }
    }

    private void handleAuditLog() {
        printAuditLog(auditLogService.getAllAuditLogs());
    }

    private void handlePlayerAuditLog() {
        scanner.nextLine();
        System.out.print("Enter player ID: ");
        String playerId = scanner.nextLine();
        List<AuditLog> auditLogs = auditLogService.getPlayerAuditLogs(playerId);
        if (auditLogs == null) {
            System.out.println("There is no user with this ID");
        } else {
            printAuditLog(auditLogs);
        }
    }

    private void printAuditLog(List<AuditLog> auditLogs) {
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