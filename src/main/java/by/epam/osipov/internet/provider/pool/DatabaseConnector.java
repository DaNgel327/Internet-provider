package by.epam.osipov.internet.provider.pool;

import by.epam.osipov.internet.provider.exception.DatabaseConnectorException;
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
 * Package level access database util
 */
class DatabaseConnector {

    private static final String URL ="jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "id81501135vonew";
    private static final String AUTO_RECONNECT = "true";
    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final String USE_UNICODE = "true";

    private static Properties configProp;

    static {
        configProp = new Properties();
        configProp.put("user", USER);
        configProp.put("password", PASSWORD);
        configProp.put("autoReconnect", AUTO_RECONNECT);
        configProp.put("characterEncoding", CHARACTER_ENCODING);
        configProp.put("useUnicode", USE_UNICODE);
    }


    DatabaseConnector() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

        } catch (SQLException e) {
            System.out.println("Datbase connector fatal");
            throw new RuntimeException(e);
        }
    }

    static ConnectionProxy produce() throws DatabaseConnectorException {
        try {
            Connection connection = DriverManager.getConnection(URL, configProp);
            ConnectionProxy proxyConnection = new ConnectionProxy(connection);
            return proxyConnection;
        } catch (SQLException e) {
            throw new DatabaseConnectorException("Connection was not produced", e);
        }

    }
}