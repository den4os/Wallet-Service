package io.ylab.walletservice;

import io.ylab.walletservice.connection.*;
import io.ylab.walletservice.domain.repositories.*;
import io.ylab.walletservice.in.*;
import io.ylab.walletservice.infrastructure.data.jdbc.*;
import io.ylab.walletservice.infrastructure.services.*;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.util.Properties;

/**
 * The primary class to bootstrap the Wallet Service application.
 * This class initializes all the necessary components, including various services and repositories,
 * and kicks off the Console User Interface (CUI) to allow both players and administrators to interact
 * with the wallet functionalities.
 * Main features include:
 * - Player Authorization
 * - Transaction Management
 * - Player Data Management
 * - Audit Logging
 * To start the Wallet Service application, execute the main method in this class, which sets up
 * all required components and starts the CUI.
 *
 * @author Denis Zanin
 * @version 1.1
 * @since 2023-10-18
 */
public class Application {

    /**
     * Serves as the main entry point for the Wallet Service application.
     * This method performs the following tasks:
     * - Reads configuration properties
     * - Establishes a database connection
     * - Initializes Liquibase for database version control
     * - Sets up services, repositories, and console interfaces
     * - Starts the Console User Interface (CUI) for user interactions
     *
     * @param args Command line arguments, not utilized in this specific implementation.
     */
    public static void main(String[] args) {

        Properties properties = PropertyReader.readProperties("src/main/resources/application.properties");
        String url = properties.getProperty("url");
        String userName = properties.getProperty("username");
        String password = properties.getProperty("password");
        String changeLogFile = properties.getProperty("changeLogFile");

        try (Connection connection = DatabaseConnection.connect(url, userName, password)) {

            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Liquibase liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), jdbcConnection);
            liquibase.update(new Contexts(), new LabelExpression());

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

    /**
     * Initializes and configures a {@link ServiceContainer} object, setting up all the necessary
     * services and repositories needed for the Wallet Service application.
     * Specifically, this method performs the following tasks:
     * - Initializes player, transaction, admin, and audit log repositories
     * - Sets up corresponding services with the initialized repositories
     * - Populates the ServiceContainer with these services
     *
     * @param connection The established JDBC connection to the database.
     * @return A fully-configured ServiceContainer object ready for use in the application.
     */
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
