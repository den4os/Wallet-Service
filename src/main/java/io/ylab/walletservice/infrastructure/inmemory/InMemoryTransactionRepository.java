package io.ylab.walletservice.infrastructure.inmemory;

import io.ylab.walletservice.domain.entities.Transaction;
import io.ylab.walletservice.domain.repositories.TransactionRepository;

import java.util.*;

/**
 * This class implements the {@link TransactionRepository} interface and provides an in-memory repository
 * for storing and retrieving transaction entities.
 * It uses two {@link Map} structures to store transactions: one to store transactions by player ID
 * and another to store all transactions by their unique IDs.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<String, List<Transaction>> transactionsByPlayerId = new HashMap<>();
    private final Map<String, Transaction> allTransactions = new HashMap<>();

    /**
     * Saves a transaction entity to the in-memory repository.
     *
     * @param transaction The transaction entity to be saved.
     */
    @Override
    public void saveTransaction(Transaction transaction) {
        String playerId = transaction.getPlayerId();
        transactionsByPlayerId.computeIfAbsent(playerId, k -> new ArrayList<>()).add(transaction);
        allTransactions.put(transaction.getTransactionId(), transaction);
    }

    /**
     * Retrieves a list of transactions associated with a player ID.
     *
     * @param playerId The ID of the player whose transactions are to be retrieved.
     * @return A list of transaction entities associated with the player ID.
     */
    @Override
    public List<Transaction> getTransactionsByPlayerId(String playerId) {
        return transactionsByPlayerId.getOrDefault(playerId, new ArrayList<>());
    }

    /**
     * Retrieves all transactions stored in the repository.
     *
     * @return A map of transaction IDs to transaction entities representing all transactions.
     */
    @Override
    public Map<String, Transaction> getAllTransactions() {
        return allTransactions;
    }
}
