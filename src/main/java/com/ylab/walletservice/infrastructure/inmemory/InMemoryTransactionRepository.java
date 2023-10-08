package com.ylab.walletservice.infrastructure.inmemory;

import com.ylab.walletservice.domain.entities.Transaction;
import com.ylab.walletservice.domain.repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<String, List<Transaction>> transactionsByPlayerId = new HashMap<>();

    @Override
    public void saveTransaction(Transaction transaction) {
        String playerId = transaction.getPlayerId();
        transactionsByPlayerId.computeIfAbsent(playerId, k -> new ArrayList<>()).add(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByPlayerId(String playerId) {
        return transactionsByPlayerId.getOrDefault(playerId, new ArrayList<>());
    }
}
