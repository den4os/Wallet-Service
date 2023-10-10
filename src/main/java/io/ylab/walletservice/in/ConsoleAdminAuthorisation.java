package io.ylab.walletservice.in;

import io.ylab.walletservice.domain.entities.Admin;

/**
 * This class represents the console interface for administrator authorization and registration in the Wallet Service.
 * It allows administrators to register as new admins or authorize existing admins. It also provides an option to return
 * to the main menu.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class ConsoleAdminAuthorisation implements ConsoleInterface {

    private final ConsoleInterfaceManager interfaceManager;
    private final ServiceContainer serviceContainer;
    private final ConsoleUserInput consoleUserInput;

    /**
     * Initializes a new instance of the {@code ConsoleAdminAuthorisation} class with the provided dependencies.
     *
     * @param interfaceManager   The manager for console interfaces.
     * @param serviceContainer  The container for various services.
     * @param consoleUserInput  The user input provider for the console.
     */
    public ConsoleAdminAuthorisation(ConsoleInterfaceManager interfaceManager,
                                     ServiceContainer serviceContainer,
                                     ConsoleUserInput consoleUserInput) {
        this.interfaceManager = interfaceManager;
        this.serviceContainer = serviceContainer;
        this.consoleUserInput = consoleUserInput;
    }

    /**
     * Displays the main menu for administrator authorization and registration.
     */
    public void showMainMenu() {
        System.out.println("Administrator authorization");
        System.out.println("1. Register Admin");
        System.out.println("2. Authorize Admin");
        System.out.println("3. Go back to the main page");
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
            case "1" -> handleAdminRegistration();
            case "2" -> handleAdminAuthorization();
            case "3" -> interfaceManager.popInterface();
            default -> {
                System.out.println("Invalid choice. Please select a valid option.\n");
            }
        }
    }

    /**
     * Handles admin registration process.
     */
    public void handleAdminRegistration() {
        System.out.print("Enter a username: ");
        String username = consoleUserInput.getNextLine();
        System.out.print("Enter a password: ");
        String password = consoleUserInput.getNextLine();

        Admin registeredAdmin = serviceContainer.getAdminService().registerAdmin(username, password);
        if (registeredAdmin != null) {
            System.out.println("Admin registered successfully.");
            System.out.println("Admin ID: " + registeredAdmin.getAdminId() + "\n");
        } else {
            System.out.println("Registration failed. Please try again.\n");
        }
    }

    /**
     * Handles admin authorization process.
     */
    public void handleAdminAuthorization() {
        System.out.print("Enter your username: ");
        String username = consoleUserInput.getNextLine();
        System.out.print("Enter your password: ");
        String password = consoleUserInput.getNextLine();

        Admin authorizedAdmin = serviceContainer.getAdminService().authorizeAdmin(username, password);
        if (authorizedAdmin != null) {
            System.out.println("Authorization successful.");
            System.out.println("Player ID: " + authorizedAdmin.getAdminId() + "\n");
            adminLogin(authorizedAdmin);
        } else {
            System.out.println("Authorization failed. Please check your credentials.\n");
        }
    }

    /**
     * Initiates the administrator login process.
     *
     * @param authorizedAdmin The authorized admin.
     */
    private void adminLogin(Admin authorizedAdmin) {
        if (authorizedAdmin != null) {
            interfaceManager.pushInterface(new ConsoleAdminMenu(interfaceManager, serviceContainer, consoleUserInput));
            interfaceManager.start();
        } else {
            System.out.println("Authorisation failed.");
        }
    }
}