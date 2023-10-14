package io.ylab.walletservice.domain.entities;

import java.util.Objects;

/**
 * This class represents an admin user.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
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

    public String getAdminId() {
        return adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(adminId, admin.adminId)
                && Objects.equals(username, admin.username)
                && Objects.equals(password, admin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, username, password);
    }
}
