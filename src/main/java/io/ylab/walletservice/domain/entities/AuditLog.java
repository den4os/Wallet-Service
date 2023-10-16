package io.ylab.walletservice.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class represents an audit log entry.
 * The audit log keeps track of various actions performed by users in the system.
 * Each log entry includes information about the action type, player ID, timestamp,
 * and the result of the action.
 *
 * @author Denis Zanin
 * @version 1.1
 * @since 2023-10-10
 */

@Data
public class AuditLog {

    private final ActionType actionType;
    private String playerId;
    private final LocalDateTime timestamp;
    private final ActionResult result;

    /**
     * Creates a new audit log entry with the specified parameters.
     *
     * @param playerId   The ID of the player associated with the action.
     * @param actionType The type of action performed (e.g., LOGIN, LOGOUT).
     * @param result     The result of the action (e.g., SUCCESS, FAILURE).
     * @param timestamp  The timestamp when the action occurred.
     */
    public AuditLog(String playerId, ActionType actionType, ActionResult result, LocalDateTime timestamp) {
        this.playerId = playerId;
        this.actionType = actionType;
        this.result = result;
        this.timestamp = timestamp;
    }
}