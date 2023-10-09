package com.ylab.walletservice.infrastructure.services;

import com.ylab.walletservice.domain.entities.Player;
import com.ylab.walletservice.domain.repositories.PlayerRepository;

public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository; // Inject the repository
    private int playerIdCount = 1;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player registerPlayer(String username, String password) {
        String playerId = Integer.toString(generateUniquePlayerId());
        Player newPlayer = new Player(playerId, username, password, 0.0);
        playerRepository.save(newPlayer);

        return newPlayer;
    }

    private int generateUniquePlayerId() {
        return playerIdCount++;
    }

    @Override
    public Player authorizePlayer(String username, String password) {
        Player player = playerRepository.findByUsername(username);
        if (player != null && player.getPassword().equals(password)) {
            return player;
        }

        return null;
    }

    @Override
    public double getPlayerBalance(String playerId) {
        Player player = playerRepository.findById(playerId);
        if (player != null) {
            return player.getBalance();
        }

        return -1;
    }
}