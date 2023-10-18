package io.ylab.walletservice.infrastructure.data.jdbc;

import io.ylab.walletservice.domain.entities.Player;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

class JdbcPlayerRepositoryTest extends AbstractContainerBaseTest {

    private JdbcPlayerRepository jdbcPlayerRepository;
    private Player player;

    @BeforeEach
    void setUp() throws Exception {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        Liquibase liquibase = new Liquibase(
                "db/changelog/changelog.xml",
                new ClassLoaderResourceAccessor(),
                new JdbcConnection(connection)
        );
        liquibase.update((String) null);

        jdbcPlayerRepository = new JdbcPlayerRepository(connection);
        player = new Player(null, "player", "password", new BigDecimal("0.0"));
        jdbcPlayerRepository.save(player);
    }

    @Test
    void findById() {
        Player currentPlayer = jdbcPlayerRepository.findById("1");
        assertNotNull(currentPlayer);
        assertEquals(player.getPlayerId(), currentPlayer.getPlayerId());
        assertEquals(player.getUsername(), currentPlayer.getUsername());
    }

    @Test
    void findByUsername() {
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
        Player updatedPlayer = new Player("1", "player", "password", new BigDecimal("200.0"));
        jdbcPlayerRepository.updatePlayer(updatedPlayer);
        Player currentPlayer = jdbcPlayerRepository.findById("1");
        assertNotNull(currentPlayer);
        assertEquals(updatedPlayer.getPlayerId(), currentPlayer.getPlayerId());
        assertEquals(updatedPlayer.getUsername(), currentPlayer.getUsername());
        assertEquals(updatedPlayer.getPassword(), currentPlayer.getPassword());
    }
}