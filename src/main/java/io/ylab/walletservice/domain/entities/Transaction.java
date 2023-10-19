package io.ylab.walletservice.domain.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This class represents a financial transaction associated with a player's account.
 * It includes information about the transaction ID, player ID, transaction type,
 * transaction amount, and the timestamp when the transaction occurred.
 *
 * @author Denis Zanin
 * @version 1.1
 * @since 2023-10-10
 */

@Data
public class Transaction {

    private final String transactionId;
    private String playerId;
    private final TransactionType type;
    private final BigDecimal amount;
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
                       BigDecimal amount,
                       LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.playerId = playerId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}