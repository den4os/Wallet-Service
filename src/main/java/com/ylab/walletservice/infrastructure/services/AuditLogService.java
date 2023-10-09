package com.ylab.walletservice.infrastructure.services;

import com.ylab.walletservice.domain.entities.ActionResult;
import com.ylab.walletservice.domain.entities.ActionType;
import com.ylab.walletservice.domain.entities.AuditLog;

import java.util.List;


public interface AuditLogService {
    void addAuditLog(String playerId, ActionType actionType, ActionResult actionResult);
    List<AuditLog> getAllAuditLogs();
    List<AuditLog> getPlayerAuditLogs(String playerId);
}
