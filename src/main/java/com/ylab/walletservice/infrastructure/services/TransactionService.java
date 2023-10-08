package com.ylab.walletservice.infrastructure.services;

import com.ylab.walletservice.domain.entities.Transaction;

import java.util.List;

public interface TransactionService {
    boolean performDebitTransaction(String playerId, String transactionId, double amount);
    boolean performCreditTransaction(String playerId, String transactionId, double amount);
    List<Transaction> getTransactionHistory(String playerId);
}
