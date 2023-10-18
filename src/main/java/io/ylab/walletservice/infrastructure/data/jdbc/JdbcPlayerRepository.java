package io.ylab.walletservice.infrastructure.data.jdbc;

import io.ylab.walletservice.domain.entities.Player;
import io.ylab.walletservice.domain.repositories.PlayerRepository;

import java.sql.*;

public class JdbcPlayerRepository implements PlayerRepository {

    private final Connection connection;

    public JdbcPlayerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Player findById(String playerId) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM wallet.player WHERE player_id = ?")){

            statement.setInt(1, Integer.parseInt(playerId));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Player(
                            String.valueOf(resultSet.getInt("player_id")),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getBigDecimal("balance")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Player findByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM wallet.player WHERE username = ?")) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Player(
                            String.valueOf(resultSet.getInt("player_id")),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getBigDecimal("balance")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void save(Player player) {
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO wallet.player (username, password, balance) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {

                insertStatement.setString(1, player.getUsername());
                insertStatement.setString(2, player.getPassword());
                insertStatement.setBigDecimal(3, player.getBalance());
                insertStatement.executeUpdate();

                try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        player.setPlayerId(Integer.toString(generatedKeys.getInt(1)));
                    } else {
                        System.err.println("Creating player failed, no ID obtained.");
                    }
                }
                connection.commit();
            }
        } catch (SQLException e) {
            System.err.println("An error has occurred: " + e.getMessage());
            try {
                connection.rollback();
                System.err.println("Transaction canceled.");
            } catch (SQLException rollbackException) {
                System.err.println("Rollback failed: " + rollbackException.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Failed to set auto commit: " + e.getMessage());
            }
        }
    }

    @Override
    public void updatePlayer(Player player) {
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE wallet.player SET username = ?, password = ?, balance = ? WHERE player_id = ?")) {

                statement.setString(1, player.getUsername());
                statement.setString(2, player.getPassword());
                statement.setBigDecimal(3, player.getBalance());
                statement.setInt(4, Integer.parseInt(player.getPlayerId()));

                int updatedRows = statement.executeUpdate();

                if (updatedRows == 0) {
                    throw new SQLException("Failed to update the player, no rows affected.");
                }
                connection.commit();
            }
        } catch (SQLException e) {
            System.err.println("An error has occurred: " + e.getMessage());
            try {
                connection.rollback();
                System.err.println("Transaction canceled.");
            } catch (SQLException rollbackException) {
                System.err.println("Rollback failed: " + rollbackException.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Failed to set auto commit: " + e.getMessage());
            }
        }
    }

    @Override
    public String generateUniquePlayerId() {
        return null;
    }
}
