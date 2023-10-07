package com.ylab.walletservice.domain.entities;

/**
 * This class represents a player's account.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-07
 */
public class Account {
    private String accountId;
    private String playerId;
    private double balance;

    /**
     * Creates a new account with the given ID, player ID, and initial balance.
     *
     * @param accountId Unique account identifier.
     * @param playerId Player's unique identifier.
     * @param balance   Initial balance of the account.
     */
    public Account(String accountId, String playerId, double balance) {
        this.accountId = accountId;
        this.playerId = playerId;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}