package com.ylab.walletservice.domain.repositories;

import com.ylab.walletservice.domain.entities.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionRepository {
    void saveTransaction(Transaction transaction);
    List<Transaction> getTransactionsByPlayerId(String playerId);
    Map<String, Transaction> getAllTransactions();
}
