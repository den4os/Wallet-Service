package io.ylab.walletservice.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class represents a financial transaction associated with a player's account.
 * It includes information about the transaction ID, player ID, transaction type,
 * transaction amount, and the timestamp when the transaction occurred.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class Transaction {
    private final String transactionId;
    private String playerId;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;

    /**
     * Creates a new transaction with the specified parameters.
     *
     * @param transactionId The unique identifier for the transaction.
     * @param playerId      The ID of the player associated with the transaction.
     * @param type          The type of the transaction (e.g., DEPOSIT, WITHDRAWAL).
     * @param amount        The amount of money involved in the transaction.
     * @param timestamp     The timestamp when the transaction occurred.
     */
    public Transaction(String transactionId,
                       String playerId,
                       TransactionType type,
                       double amount,
                       LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.playerId = playerId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
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

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(amount, that.amount) == 0
                && Objects.equals(transactionId, that.transactionId)
                && Objects.equals(playerId, that.playerId)
                && type == that.type && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, playerId, type, amount, timestamp);
    }
}