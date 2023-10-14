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

/**
 * This class implements the {@link AuditLogService} interface and provides functionality
 * for managing audit log entries in the system.
 * It allows for the recording of actions performed by players, retrieval of all audit log entries,
 * and retrieval of audit log entries for a specific player.
 * Audit logs record various actions and their results in the system.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final PlayerRepository playerRepository;


    /**
     * Initializes a new instance of the {@code AuditLogServiceImpl} class with the provided audit log repository
     * and player repository.
     *
     * @param auditLogRepository The repository used to store and retrieve audit log entries.
     * @param playerRepository   The repository used to store and retrieve player entities.
     */
    public AuditLogServiceImpl(AuditLogRepository auditLogRepository, PlayerRepository playerRepository) {
        this.auditLogRepository = auditLogRepository;
        this.playerRepository = playerRepository;
    }

    /**
     * Adds a new audit log entry to record an action performed by a player.
     *
     * @param playerId     The ID of the player performing the action.
     * @param actionType   The type of action performed (e.g., LOGIN, LOGOUT).
     * @param actionResult The result of the action (e.g., SUCCESS, FAILURE).
     */
    @Override
    public void addAuditLog(String playerId, ActionType actionType, ActionResult actionResult) {
        LocalDateTime timestamp = LocalDateTime.now();
        AuditLog auditLog = new AuditLog(playerId, actionType, actionResult, timestamp);
        auditLogRepository.saveAuditLog(auditLog);
    }

    /**
     * Retrieves all audit log entries recorded in the system.
     *
     * @return A list of audit log entries representing all recorded actions.
     */
    @Override
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.getAuditLogs();
    }

    /**
     * Retrieves audit log entries recorded for a specific player.
     *
     * @param playerId The ID of the player for whom audit log entries are to be retrieved.
     * @return A list of audit log entries representing actions performed by the player.
     */
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
