package io.ylab.walletservice.infrastructure.inmemory;

import io.ylab.walletservice.domain.entities.AuditLog;
import io.ylab.walletservice.domain.repositories.AuditLogRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAuditLogRepository implements AuditLogRepository {
    private final List<AuditLog> auditLogs = new ArrayList<>();

    @Override
    public void saveAuditLog(AuditLog auditLog) {
        auditLogs.add(auditLog);
    }

    @Override
    public List<AuditLog> getAuditLogs() {
        return auditLogs;
    }
}
