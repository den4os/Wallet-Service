package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.AuditLog;
import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.domain.repositories.AuditLogRepository;
import io.ylab.walletservice.domain.repositories.PlayerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final PlayerRepository playerRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository, PlayerRepository playerRepository) {
        this.auditLogRepository = auditLogRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public void addAuditLog(String playerId, ActionType actionType, ActionResult actionResult) {
        LocalDateTime timestamp = LocalDateTime.now();
        AuditLog auditLog = new AuditLog(playerId, actionType, actionResult, timestamp);
        auditLogRepository.saveAuditLog(auditLog);
    }

    @Override
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.getAuditLogs();
    }

    @Override
    public List<AuditLog> getPlayerAuditLogs(String playerId) {
        Player player = playerRepository.findById(playerId);
        if (player != null) {
            List<AuditLog> auditLogs = auditLogRepository.getAuditLogs();
            return auditLogs.stream()
                    .filter(auditLog -> auditLog.getPlayerId().equals(playerId))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
