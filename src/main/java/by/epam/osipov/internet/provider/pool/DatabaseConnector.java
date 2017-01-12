package by.epam.osipov.internet.provider.pool;


import com.mysql.jdbc.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */

/**
 * Package level access database util
 */
class DatabaseConnector {
    private static final String CONFIG_FILE_NAME = "database.properties";
    private static Properties config;
    private static AtomicBoolean initialized = new AtomicBoolean(false);


    public static Connection getConnection() {
        if (!initialized.get()) {
            init();
        }

        try {
            return DriverManager.getConnection(config.getProperty("url"), config);
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        return null;
    }

    private static void init() {
        initialized.set(true);
        URL configFile = DatabaseConnector.class.getClassLoader().getResource(CONFIG_FILE_NAME);

        if (configFile == null) {
            System.out.println("Config file (" + CONFIG_FILE_NAME + ") not found");
        }

        try (FileInputStream inputStream = new FileInputStream(configFile.getFile())) {
            DriverManager.registerDriver(new Driver());
            config = new Properties();
            config.load(inputStream);
        } catch (IOException e) {
            System.out.println("Problem with config file: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }
}