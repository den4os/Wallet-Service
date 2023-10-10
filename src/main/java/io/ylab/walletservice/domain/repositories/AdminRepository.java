package io.ylab.walletservice.domain.repositories;

import io.ylab.walletservice.domain.entities.Admin;

/**
 * This interface defines the methods for interacting with the repository of admin entities.
 * It provides methods for finding an admin by username and saving an admin entity.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public interface AdminRepository {

    /**
     * Finds an admin entity by the given username.
     *
     * @param username The username of the admin to find.
     * @return The admin entity if found, or null if not found.
     */
    Admin findByUsername(String username);

    /**
     * Saves an admin entity to the repository.
     *
     * @param admin The admin entity to be saved.
     */
    void save(Admin admin);
}