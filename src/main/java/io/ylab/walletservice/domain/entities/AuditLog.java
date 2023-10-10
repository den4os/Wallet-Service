package io.ylab.walletservice.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class represents an audit log entry.
 * The audit log keeps track of various actions performed by users in the system.
 * Each log entry includes information about the action type, player ID, timestamp,
 * and the result of the action.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
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

    public ActionType getActionType() {
        return actionType;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ActionResult getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditLog auditLog = (AuditLog) o;
        return Objects.equals(actionType, auditLog.actionType)
                && Objects.equals(playerId, auditLog.playerId)
                && Objects.equals(timestamp, auditLog.timestamp)
                && result == auditLog.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionType, playerId, timestamp, result);
    }
}