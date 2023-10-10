package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.ActionResult;
import io.ylab.walletservice.domain.entities.ActionType;
import io.ylab.walletservice.domain.entities.AuditLog;

import java.util.List;

/**
 * This interface defines the methods for managing audit log entries in the system.
 * It provides methods for adding audit log entries, retrieving all audit log entries,
 * and retrieving audit log entries for a specific player.
 * Audit logs record various actions and their results in the system.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public interface AuditLogService {

    /**
     * Adds a new audit log entry to record an action performed by a player.
     *
     * @param playerId     The ID of the player performing the action.
     * @param actionType   The type of action performed (e.g., LOGIN, LOGOUT).
     * @param actionResult The result of the action (e.g., SUCCESS, FAILURE).
     */
    void addAuditLog(String playerId, ActionType actionType, ActionResult actionResult);

    /**
     * Retrieves all audit log entries recorded in the system.
     *
     * @return A list of audit log entries representing all recorded actions.
     */
    List<AuditLog> getAllAuditLogs();

    /**
     * Retrieves audit log entries recorded for a specific player.
     *
     * @param playerId The ID of the player for whom audit log entries are to be retrieved.
     * @return A list of audit log entries representing actions performed by the player.
     */
    List<AuditLog> getPlayerAuditLogs(String playerId);
}
