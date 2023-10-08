package com.ylab.walletservice.infrastructure.services;

import com.ylab.walletservice.domain.entities.Player;

public interface PlayerService {
    Player registerPlayer(String username, String password);
    Player authorizePlayer(String username, String password);
    double getPlayerBalance(String playerId);
    void updatePlayerBalance(String playerId, double amount);
}