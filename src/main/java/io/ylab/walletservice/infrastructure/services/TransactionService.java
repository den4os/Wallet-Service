package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Transaction;

import java.util.List;

/**
 * This interface defines the methods for managing player transactions in the system.
 * It provides methods for performing debit and credit transactions, as well as retrieving
 * transaction history for a specific player.
 * Transactions represent changes in a player's balance, either adding funds (credit) or deducting funds (debit).
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public interface TransactionService {

    /**
     * Performs a debit transaction for a player, deducting a specified amount from their balance.
     *
     * @param playerId      The ID of the player for whom the transaction is performed.
     * @param transactionId The unique ID of the transaction.
     * @param amount        The amount to deduct from the player's balance.
     * @return `true` if the transaction is successful, `false` if the transaction fails (e.g., insufficient balance).
     */
    boolean performDebitTransaction(String playerId, String transactionId, double amount);

    /**
     * Performs a credit transaction for a player, adding a specified amount to their balance.
     *
     * @param playerId      The ID of the player for whom the transaction is performed.
     * @param transactionId The unique ID of the transaction.
     * @param amount        The amount to add to the player's balance.
     * @return `true` if the transaction is successful, `false` if the transaction fails (e.g., invalid player ID).
     */
    boolean performCreditTransaction(String playerId, String transactionId, double amount);

    /**
     * Retrieves the transaction history for a specific player.
     *
     * @param playerId The ID of the player for whom the transaction history is retrieved.
     * @return A list of transaction entities representing the player's transaction history.
     */
    List<Transaction> getPlayerTransactionHistory(String playerId);
}
