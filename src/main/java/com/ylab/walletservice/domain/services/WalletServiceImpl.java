package com.ylab.walletservice.domain.services;

import com.ylab.walletservice.domain.entities.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the {@link WalletService} interface.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-06
 */
public class WalletServiceImpl implements WalletService {
    private Map<Integer, Player> players; // Simulation of storage of players and their balances

    public WalletServiceImpl() {
        this.players = new HashMap<>();
    }

    @Override
    public boolean debit(int playerId, double amount) {
        Player player = players.get(playerId);
        if (player == null) {
            return false; // Player not found
        }

        double newBalance = player.getBalance() - amount;
        if (newBalance < 0) {
            return false; // Insufficient funds for debit transaction
        }

        player.setBalance(newBalance);
        return true;
    }

    @Override
    public boolean credit(int playerId, double amount) {
        Player player = players.get(playerId);
        if (player == null) {
            return false; // Player not found
        }

        double newBalance = player.getBalance() + amount;
        player.setBalance(newBalance);
        return true;
    }

    @Override
    public double getBalance(int playerId) {
        Player player = players.get(playerId);
        if (player == null) {
            return -1; // Player not found
        }

        return player.getBalance();
    }
}
