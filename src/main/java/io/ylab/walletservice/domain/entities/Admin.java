package io.ylab.walletservice.domain.entities;

import lombok.Data;

/**
 * This class represents an admin user.
 *
 * @author Denis Zanin
 * @version 1.1
 * @since 2023-10-10
 */

@Data
public class Admin {

    private final String adminId;
    private String username;
    private final String password;

    /**
     * Creates a new admin user with the specified parameters.
     *
     * @param adminId  The unique identifier of the admin user.
     * @param username The username of the admin user.
     * @param password The password of the admin user.
     */
    public Admin(String adminId, String username, String password) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
    }
}
