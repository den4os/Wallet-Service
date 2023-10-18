package io.ylab.walletservice.infrastructure.data.jdbc;

import io.ylab.walletservice.domain.entities.Admin;
import io.ylab.walletservice.domain.repositories.AdminRepository;

import java.sql.*;

public class JdbcAdminRepository implements AdminRepository {

    private final Connection connection;

    public JdbcAdminRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Admin findByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM wallet.admin WHERE username = ?")) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Admin(
                            resultSet.getString("admin_id"),
                            resultSet.getString("username"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void save(Admin admin) {
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO wallet.admin (username, password) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                insertStatement.setString(1, admin.getUsername());
                insertStatement.setString(2, admin.getPassword());
                insertStatement.executeUpdate();

                try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        admin.setAdminId(Integer.toString(generatedKeys.getInt(1)));
                        connection.commit();
                    } else {
                        System.err.println("Creating admin failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.err.println("Transaction canceled.");
            } catch (SQLException rollbackException) {
                System.err.println("Rollback failed: " + rollbackException.getMessage());
            }
            throw new RuntimeException("An error has occurred: " + e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Failed to set auto commit: " + e.getMessage());
            }
        }
    }

    @Override
    public String generateUniqueAdminId() {
        return null;
    }
}
