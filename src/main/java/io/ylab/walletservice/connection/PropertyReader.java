package io.ylab.walletservice.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
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