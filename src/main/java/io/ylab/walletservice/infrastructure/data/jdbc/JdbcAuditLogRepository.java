package io.ylab.walletservice.infrastructure.data.jdbc;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.AuditLog;
import io.ylab.walletservice.domain.repositories.AuditLogRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The JdbcAuditLogRepository class provides an implementation of the AuditLogRepository interface.
 * This class handles CRUD operations for the AuditLog entity in a JDBC-compatible database.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-18
 */
public class JdbcAuditLogRepository implements AuditLogRepository {

    private final Connection connection;

    /**
     * Initializes a new JdbcAuditLogRepository instance with the given database connection.
     *
     * @param connection The SQL connection object.
     */
    public JdbcAuditLogRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Saves an AuditLog entity to the database.
     *
     * @param log The AuditLog entity to be saved.
     * @throws RuntimeException if a SQLException occurs during the operation.
     */
    @Override
    public void saveAuditLog (AuditLog log) {
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO wallet.audit_log (player_id, action_type, result, timestamp) VALUES (?, ?, ?, ?)")) {

                insertStatement.setString(1, log.getPlayerId());
                insertStatement.setString(2, log.getActionType().name());
                insertStatement.setString(3, log.getResult().name());
                insertStatement.setTimestamp(4, Timestamp.valueOf(log.getTimestamp()));
                insertStatement.executeUpdate();

                connection.commit();
            }
        } catch (SQLException e) {
            System.err.println("An error has occurred: " + e.getMessage());
            try {
                connection.rollback();
                System.err.println("Transaction canceled.");
            } catch (SQLException rollbackException) {
                System.err.println("Rollback failed: " + rollbackException.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Failed to set auto commit: " + e.getMessage());
            }
        }
    }

    /**
     * Retrieves a list of all AuditLog entries from the database.
     *
     * @return List of AuditLog entries.
     * @throws RuntimeException if a SQLException occurs during the operation.
     */
    @Override
    public List<AuditLog> getAuditLogs() {
        List<AuditLog> auditLogs = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM wallet.audit_log")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AuditLog log = new AuditLog(
                            resultSet.getString("player_id"),
                            ActionType.valueOf(resultSet.getString("action_type")),
                            ActionResult.valueOf(resultSet.getString("result")),
                            resultSet.getTimestamp("timestamp").toLocalDateTime()
                    );
                    auditLogs.add(log);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return auditLogs;
    }
}