package io.ylab.walletservice.domain.repositories;

import io.ylab.walletservice.domain.entities.Player;

public interface PlayerRepository {
    Player findById(String playerId);
    Player findByUsername(String username);
    void save(Player player);
}