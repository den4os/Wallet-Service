package com.ylab.walletservice.domain.repositories;

import com.ylab.walletservice.domain.entities.AuditLog;

import java.util.List;

public interface AuditLogRepository {
    void saveAuditLog(AuditLog auditLog);
    List<AuditLog> getAuditLogs();
}