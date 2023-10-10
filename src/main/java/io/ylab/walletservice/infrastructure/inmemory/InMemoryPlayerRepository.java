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
 * @version 1.0
 * @since 2023-10-10
 */
public class InMemoryPlayerRepository implements PlayerRepository {
    private final Map<String, Player> players = new HashMap<>();

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
}