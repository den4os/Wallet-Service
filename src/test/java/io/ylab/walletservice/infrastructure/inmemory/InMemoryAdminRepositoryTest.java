package io.ylab.walletservice.infrastructure.inmemory;

import io.ylab.walletservice.domain.entities.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryAdminRepositoryTest {

    private InMemoryAdminRepository adminRepository;

    @BeforeEach
    void setUp() {
        adminRepository = new InMemoryAdminRepository();
    }

    @Test
    void testFindByUsername() {
        Admin admin1 = new Admin("1", "Admin1", "password1");
        Admin admin2 = new Admin("2", "Admin2", "password2");

        adminRepository.save(admin1);
        adminRepository.save(admin2);

        Admin result = adminRepository.findByUsername("Admin1");

        assertNotNull(result);
        assertEquals("Admin1", result.getUsername());
    }

    @Test
    void testSave() {
        Admin admin = new Admin("1", "Admin", "password");

        adminRepository.save(admin);

        Admin result = adminRepository.findByUsername("Admin");

        assertNotNull(result);
        assertEquals("Admin", result.getUsername());
    }
}