package io.ylab.walletservice.in;

/**
 * This interface defines a method for retrieving user input from the console.
 * Implementations of this interface are responsible for providing user input data
 * during application runtime.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public interface ConsoleUserInput {

    /**
     * Retrieves the next line of user input from the console.
     *
     * @return The user's input line as a String.
     */
    String getNextLine();
}