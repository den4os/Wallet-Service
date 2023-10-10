package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Admin;

/**
 * This interface defines the methods for managing administrative users in the system.
 * It provides methods for registering a new admin and authorizing an existing admin.
 * Admins are responsible for managing and overseeing the system's functionality.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public interface AdminService {

    /**
     * Registers a new admin user with the given username and password.
     *
     * @param username The username for the new admin.
     * @param password The password for the new admin.
     * @return The registered admin entity.
     */
    Admin registerAdmin(String username, String password);

    /**
     * Authorizes an admin user with the provided username and password.
     *
     * @param username The username of the admin to authorize.
     * @param password The password of the admin to authorize.
     * @return The authorized admin entity if valid credentials are provided, or null if not authorized.
     */
    Admin authorizeAdmin(String username, String password);
}