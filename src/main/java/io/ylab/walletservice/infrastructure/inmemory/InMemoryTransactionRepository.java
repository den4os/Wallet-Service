package io.ylab.walletservice.infrastructure.inmemory;

import io.ylab.walletservice.domain.entities.Transaction;
import io.ylab.walletservice.domain.repositories.TransactionRepository;

import java.util.*;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<String, List<Transaction>> transactionsByPlayerId = new HashMap<>();
    private final Map<String, Transaction> allTransactions = new HashMap<>();

    @Override
    public void saveTransaction(Transaction transaction) {
        String playerId = transaction.getPlayerId();
        transactionsByPlayerId.computeIfAbsent(playerId, k -> new ArrayList<>()).add(transaction);
        allTransactions.put(transaction.getTransactionId(), transaction);
    }

    @Override
    public List<Transaction> getTransactionsByPlayerId(String playerId) {
        return transactionsByPlayerId.getOrDefault(playerId, new ArrayList<>());
    }

    @Override
    public Map<String, Transaction> getAllTransactions() {
        return allTransactions;
    }
}
