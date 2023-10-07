package com.ylab.walletservice.domain.entities;

/**
 * This class representing a player
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-07
 */
public class Player {
    private String playerId;
    private String username;
    private String password;

    /**
     * Creates a new player with the given ID, username and password.
     *
     * @param playerId Unique player identifier.
     * @param username Username (login).
     * @param password Hashed password (safely store the password).
     */
    public Player(String playerId, String username, String password) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
