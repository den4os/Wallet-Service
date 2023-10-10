package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.domain.entities.Transaction;
import io.ylab.walletservice.domain.entities.TransactionType;
import io.ylab.walletservice.domain.repositories.PlayerRepository;
import io.ylab.walletservice.domain.repositories.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public boolean performDebitTransaction(String playerId, String transactionId, double amount) {
        Player player = playerRepository.findById(playerId);

        if (player != null && player.getBalance() >= amount) {
            if (!transactionRepository.getAllTransactions().containsKey(transactionId)) {
                LocalDateTime timestamp = LocalDateTime.now();
                Transaction debitTransaction = new Transaction(transactionId, playerId, TransactionType.DEBIT, amount, timestamp);

                double newBalance = player.getBalance() - amount;
                player.setBalance(newBalance);

                transactionRepository.saveTransaction(debitTransaction);
                playerRepository.save(player);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean performCreditTransaction(String playerId, String transactionId, double amount) {
        Player player = playerRepository.findById(playerId);

        if (player != null) {
            if (!transactionRepository.getAllTransactions().containsKey(transactionId)) {
                LocalDateTime timestamp = LocalDateTime.now();
                Transaction creditTransaction = new Transaction(transactionId, playerId, TransactionType.CREDIT, amount, timestamp);

                double newBalance = player.getBalance() + amount;
                player.setBalance(newBalance);

                transactionRepository.saveTransaction(creditTransaction);
                playerRepository.save(player);


                return true;
            }
        }

        return false;
    }

    @Override
    public List<Transaction> getPlayerTransactionHistory(String playerId) {
        return transactionRepository.getTransactionsByPlayerId(playerId);
    }
}