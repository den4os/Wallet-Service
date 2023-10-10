package io.ylab.walletservice.domain.repositories;

import io.ylab.walletservice.domain.entities.Transaction;

import java.util.List;
import java.util.Map;

/**
 * This interface defines the methods for interacting with the repository of transaction entities.
 * It provides methods for saving a transaction, retrieving transactions by player ID, and retrieving all transactions.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public interface TransactionRepository {

    /**
     * Saves a transaction entity to the repository.
     *
     * @param transaction The transaction entity to be saved.
     */
    void saveTransaction(Transaction transaction);

    /**
     * Retrieves a list of transactions associated with a player ID.
     *
     * @param playerId The ID of the player whose transactions are to be retrieved.
     * @return A list of transaction entities associated with the player ID.
     */
    List<Transaction> getTransactionsByPlayerId(String playerId);

    /**
     * Retrieves all transactions stored in the repository.
     *
     * @return A map of transaction IDs to transaction entities representing all transactions.
     */
    Map<String, Transaction> getAllTransactions();
}
