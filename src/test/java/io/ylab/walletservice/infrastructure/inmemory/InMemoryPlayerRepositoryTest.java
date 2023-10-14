package io.ylab.walletservice.infrastructure.inmemory;

import io.ylab.walletservice.domain.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryPlayerRepositoryTest {

    private InMemoryPlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        playerRepository = new InMemoryPlayerRepository();
    }

    @Test
    void testFindById() {
        Player player1 = new Player("1", "Player1", "password1", 100.0);
        Player player2 = new Player("2", "Player2", "password2", 200.0);

        playerRepository.save(player1);
        playerRepository.save(player2);

        Player result = playerRepository.findById("1");

        assertNotNull(result);
        assertEquals("Player1", result.getUsername());
        assertEquals(100.0, result.getBalance());
    }

    @Test
    void testFindByUsername() {
        Player player1 = new Player("1", "Player1", "password1", 100.0);
        Player player2 = new Player("2", "Player2", "password2", 200.0);

        playerRepository.save(player1);
        playerRepository.save(player2);

        Player result = playerRepository.findByUsername("Player2");

        assertNotNull(result);
        assertEquals("Player2", result.getUsername());
        assertEquals(200.0, result.getBalance());
    }

    @Test
    void testSave() {
        Player player = new Player("1", "Player", "password", 150.0);

        playerRepository.save(player);

        Player result = playerRepository.findById("1");

        assertNotNull(result);
        assertEquals("Player", result.getUsername());
        assertEquals(150.0, result.getBalance());
    }
}