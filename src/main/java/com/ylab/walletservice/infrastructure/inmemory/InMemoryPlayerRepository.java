package com.ylab.walletservice.infrastructure.inmemory;

import com.ylab.walletservice.domain.entities.Player;
import com.ylab.walletservice.domain.repositories.PlayerRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPlayerRepository implements PlayerRepository {
    private final Map<String, Player> players = new HashMap<>();

    @Override
    public Player findById(String playerId) {
        return players.get(playerId);
    }

    @Override
    public Player findByUsername(String username) {
        for (Player player : players.values()) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public void save(Player player) {
        players.put(player.getPlayerId(), player);
    }
}