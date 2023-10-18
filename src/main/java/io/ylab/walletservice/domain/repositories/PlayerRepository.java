package io.ylab.walletservice.domain.repositories;

import io.ylab.walletservice.domain.entities.Player;

/**
 * This interface defines the methods for interacting with the repository of player entities.
 * It provides methods for finding a player by ID, finding a player by username, and saving a player entity.
 *
 * @author Denis Zanin
 * @version 1.1
 * @since 2023-10-18
 */
public interface PlayerRepository {

    /**
     * Finds a player entity by the given player ID.
     *
     * @param playerId The ID of the player to find.
     * @return The player entity if found, or null if not found.
     */
    Player findById(String playerId);

    /**
     * Finds a player entity by the given username.
     *
     * @param username The username of the player to find.
     * @return The player entity if found, or null if not found.
     */
    Player findByUsername(String username);

    /**
     * Saves a player entity to the repository.
     *
     * @param player The player entity to be saved.
     */
    void save(Player player);

    String generateUniquePlayerId();

    void updatePlayer(Player player);
}