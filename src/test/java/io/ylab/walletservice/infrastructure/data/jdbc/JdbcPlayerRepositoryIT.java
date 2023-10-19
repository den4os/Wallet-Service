package io.ylab.walletservice.infrastructure.data.jdbc;

import io.ylab.walletservice.domain.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class JdbcPlayerRepositoryIT extends AbstractContainerBaseIT {

    private JdbcPlayerRepository jdbcPlayerRepository;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        jdbcPlayerRepository = new JdbcPlayerRepository(connection);
    }

    @Test
    void findById() {
        Player player = new Player("1", "player", "password", new BigDecimal("0.0"));
        jdbcPlayerRepository.save(player);
        Player currentPlayer = jdbcPlayerRepository.findById("1");
        assertNotNull(currentPlayer);
        assertEquals(player.getPlayerId(), currentPlayer.getPlayerId());
        assertEquals(player.getUsername(), currentPlayer.getUsername());
    }

    @Test
    void findByUsername() {
        Player player = new Player("1", "player", "password", new BigDecimal("0.0"));
        jdbcPlayerRepository.save(player);
        Player currentPlayer = jdbcPlayerRepository.findByUsername("player");
        assertNotNull(currentPlayer);
        assertEquals(player.getPlayerId(), currentPlayer.getPlayerId());
        assertEquals(player.getUsername(), currentPlayer.getUsername());
    }

    @Test
    void save() {
        Player newPlayer = new Player("1", "newPlayer", "password", new BigDecimal("0.0"));
        jdbcPlayerRepository.save(newPlayer);
        Player savedPlayer = jdbcPlayerRepository.findById(newPlayer.getPlayerId());
        assertNotNull(savedPlayer);
        assertEquals(newPlayer.getPlayerId(), savedPlayer.getPlayerId());
        assertEquals(newPlayer.getUsername(), savedPlayer.getUsername());
    }

    @Test
    void updatePlayer() {
        Player player = new Player("1", "player", "password", new BigDecimal("0.0"));
        jdbcPlayerRepository.save(player);
        Player updatedPlayer = new Player("1", "player", "password", new BigDecimal("200.0"));
        jdbcPlayerRepository.updatePlayer(updatedPlayer);
        Player currentPlayer = jdbcPlayerRepository.findById("1");
        assertNotNull(currentPlayer);
        assertEquals(updatedPlayer.getPlayerId(), currentPlayer.getPlayerId());
        assertEquals(updatedPlayer.getUsername(), currentPlayer.getUsername());
        assertEquals(updatedPlayer.getPassword(), currentPlayer.getPassword());
    }
}