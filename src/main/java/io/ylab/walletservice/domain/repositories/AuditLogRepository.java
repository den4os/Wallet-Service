package io.ylab.walletservice.domain.repositories;

import io.ylab.walletservice.domain.entities.AuditLog;

import java.util.List;

/**
 * This interface defines the methods for interacting with the repository of audit log entries.
 * It provides methods for saving an audit log entry and retrieving a list of audit log entries.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public interface AuditLogRepository {

    /**
     * Saves an audit log entry to the repository.
     *
     * @param auditLog The audit log entry to be saved.
     */
    void saveAuditLog(AuditLog auditLog);

    /**
     * Retrieves a list of audit log entries from the repository.
     *
     * @return A list of audit log entries.
     */
    List<AuditLog> getAuditLogs();
}