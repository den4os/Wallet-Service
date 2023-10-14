package io.ylab.walletservice.domain.entities;

import java.util.Objects;

/**
 * This class representing a player
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public class Player {
    private String playerId;
    private String username;
    private final String password;
    private double balance;

    /**
     * Creates a new player with the specified parameters.
     *
     * @param playerId The unique identifier of the player.
     * @param username The username of the player.
     * @param password The password of the player.
     * @param balance  The initial balance of the player's account.
     */
    public Player(String playerId, String username, String password, double balance) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Double.compare(balance, player.balance) == 0
                && Objects.equals(playerId, player.playerId)
                && Objects.equals(username, player.username)
                && Objects.equals(password, player.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, username, password, balance);
    }
}
