package io.ylab.walletservice.infrastructure.services;

import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.domain.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceImplTest {

    private PlayerServiceImpl playerService;
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        playerRepository = mock(PlayerRepository.class);
        playerService = new PlayerServiceImpl(playerRepository);
    }

    @Test
    void testRegisterPlayer() {
        playerService.registerPlayer("SamplePlayer", "password");
        verify(playerRepository, times(1)).save(argThat(player ->
                player.getUsername().equals("SamplePlayer") &&
                player.getPassword().equals("password")));
    }

    @Test
    void testAuthorizePlayer() {
        Player existingPlayer = new Player("1", "SamplePlayer", "password", 100.0);
        when(playerRepository.findByUsername("SamplePlayer")).thenReturn(existingPlayer);
        Player result = playerService.authorizePlayer("SamplePlayer", "password");
        assertNotNull(result);
        assertEquals("SamplePlayer", result.getUsername());
    }
}