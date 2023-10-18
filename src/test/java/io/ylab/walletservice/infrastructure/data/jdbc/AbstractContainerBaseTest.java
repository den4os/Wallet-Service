package io.ylab.walletservice.infrastructure.data.jdbc;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractContainerBaseTest {

    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll
    public static void startContainer() {
        postgreSQLContainer.start();
    }

    @AfterAll
    public static void stopContainer() {
        postgreSQLContainer.stop();
    }
}