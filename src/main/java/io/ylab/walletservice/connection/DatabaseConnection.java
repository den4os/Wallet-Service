package io.ylab.walletservice.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseConnection class provides a method to establish a database connection.
 * This class leverages the Java JDBC DriverManager to connect to a relational database.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-18
 */
public class DatabaseConnection {

    /**
     * Establishes a database connection and returns it.
     *
     * @param url      The URL of the database.
     * @param username The username to connect to the database.
     * @param password The password to connect to the database.
     * @return An active Connection object.
     * @throws RuntimeException if a SQL exception occurs during the database connection.
     */
    public static Connection connect(String url, String username, String password) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}