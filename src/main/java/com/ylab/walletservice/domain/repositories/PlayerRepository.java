package com.ylab.walletservice.domain.repositories;

import com.ylab.walletservice.domain.entities.Player;

public interface PlayerRepository {
    Player findById(String playerId);
    Player findByUsername(String username);
    void save(Player player);
}