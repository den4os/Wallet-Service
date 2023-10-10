package io.ylab.walletservice;

import io.ylab.walletservice.domain.repositories.*;
import io.ylab.walletservice.in.*;
import io.ylab.walletservice.infrastructure.inmemory.*;
import io.ylab.walletservice.infrastructure.services.*;

/**
 * The main application class for the Wallet Service.
 * It initializes the necessary components and starts the console interface.
 * This class sets up the Console User Interface (CUI) for the Wallet Service.
 * It initializes various services, repositories, and user interfaces to allow players
 * and administrators to interact with the wallet system through the console.
 * The main functionality includes player authorization, performing transactions,
 * managing player data, and logging audit information.
 * To run the Wallet Service application, execute the main method within this class.
 * It sets up the required components and starts the user interface.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class Application {

    /**
     * The main entry point for the Wallet Service application.
     * This method initializes the console user input, console interface manager, and various services and repositories.
     * It then sets up player authorization, initializes the service container, and starts the console user interface.
     *
     * @param args The command line arguments (not used in this application).
     */
    public static void main(String[] args) {

        // Initialize the Console User Input
        ConsoleUserInput consoleUserInput = new ConsoleRealConsoleUserInput();

        // Initialize the Console Interface Manager
        ConsoleInterfaceManager consoleInterfaceManager = new ConsoleInterfaceManager(consoleUserInput);

        // Initialize the Service Container
        ServiceContainer serviceContainer = new ServiceContainer();

        // Initialize Player-related repositories and services
        PlayerRepository playerRepository = new InMemoryPlayerRepository();
        PlayerService playerService = new PlayerServiceImpl(playerRepository);

        // Initialize Transaction-related repositories and services
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        TransactionService transactionService = new TransactionServiceImpl(playerRepository, transactionRepository);

        // Initialize Admin-related repositories and services
        AdminRepository adminRepository = new InMemoryAdminRepository();
        AdminService adminService = new AdminServiceImpl(adminRepository);

        // Initialize Audit Log-related repositories and services
        AuditLogRepository auditLogRepository = new InMemoryAuditLogRepository();
        AuditLogService auditLogService = new AuditLogServiceImpl(auditLogRepository, playerRepository);

        // Set the services in the Service Container
        serviceContainer.setPlayerService(playerService);
        serviceContainer.setTransactionService(transactionService);
        serviceContainer.setAdminService(adminService);
        serviceContainer.setAuditLogService(auditLogService);

        // Initialize the Console Authorization interface
        ConsoleAuthorization consoleAuthorization = new ConsoleAuthorization(consoleInterfaceManager,
                serviceContainer,
                consoleUserInput);

        // Push the authorization interface to the interface manager and start the application
        consoleInterfaceManager.pushInterface(consoleAuthorization);
        consoleInterfaceManager.start();
    }
}
