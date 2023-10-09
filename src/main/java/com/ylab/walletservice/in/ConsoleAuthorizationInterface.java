package com.ylab.walletservice.in;

import com.ylab.walletservice.domain.entities.ActionResult;
import com.ylab.walletservice.domain.entities.ActionType;
import com.ylab.walletservice.infrastructure.services.AdminService;
import com.ylab.walletservice.infrastructure.services.AuditLogService;
import com.ylab.walletservice.infrastructure.services.PlayerService;
import com.ylab.walletservice.infrastructure.services.TransactionService;
import com.ylab.walletservice.domain.entities.Player;

import java.util.Scanner;

public class ConsoleAuthorizationInterface {
    private final Scanner scanner;
    private final PlayerService playerService;
    private final TransactionService transactionService;
    private final AdminService adminService;
    private final AuditLogService auditLogService;

    public ConsoleAuthorizationInterface(PlayerService playerService, TransactionService transactionService, AdminService adminService, AuditLogService auditLogService) {
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

    public void start() {
        int choice;
        do {
            showMainMenu();
            choice = getUserChoice();
            switch (choice) {
                case 1 -> handlePlayerRegistration();
                case 2 -> handlePlayerAuthorization();
                case 3 -> adminInterface();
                case 4 -> System.out.println("Exiting the application. Goodbye!");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 4);
    }

    public int getUserChoice() {
        return scanner.nextInt();
    }

    private void adminInterface() {
        ConsoleAdminInterface consoleAdminInterface = new ConsoleAdminInterface(adminService, auditLogService);
        consoleAdminInterface.start();
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
        scanner.nextLine();
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Player authorizedPlayer = playerService.authorizePlayer(username, password);
        if (authorizedPlayer != null) {
            System.out.println("Authorization successful.");
            System.out.println("Player ID: " + authorizedPlayer.getPlayerId() + "\n");
            auditLogService.addAuditLog(authorizedPlayer.getPlayerId(),
                    ActionType.PLAYER_AUTHORIZATION,
                    ActionResult.SUCCESS);
            playerLogin(authorizedPlayer);
        } else {
            System.out.println("Authorization failed. Please check your credentials.\n");
            auditLogService.addAuditLog("Unknown",
                    ActionType.PLAYER_AUTHORIZATION,
                    ActionResult.FAILURE);
        }
    }

    private void playerLogin(Player authorizedPlayer) {
        if (authorizedPlayer != null) {
            ConsolePlayerInterface consolePlayerInterface = new ConsolePlayerInterface(authorizedPlayer,
                    playerService,
                    transactionService,
                    auditLogService);
            consolePlayerInterface.playerMenu();
        } else {
            System.out.println("Authorisation failed.");
        }
    }
}
