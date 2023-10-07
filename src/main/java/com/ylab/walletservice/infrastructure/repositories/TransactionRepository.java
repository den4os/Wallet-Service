package com.ylab.walletservice.infrastructure.repositories;

import com.ylab.walletservice.domain.entities.Transaction;

/**
 * Repository interface for accessing player transactions.
 * This interface provides methods to interact with player transactions in the repository.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-07
 */
public interface TransactionRepository {
    /**
     * Retrieves a transaction by its unique identifier.
     *
     * @param transactionId The unique identifier of the transaction to retrieve.
     * @return The transaction with the specified identifier, or null if not found.
     */
    Transaction findById(String transactionId);

    /**
     * Saves a transaction in the repository.
     *
     * @param transaction The transaction to save.
     */
    void save(Transaction transaction);
}