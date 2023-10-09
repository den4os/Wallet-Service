package com.ylab.walletservice.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class AuditLog {

    private ActionType actionType;
    private String playerId;
    private LocalDateTime timestamp;
    private ActionResult result;

    public AuditLog(String playerId, ActionType actionType, ActionResult result, LocalDateTime timestamp) {
        this.playerId = playerId;
        this.actionType = actionType;
        this.result = result;
        this.timestamp = timestamp;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
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

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ActionResult getResult() {
        return result;
    }

    public void setResult(ActionResult result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditLog auditLog = (AuditLog) o;
        return Objects.equals(actionType, auditLog.actionType) && Objects.equals(playerId, auditLog.playerId) && Objects.equals(timestamp, auditLog.timestamp) && result == auditLog.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionType, playerId, timestamp, result);
    }

    // Constructor, getters, and setters for the fields
}