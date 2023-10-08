package com.ylab.walletservice.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class represents a financial transaction associated with a player's account.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-07
 */
public class Transaction {
    private String transactionId;
    private String playerId; // Reference to the player involved
    private TransactionType type; // Enum for credit or debit
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(String transactionId, String playerId, TransactionType type, double amount, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.playerId = playerId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(transactionId, that.transactionId) && Objects.equals(playerId, that.playerId) && type == that.type && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, playerId, type, amount, timestamp);
    }
}