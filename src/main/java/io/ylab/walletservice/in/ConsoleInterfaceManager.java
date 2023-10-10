package io.ylab.walletservice.in;

import java.util.Stack;

/**
 * This class manages the stack of console interfaces and user input for the wallet service.
 * It allows for pushing and popping interfaces onto the stack, starting the console interface loop,
 * and handling user input for the active interface.
 * The stack of interfaces enables navigation between different parts of the application.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class ConsoleInterfaceManager {

    private final Stack<ConsoleInterface> interfaceStack = new Stack<>();
    private final ConsoleUserInput consoleUserInput;

    /**
     * Initializes a new instance of the {@code ConsoleInterfaceManager} class with the provided UserInput.
     *
     * @param consoleUserInput The UserInput instance used to retrieve user input.
     */
    public ConsoleInterfaceManager(ConsoleUserInput consoleUserInput) {
        this.consoleUserInput = consoleUserInput;
    }

    /**
     * Pushes a new console interface onto the stack.
     *
     * @param consoleInterface The console interface to push onto the stack.
     */
    public void pushInterface(ConsoleInterface consoleInterface) {
        interfaceStack.push(consoleInterface);
    }

    /**
     * Pops the top console interface from the stack.
     * This allows navigation back to the previous interface.
     */
    public void popInterface() {
        if (!interfaceStack.isEmpty()) {
            interfaceStack.pop();
        }
    }

    /**
     * Starts the console interface loop, displaying the main menu of the active interface
     * and handling user input until the stack of interfaces is empty.
     */
    public void start() {
        while (!interfaceStack.isEmpty()) {
            ConsoleInterface currentInterface = interfaceStack.peek();
            currentInterface.showMainMenu();
            String choice = consoleUserInput.getNextLine();
            currentInterface.handleMainMenuInput(choice);
        }
    }
}
