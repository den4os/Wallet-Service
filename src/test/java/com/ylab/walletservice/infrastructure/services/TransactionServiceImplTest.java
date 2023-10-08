package com.ylab.walletservice.infrastructure.services;

import com.ylab.walletservice.domain.entities.Player;
import com.ylab.walletservice.domain.repositories.PlayerRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerServiceImplTest {
    private PlayerRepository playerRepository;
    private PlayerServiceImpl playerService;


    @BeforeEach
    public void setUp() {
        // Create a mock PlayerRepository using Mockito
        playerRepository = Mockito.mock(PlayerRepository.class);

        // Create an instance of PlayerServiceImpl with the mock repository
        playerService = new PlayerServiceImpl(playerRepository);
    }

    @Test
    public void testRegisterPlayer() {
        // Define test data
        String username = "testuser";
        String password = "password";

        // Create a player
        Player testPlayer = new Player("1", username, password, 0.0);

        Mockito.when(playerRepository.save(Mockito.any(Player.class))).thenReturn(testPlayer);

        // Call the registerPlayer method
        Player registeredPlayer = playerService.registerPlayer(username, password);

        // Assertions
        assertNotNull(registeredPlayer);
        assertEquals(username, registeredPlayer.getUsername());
        assertEquals(password, registeredPlayer.getPassword());
        assertEquals(0.0, registeredPlayer.getBalance(), 0.001);
    }

    // Add more test methods for other scenarios, e.g., authorization, balance retrieval, etc.
}