package io.ylab.walletservice.domain.entities;

import lombok.Data;

import java.math.BigDecimal;

/**
 * This class representing a player
 *
 * @author Denis Zanin
 * @version 1.1
 * @since 2023-10-10
 */

@Data
public class Player {

    private String playerId;
    private String username;
    private final String password;
    private BigDecimal balance;

    /**
     * Creates a new player with the specified parameters.
     *
     * @param playerId The unique identifier of the player.
     * @param username The username of the player.
     * @param password The password of the player.
     * @param balance  The initial balance of the player's account.
     */
    public Player(String playerId, String username, String password, BigDecimal balance) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }
}
