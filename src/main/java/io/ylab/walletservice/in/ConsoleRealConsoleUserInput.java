package io.ylab.walletservice.in;

import java.util.Scanner;

/**
 * This class implements the {@link ConsoleUserInput} interface to provide user input from the real console.
 * It uses the standard input (System.in) and a Scanner to read user input lines.
 * This class is responsible for retrieving user input from the console during application runtime.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class ConsoleRealConsoleUserInput implements ConsoleUserInput {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Retrieves the next line of user input from the console.
     *
     * @return The user's input line as a String.
     */
    @Override
    public String getNextLine() {
        return scanner.nextLine();
    }
}
