package io.ylab.walletservice.in;

/**
 * This interface defines the methods for handling the console interface of the wallet service.
 * It provides methods for displaying the main menu and handling user input within the console.
 * Implementations of this interface are responsible for interacting with users via the console.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public interface ConsoleInterface {

    /**
     * Displays the main menu in the console.
     */
    void showMainMenu();

    /**
     * Handles user input from the main menu.
     *
     * @param choice The user's choice from the main menu.
     */
    void handleMainMenuInput(String choice);
}