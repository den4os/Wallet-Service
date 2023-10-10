package io.ylab.walletservice.in;

import java.util.Scanner;
import java.util.Stack;

public class ConsoleInterfaceManager {
    private final Stack<ConsoleInterface> interfaceStack = new Stack<>();
    private final Scanner scanner = new Scanner(System.in);

    public void pushInterface(ConsoleInterface consoleInterface) {
        interfaceStack.push(consoleInterface);
    }

    public void popInterface() {
        if (!interfaceStack.isEmpty()) {
            interfaceStack.pop();
        }
    }

    public void start() {
        while (!interfaceStack.isEmpty()) {
            ConsoleInterface currentInterface = interfaceStack.peek();
            currentInterface.showMainMenu();
            String choice = scanner.nextLine();
            currentInterface.handleMainMenuInput(choice);
        }
    }
}
