package com.ylab.walletservice.domain.entities;

/**
 * This class representing a player and
 * information about his current balance.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-06
 */
public class Player {
    private final int playerId;
    private double balance;

    /**
     * Constructor for creating a new player with a given identifier and initial balance.
     *
     * @param playerId Player ID.
     * @param balance  Player's initial balance..
     */
    public Player(int playerId, double balance) {
        this.playerId = playerId;
        this.balance = balance;
    }

    /**
     * Gets the player ID.
     *
     * @return Player ID.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the player's current balance.
     *
     * @return Current player balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the player's current balance.
     *
     * @param balance New player balance.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
