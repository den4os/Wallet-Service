package io.ylab.walletservice.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The PropertyReader class provides a method for reading properties from a file.
 * This class utilizes FileInputStream to read key-value pairs stored in a properties file.
 *
 * @author Denis Zanin
 * @version 1.0
 * @since 2023-10-18
 */
public class PropertyReader {

    /**
     * Reads the properties from a file specified by its file name.
     *
     * @param fileName The name of the properties file.
     * @return A Properties object containing key-value pairs from the file.
     * @throws RuntimeException if an IOException occurs during reading the properties file.
     */
    public static Properties readProperties(String fileName) {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}