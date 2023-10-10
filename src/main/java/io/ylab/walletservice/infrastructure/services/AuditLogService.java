package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.AuditLog;

import java.util.List;


public interface AuditLogService {
    void addAuditLog(String playerId, ActionType actionType, ActionResult actionResult);
    List<AuditLog> getAllAuditLogs();
    List<AuditLog> getPlayerAuditLogs(String playerId);
}
