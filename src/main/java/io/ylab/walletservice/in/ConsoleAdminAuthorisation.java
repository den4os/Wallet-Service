package io.ylab.walletservice.in;

import io.ylab.walletservice.domain.entities.Admin;

import java.util.Scanner;

public class ConsoleAdminAuthorisation implements ConsoleInterface {

    private ConsoleInterfaceManager interfaceManager;
    private ServiceContainer serviceContainer;
    private final Scanner scanner;

    public ConsoleAdminAuthorisation(ConsoleInterfaceManager interfaceManager,
                                     ServiceContainer serviceContainer) {
        this.interfaceManager = interfaceManager;
        this.serviceContainer = serviceContainer;
        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        System.out.println("Administrator authorization");
        System.out.println("1. Register Admin");
        System.out.println("2. Authorize Admin");
        System.out.println("3. Go back to the main page");
        System.out.print("Please select an option: ");
        System.out.println();
    }

    public void handleMainMenuInput(String choice) {
        switch (choice) {
            case "1" -> handleAdminRegistration();
            case "2" -> handleAdminAuthorization();
            case "3" -> interfaceManager.popInterface();
            default -> {
                showMainMenu();
                System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    public void handleAdminRegistration() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        Admin registeredAdmin = serviceContainer.getAdminService().registerAdmin(username, password);
        if (registeredAdmin != null) {
            System.out.println("Admin registered successfully.");
            System.out.println("Admin ID: " + registeredAdmin.getAdminId() + "\n");
        } else {
            System.out.println("Registration failed. Please try again.\n");
        }
    }

    public void handleAdminAuthorization() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Admin authorizedAdmin = serviceContainer.getAdminService().authorizeAdmin(username, password);
        if (authorizedAdmin != null) {
            System.out.println("Authorization successful.");
            System.out.println("Player ID: " + authorizedAdmin.getAdminId() + "\n");
            adminLogin(authorizedAdmin);
        } else {
            System.out.println("Authorization failed. Please check your credentials.\n");
        }
    }

    private void adminLogin(Admin authorizedAdmin) {
        if (authorizedAdmin != null) {
            interfaceManager.pushInterface(new ConsoleAdminMenu(interfaceManager, serviceContainer));
            interfaceManager.start();
        } else {
            System.out.println("Authorisation failed.");
        }
    }
}