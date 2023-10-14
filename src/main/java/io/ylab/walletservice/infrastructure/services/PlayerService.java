package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Player;

/**
 * This interface defines the methods for managing player entities in the system.
 * It provides methods for registering a new player, authorizing an existing player,
 * and retrieving a player's balance.
 * Players use the system for various activities, and their balances reflect their in-game currency.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-10
 */
public interface PlayerService {

    /**
     * Registers a new player with the given username and password.
     *
     * @param username The username for the new player.
     * @param password The password for the new player.
     * @return The registered player entity, or null if registration fails (e.g., duplicate username).
     */
    Player registerPlayer(String username, String password);

    /**
     * Authorizes a player with the provided username and password.
     *
     * @param username The username of the player to authorize.
     * @param password The password of the player to authorize.
     * @return The authorized player entity if valid credentials are provided, or null if not authorized.
     */
    Player authorizePlayer(String username, String password);

    /**
     * Retrieves the balance of a player with the given ID.
     *
     * @param playerId The ID of the player whose balance is to be retrieved.
     * @return The balance of the player.
     */
    double getPlayerBalance(String playerId);
}