package io.ylab.walletservice.infrastructure.inmemory;

import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.domain.repositories.PlayerRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the {@link PlayerRepository} interface and provides an in-memory repository
 * for storing and retrieving player entities.
 * It uses a {@link Map} to store player entities with their unique IDs.
 *
 * @author Denis Zanin
 * @version 1.1
 * @since 2023-10-17
 */
public class InMemoryPlayerRepository implements PlayerRepository {
    private final Map<String, Player> players = new HashMap<>();

    private int playerIdCount = 0;

    /**
     * Finds a player entity by the given player ID.
     *
     * @param playerId The ID of the player to find.
     * @return The player entity if found, or null if not found.
     */
    @Override
    public Player findById(String playerId) {
        return players.get(playerId);
    }

    /**
     * Finds a player entity by the given username.
     *
     * @param username The username of the player to find.
     * @return The player entity if found, or null if not found.
     */
    @Override
    public Player findByUsername(String username) {
        return players.values().stream()
                .filter(player -> player.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves a player entity to the in-memory repository.
     *
     * @param player The player entity to be saved.
     */
    @Override
    public void save(Player player) {
        players.put(player.getPlayerId(), player);
    }

    /**
     * Updates the details of an existing player entity in the repository.
     *
     * @param player The player entity with updated information.
     */
    @Override
    public void updatePlayer(Player player) {
        players.put(player.getPlayerId(), player);
    }

    /**
     * Generates a unique player ID for storing a new player entity.
     *
     * @return A string representing the unique player ID.
     */
    @Override
    public String generateUniquePlayerId() {
        playerIdCount += 1;
        return Integer.toString(playerIdCount);
    }
}