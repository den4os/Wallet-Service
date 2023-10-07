package com.ylab.walletservice.domain.services;

/**
 * Interface for managing player balance.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-06
 */
public interface WalletService {

    /**
     * Performs a debit transaction for the player.
     *
     * @param playerId Player ID.
     * @param amount   Debit transaction amount.
     * @return `true` if the operation was successful, `false` otherwise.
     */
    boolean debit(int playerId, double amount);

    /**
     * Выполняет кредитную транзакцию для игрока.
     *
     * @param playerId Player ID.
     * @param amount   Credit transaction amount.
     * @return `true` if the operation was successful, `false` otherwise.
     */
    boolean credit(int playerId, double amount);

    /**
     * Gets the player's current balance.
     *
     * @param playerId Player ID.
     * @return The player's current balance or -1 if the player is not found.
     */
    double getBalance(int playerId);
}
