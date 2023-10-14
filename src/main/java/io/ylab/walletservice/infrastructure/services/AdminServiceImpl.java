package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Admin;
import io.ylab.walletservice.domain.repositories.AdminRepository;

/**
 * This class implements the {@link AdminService} interface and provides functionality
 * for managing administrative users in the system.
 * It allows for the registration of new admin users and authorization of existing ones.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private int playerIdCount = 1;

    /**
     * Initializes a new instance of the {@code AdminServiceImpl} class with the provided admin repository.
     *
     * @param adminRepository The repository used to store and retrieve admin entities.
     */
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Registers a new admin user with the given username and password.
     *
     * @param username The username for the new admin.
     * @param password The password for the new admin.
     * @return The registered admin entity, or null if registration fails (e.g., duplicate username).
     */
    @Override
    public Admin registerAdmin(String username, String password) {
        Admin existingAdmin = adminRepository.findByUsername(username);
        if (existingAdmin != null) {
            return null;
        }

        String adminId = generateUniqueAdminId();
        Admin newAdmin = new Admin(adminId, username, password);
        adminRepository.save(newAdmin);

        return newAdmin;
    }

    /**
     * Generates a unique admin ID for a newly registered admin user.
     *
     * @return A unique admin ID.
     */
    private String generateUniqueAdminId() {
        playerIdCount += playerIdCount;
        return Integer.toString(playerIdCount);
    }

    /**
     * Authorizes an admin user with the provided username and password.
     *
     * @param username The username of the admin to authorize.
     * @param password The password of the admin to authorize.
     * @return The authorized admin entity if valid credentials are provided, or null if not authorized.
     */
    @Override
    public Admin authorizeAdmin(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);

        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }

        return null;
    }
}