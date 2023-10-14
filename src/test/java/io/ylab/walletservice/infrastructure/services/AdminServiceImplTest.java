package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Admin;
import io.ylab.walletservice.domain.repositories.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    private AdminServiceImpl adminService;
    private AdminRepository adminRepository;

    @BeforeEach
    void setUp() {
        adminRepository = mock(AdminRepository.class);
        adminService = new AdminServiceImpl(adminRepository);
    }

    @Test
    void testRegisterAdmin() {
        Admin newAdmin = new Admin("1", "Admin", "password");
        adminService.registerAdmin("Admin", "password");
        verify(adminRepository, times(1)).save(argThat(admin ->
                admin.getUsername().equals("Admin") &&
                admin.getPassword().equals("password")));
    }

    @Test
    void testAuthorizeAdmin() {
        Admin existingAdmin = new Admin("1", "SampleAdmin", "password");
        when(adminRepository.findByUsername("SampleAdmin")).thenReturn(existingAdmin);
        Admin result = adminService.authorizeAdmin("SampleAdmin", "password");
        assertNotNull(result);
        assertEquals("SampleAdmin", result.getUsername());
    }

    @Test
    void testAuthorizeAdmin_IncorrectPassword() {
        Admin existingAdmin = new Admin("1", "SampleAdmin", "password");
        when(adminRepository.findByUsername("SampleAdmin")).thenReturn(existingAdmin);
        Admin result = adminService.authorizeAdmin("SampleAdmin", "incorrectPassword");
        assertNull(result);
    }
}