package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.domain.entities.Transaction;
import io.ylab.walletservice.domain.entities.TransactionType;
import io.ylab.walletservice.domain.repositories.PlayerRepository;
import io.ylab.walletservice.domain.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class implements the {@link TransactionService} interface and provides functionality
 * for managing player transactions in the system.
 * It allows for performing debit and credit transactions, as well as retrieving transaction history
 * for a specific player.
 *
 * @author Denis Zanin
 * @version 1.1
 * @since 2023-10-18
 */
public class TransactionServiceImpl implements TransactionService {

    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Initializes a new instance of the {@code TransactionServiceImpl} class with the provided player repository
     * and transaction repository.
     *
     * @param playerRepository      The repository used to store and retrieve player entities.
     * @param transactionRepository The repository used to store and retrieve transaction entities.
     */
    public TransactionServiceImpl(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Performs a debit transaction for a player, deducting a specified amount from their balance.
     *
     * @param playerId      The ID of the player for whom the transaction is performed.
     * @param transactionId The unique ID of the transaction.
     * @param amount        The amount to deduct from the player's balance.
     * @return `true` if the transaction is successful, `false` if the transaction fails
     * (e.g., insufficient balance or duplicate transaction ID).
     */
    @Override
    public boolean performDebitTransaction(String playerId, String transactionId, BigDecimal amount) {
        Player player = playerRepository.findById(playerId);

        if (player != null && player.getBalance().compareTo(amount) >= 0) {
            if (!transactionRepository.getAllTransactions().containsKey(transactionId)) {
                LocalDateTime timestamp = LocalDateTime.now();
                Transaction debitTransaction = new Transaction(transactionId,
                        playerId,
                        TransactionType.DEBIT,
                        amount,
                        timestamp);

                BigDecimal newBalance = player.getBalance().subtract(amount);
                player.setBalance(newBalance);

                transactionRepository.saveTransaction(debitTransaction);
                playerRepository.updatePlayer(player);

                return true;
            }
        }

        return false;
    }

    /**
     * Performs a credit transaction for a player, adding a specified amount to their balance.
     *
     * @param playerId      The ID of the player for whom the transaction is performed.
     * @param transactionId The unique ID of the transaction.
     * @param amount        The amount to add to the player's balance.
     * @return `true` if the transaction is successful, `false` if the transaction fails
     * (e.g., invalid player ID or duplicate transaction ID).
     */
    @Override
    public boolean performCreditTransaction(String playerId, String transactionId, BigDecimal amount) {
        Player player = playerRepository.findById(playerId);

        if (player != null) {
            if (!transactionRepository.getAllTransactions().containsKey(transactionId)) {
                LocalDateTime timestamp = LocalDateTime.now();
                Transaction creditTransaction = new Transaction(transactionId,
                        playerId,
                        TransactionType.CREDIT,
                        amount,
                        timestamp);

                BigDecimal newBalance = player.getBalance().add(amount);
                player.setBalance(newBalance);

                transactionRepository.saveTransaction(creditTransaction);
                playerRepository.updatePlayer(player);

                return true;
            }
        }

        return false;
    }

    /**
     * Retrieves the transaction history for a specific player.
     *
     * @param playerId The ID of the player for whom the transaction history is retrieved.
     * @return A list of transaction entities representing the player's transaction history.
     */
    @Override
    public List<Transaction> getPlayerTransactionHistory(String playerId) {
        return transactionRepository.getTransactionsByPlayerId(playerId);
    }
}