package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Transaction;

import java.util.List;

public interface TransactionService {
    boolean performDebitTransaction(String playerId, String transactionId, double amount);
    boolean performCreditTransaction(String playerId, String transactionId, double amount);
    List<Transaction> getPlayerTransactionHistory(String playerId);
}
