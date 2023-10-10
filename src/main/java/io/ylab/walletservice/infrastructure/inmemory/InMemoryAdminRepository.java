package io.ylab.walletservice.infrastructure.inmemory;

import io.ylab.walletservice.domain.entities.Admin;
import io.ylab.walletservice.domain.repositories.AdminRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the {@link AdminRepository} interface and provides an in-memory repository
 * for storing and retrieving admin entities.
 * It uses a {@link Map} to store admin entities with their unique IDs.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class InMemoryAdminRepository implements AdminRepository {
    private final Map<String, Admin> admins = new HashMap<>();

    /**
     * Finds an admin entity by the given username.
     *
     * @param username The username of the admin to find.
     * @return The admin entity if found, or null if not found.
     */
    @Override
    public Admin findByUsername(String username) {
        return admins.values().stream()
                .filter(admin -> admin.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves an admin entity to the in-memory repository.
     *
     * @param admin The admin entity to be saved.
     */
    @Override
    public void save(Admin admin) {
        admins.put(admin.getAdminId(), admin);
    }
}