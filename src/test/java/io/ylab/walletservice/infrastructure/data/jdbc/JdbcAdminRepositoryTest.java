package io.ylab.walletservice.infrastructure.data.jdbc;

import io.ylab.walletservice.domain.entities.Admin;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcAdminRepositoryTest extends AbstractContainerBaseTest {

    private JdbcAdminRepository jdbcAdminRepository;

    @BeforeEach
    void setUp() throws Exception {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        Liquibase liquibase = new Liquibase(
                "db/changelog/changelog.xml",
                new ClassLoaderResourceAccessor(),
                new JdbcConnection(connection)
        );
        liquibase.update((String) null);

        jdbcAdminRepository = new JdbcAdminRepository(connection);
    }

    @Test
    void testFindByUsername() {
        Admin expectedAdmin = new Admin("1", "admin", "password");
        jdbcAdminRepository.save(expectedAdmin);
        Admin actualAdmin = jdbcAdminRepository.findByUsername("admin");
        assertNotNull(actualAdmin);
        assertEquals(expectedAdmin.getUsername(), actualAdmin.getUsername());
    }

    @Test
    void testSave() {
        Admin newAdmin = new Admin("2", "newAdmin", "newPassword");
        jdbcAdminRepository.save(newAdmin);
        Admin savedAdmin = jdbcAdminRepository.findByUsername("newAdmin");
        assertNotNull(savedAdmin);
        assertEquals(newAdmin.getUsername(), savedAdmin.getUsername());
    }

    @Test
    void testSaveWithDuplicateUsername() {
        Admin firstAdmin = new Admin("3", "adminDuplicate", "password");
        Admin secondAdmin = new Admin("4", "adminDuplicate", "password2");
        jdbcAdminRepository.save(firstAdmin);
        assertThrows(RuntimeException.class, () -> jdbcAdminRepository.save(secondAdmin));
    }
}