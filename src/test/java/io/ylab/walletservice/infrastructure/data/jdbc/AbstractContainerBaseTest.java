package io.ylab.walletservice.infrastructure.data.jdbc;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class AbstractContainerBaseTest {

    protected Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("testdb_" + System.currentTimeMillis()) // Уникальное имя базы данных
                .withUsername("test")
                .withPassword("test");

        postgreSQLContainer.start();

        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        connection = DriverManager.getConnection(jdbcUrl, username, password);
        connection.setAutoCommit(false);
        Liquibase liquibase = new Liquibase(
                "db/changelog/changelog.xml",
                new ClassLoaderResourceAccessor(),
                new JdbcConnection(connection)
        );
        liquibase.update((String) null);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (!connection.getAutoCommit()) {
            connection.rollback();
        }
        connection.close();
    }
}