package io.ylab.walletservice;

import io.ylab.walletservice.connection.DatabaseConnection;
import io.ylab.walletservice.connection.PropertyReader;
import io.ylab.walletservice.domain.repositories.*;
import io.ylab.walletservice.in.*;
import io.ylab.walletservice.infrastructure.data.jdbc.JdbcAdminRepository;
import io.ylab.walletservice.infrastructure.data.jdbc.JdbcAuditLogRepository;
import io.ylab.walletservice.infrastructure.data.jdbc.JdbcPlayerRepository;
import io.ylab.walletservice.infrastructure.data.jdbc.JdbcTransactionRepository;
import io.ylab.walletservice.infrastructure.services.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

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
 * @version 1.1
 * @since 2023-10-18
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

        Properties properties = PropertyReader.readProperties("src/resources/application.properties");
        String url = properties.getProperty("url");
        String userName = properties.getProperty("username");
        String password = properties.getProperty("password");

        try (Connection connection = DatabaseConnection.connect(url, userName, password)) {

            ConsoleUserInput consoleUserInput = new ConsoleRealConsoleUserInput();

            ConsoleInterfaceManager consoleInterfaceManager = new ConsoleInterfaceManager(consoleUserInput);

            ServiceContainer serviceContainer = getServiceContainer(connection);

            ConsoleAuthorization consoleAuthorization = new ConsoleAuthorization(consoleInterfaceManager,
                    serviceContainer,
                    consoleUserInput);

            consoleInterfaceManager.pushInterface(consoleAuthorization);
            consoleInterfaceManager.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ServiceContainer getServiceContainer(Connection connection) {
        ServiceContainer serviceContainer = new ServiceContainer();

        PlayerRepository playerRepository = new JdbcPlayerRepository(connection);
        PlayerService playerService = new PlayerServiceImpl(playerRepository);

        TransactionRepository transactionRepository = new JdbcTransactionRepository(connection);
        TransactionService transactionService = new TransactionServiceImpl(playerRepository, transactionRepository);

        AdminRepository adminRepository = new JdbcAdminRepository(connection);
        AdminService adminService = new AdminServiceImpl(adminRepository);

        AuditLogRepository auditLogRepository = new JdbcAuditLogRepository(connection);
        AuditLogService auditLogService = new AuditLogServiceImpl(auditLogRepository, playerRepository);

        serviceContainer.setPlayerService(playerService);
        serviceContainer.setTransactionService(transactionService);
        serviceContainer.setAdminService(adminService);
        serviceContainer.setAuditLogService(auditLogService);
        return serviceContainer;
    }
}
