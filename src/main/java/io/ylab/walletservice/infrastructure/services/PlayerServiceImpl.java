package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.domain.repositories.PlayerRepository;

import java.math.BigDecimal;

/**
 * This class implements the {@link PlayerService} interface and provides functionality
 * for managing player entities in the system.
 * It allows for the registration of new players, authorization of existing ones,
 * and retrieval of player balances.
 *
 * @author Denis Zanin
 * @version 1.1
 * @since 2023-10-17
 */
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    /**
     * Initializes a new instance of the {@code PlayerServiceImpl} class with the provided player repository.
     *
     * @param playerRepository The repository used to store and retrieve player entities.
     */
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Registers a new player with the given username and password.
     *
     * @param username The username for the new player.
     * @param password The password for the new player.
     * @return The registered player entity, or null if registration fails (e.g., duplicate username).
     */
    @Override
    public Player registerPlayer(String username, String password) {
        Player existingPlayer = playerRepository.findByUsername(username);
        if (existingPlayer != null) {
            return null;
        }
        String playerId = playerRepository.generateUniquePlayerId();
        Player newPlayer = new Player(playerId, username, password, new BigDecimal("0.0"));
        if (playerId != null) {
            newPlayer.setPlayerId(playerId);
        }
        playerRepository.save(newPlayer);

        return newPlayer;
    }

    /**
     * Authorizes a player with the provided username and password.
     *
     * @param username The username of the player to authorize.
     * @param password The password of the player to authorize.
     * @return The authorized player entity if valid credentials are provided, or null if not authorized.
     */
    @Override
    public Player authorizePlayer(String username, String password) {
        Player player = playerRepository.findByUsername(username);
        if (player != null && player.getPassword().equals(password)) {
            return player;
        }

        return null;
    }

    /**
     * Retrieves the balance of a player with the given ID.
     *
     * @param playerId The ID of the player whose balance is to be retrieved.
     * @return The balance of the player, or -1 if the player ID is not found.
     */
    @Override
    public BigDecimal getPlayerBalance(String playerId) {
        Player player = playerRepository.findById(playerId);
        if (player != null) {
            return player.getBalance();
        }

        return new BigDecimal("-1");
    }
}