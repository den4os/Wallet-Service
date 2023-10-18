package io.ylab.walletservice.infrastructure.data.jdbc;

import io.ylab.walletservice.domain.entities.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcAdminRepositoryTest extends AbstractContainerBaseTest {

    private JdbcAdminRepository jdbcAdminRepository;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
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