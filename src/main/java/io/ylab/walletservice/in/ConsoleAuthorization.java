package io.ylab.walletservice.in;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.Player;

import java.util.Scanner;

public class ConsoleAuthorization implements ConsoleInterface {
    private ConsoleInterfaceManager interfaceManager;
    private ServiceContainer serviceContainer;
    private final Scanner scanner;

    public ConsoleAuthorization(ConsoleInterfaceManager interfaceManager,
                                ServiceContainer serviceContainer) {
        this.interfaceManager = interfaceManager;
        this.serviceContainer = serviceContainer;
        this.scanner = new Scanner(System.in);
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

    public void handleMainMenuInput(String choice) {
        switch (choice) {
            case "1" -> handlePlayerRegistration();
            case "2" -> handlePlayerAuthorization();
            case "3" -> administratorAuthorisation();
            case "4" ->{
                System.out.println("Exiting the application. Goodbye!");
                System.exit(0);
            }
            default ->{
                showMainMenu();
                System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void handlePlayerRegistration() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        Player registeredPlayer = serviceContainer.getPlayerService().registerPlayer(username, password);
        if (registeredPlayer != null) {
            System.out.println("Player registered successfully.");
            System.out.println("Player ID: " + registeredPlayer.getPlayerId() + "\n");
            serviceContainer.getAuditLogService().addAuditLog(registeredPlayer.getPlayerId(),
                    ActionType.PLAYER_REGISTRATION,
                    ActionResult.SUCCESS);
        } else {
            System.out.println("Registration failed. Please try again.\n");
            serviceContainer.getAuditLogService().addAuditLog("Unknown",
                    ActionType.PLAYER_REGISTRATION,
                    ActionResult.FAIL);
        }
    }

    private void handlePlayerAuthorization() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Player authorizedPlayer = serviceContainer.getPlayerService().authorizePlayer(username, password);
        if (authorizedPlayer != null) {
            System.out.println("Authorization successful.");
            System.out.println("Player ID: " + authorizedPlayer.getPlayerId() + "\n");
            serviceContainer.getAuditLogService().addAuditLog(authorizedPlayer.getPlayerId(),
                    ActionType.PLAYER_AUTHORIZATION,
                    ActionResult.SUCCESS);
            playerLogin(authorizedPlayer);
        } else {
            System.out.println("Authorization failed. Please check your credentials.\n");
            serviceContainer.getAuditLogService().addAuditLog("Unknown",
                    ActionType.PLAYER_AUTHORIZATION,
                    ActionResult.FAIL);
        }
    }

    private void playerLogin(Player authorizedPlayer) {
        if (authorizedPlayer != null) {
            interfaceManager.pushInterface(new ConsolePlayerMenu(interfaceManager, serviceContainer, authorizedPlayer));
            interfaceManager.start();
        } else {
            System.out.println("Authorisation failed.");
        }
    }

    private void administratorAuthorisation() {
        interfaceManager.pushInterface(new ConsoleAdminAuthorisation(interfaceManager, serviceContainer));
        interfaceManager.start();
    }
}
