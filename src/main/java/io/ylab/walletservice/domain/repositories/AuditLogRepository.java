package io.ylab.walletservice.domain.repositories;

import io.ylab.walletservice.domain.entities.AuditLog;

import java.util.List;

public interface AuditLogRepository {
    void saveAuditLog(AuditLog auditLog);
    List<AuditLog> getAuditLogs();
}