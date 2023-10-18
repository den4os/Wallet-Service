package io.ylab.walletservice.infrastructure.data.jdbc;

import io.ylab.walletservice.domain.entities.Transaction;
import io.ylab.walletservice.domain.entities.TransactionType;
import io.ylab.walletservice.domain.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTransactionRepository implements TransactionRepository {
    private final Connection connection;

    public JdbcTransactionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO wallet.transaction (transaction_id, player_id, type, amount, timestamp) VALUES (?, ?, ?, ?, ?)")) {

                insertStatement.setString(1, transaction.getTransactionId());
                insertStatement.setString(2, transaction.getPlayerId());
                insertStatement.setString(3, transaction.getType().name());
                insertStatement.setBigDecimal(4, transaction.getAmount());
                insertStatement.setTimestamp(5, Timestamp.valueOf(transaction.getTimestamp()));
                insertStatement.executeUpdate();

                try (PreparedStatement updateStatement = connection.prepareStatement(
                        "UPDATE wallet.player SET balance = ? WHERE player_id = ?")) {

                    updateStatement.setBigDecimal(1, transaction.getAmount());
                    updateStatement.setInt(2, Integer.parseInt(transaction.getPlayerId()));
                    updateStatement.executeUpdate();
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
    public List<Transaction> getTransactionsByPlayerId(String playerId) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            try (PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM wallet.transaction WHERE player_id = ?")) {

                selectStatement.setString(1, playerId);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String transactionId = resultSet.getString("transaction_id");
                        String type = resultSet.getString("type");
                        BigDecimal amount = resultSet.getBigDecimal("amount");
                        Timestamp timestamp = resultSet.getTimestamp("timestamp");
                        transactions.add(new Transaction(transactionId, playerId, TransactionType.valueOf(type), amount, timestamp.toLocalDateTime()));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("An error has occurred: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public Map<String, Transaction> getAllTransactions() {
        Map<String, Transaction> transactions = new HashMap<>();
        try {
            try (PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM wallet.transaction")) {

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String transactionId = resultSet.getString("transaction_id");
                        String playerId = resultSet.getString("player_id");
                        String type = resultSet.getString("type");
                        BigDecimal amount = resultSet.getBigDecimal("amount");
                        Timestamp timestamp = resultSet.getTimestamp("timestamp");

                        Transaction transaction = new Transaction(transactionId, playerId, TransactionType.valueOf(type), amount, timestamp.toLocalDateTime());
                        transactions.put(transactionId, transaction);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("An error has occurred: " + e.getMessage());
        }
        return transactions;
    }
}