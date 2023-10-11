package io.ylab.walletservice.in;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.Player;

/**
 * This class represents the console interface for player authorization and registration in the Wallet Service.
 * It allows users to register as new players or authorize existing players. It also provides access to the
 * admin panel and an option to exit the application.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class ConsoleAuthorization implements ConsoleInterface {
    private final ConsoleInterfaceManager interfaceManager;
    private final ServiceContainer serviceContainer;
    private final ConsoleUserInput consoleUserInput;

    /**
     * Initializes a new instance of the {@code ConsoleAuthorization} class with the provided dependencies.
     *
     * @param interfaceManager   The manager for console interfaces.
     * @param serviceContainer  The container for various services.
     * @param consoleUserInput  The user input provider for the console.
     */
    public ConsoleAuthorization(ConsoleInterfaceManager interfaceManager,
                                ServiceContainer serviceContainer,
                                ConsoleUserInput consoleUserInput) {
        this.interfaceManager = interfaceManager;
        this.serviceContainer = serviceContainer;
        this.consoleUserInput = consoleUserInput;
    }

    /**
     * Displays the main menu for player authorization and registration.
     */
    public void showMainMenu() {
        System.out.println("Welcome to the Wallet Service Console App");
        System.out.println("1. Register Player");
        System.out.println("2. Authorize Player");
        System.out.println("3. Admin panel");
        System.out.println("4. Exit");
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
            case "1" -> handlePlayerRegistration();
            case "2" -> handlePlayerAuthorization();
            case "3" -> administratorAuthorisation();
            case "4" ->{
                System.out.println("Exiting the application. Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please select a valid option.\n");
        }
    }

    /**
     * Handles player registration process.
     */
    public void handlePlayerRegistration() {
        System.out.print("Enter a username: ");
        String username = consoleUserInput.getNextLine();
        System.out.print("Enter a password: ");
        String password = consoleUserInput.getNextLine();

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

    /**
     * Handles player authorization process.
     */
    public void handlePlayerAuthorization() {
        System.out.print("Enter your username: ");
        String username = consoleUserInput.getNextLine();
        System.out.print("Enter your password: ");
        String password = consoleUserInput.getNextLine();

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


    /**
     * Initiates the player login process.
     *
     * @param authorizedPlayer The authorized player.
     */
    public void playerLogin(Player authorizedPlayer) {
        if (authorizedPlayer != null) {
            interfaceManager.pushInterface(new ConsolePlayerMenu(interfaceManager,
                    serviceContainer,
                    authorizedPlayer,
                    consoleUserInput));
            interfaceManager.start();
        } else {
            System.out.println("Authorisation failed.");
        }
    }

    /**
     * Initiates the administrator authorization process.
     */
    public void administratorAuthorisation() {
        interfaceManager.pushInterface(new ConsoleAdminAuthorisation(interfaceManager,
                serviceContainer,
                consoleUserInput));
        interfaceManager.start();
    }
}
