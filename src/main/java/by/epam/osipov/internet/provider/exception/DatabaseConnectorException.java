package by.epam.osipov.internet.provider.exception;

/**
 * Created by Lenovo on 12.01.2017.
 */
public class DatabaseConnectorException extends Exception {
    public DatabaseConnectorException() {
    }
    public DatabaseConnectorException(String message) {
        super(message);
    }
    public DatabaseConnectorException(String message, Throwable cause) {
        super(message, cause);
    }
    public DatabaseConnectorException(Throwable cause) {
        super(cause);
    }
    public DatabaseConnectorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
