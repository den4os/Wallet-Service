package io.ylab.walletservice.infrastructure.inmemory;

import io.ylab.walletservice.domain.entities.AuditLog;
import io.ylab.walletservice.domain.repositories.AuditLogRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the {@link AuditLogRepository} interface and provides an in-memory repository
 * for storing and retrieving audit log entries.
 * It uses a {@link List} to store audit log entries.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class InMemoryAuditLogRepository implements AuditLogRepository {
    private final List<AuditLog> auditLogs = new ArrayList<>();


    /**
     * Saves an audit log entry to the in-memory repository.
     *
     * @param auditLog The audit log entry to be saved.
     */
    @Override
    public void saveAuditLog(AuditLog auditLog) {
        auditLogs.add(auditLog);
    }

    /**
     * Retrieves a list of audit log entries from the in-memory repository.
     *
     * @return A list of audit log entries.
     */
    @Override
    public List<AuditLog> getAuditLogs() {
        return auditLogs;
    }
}
