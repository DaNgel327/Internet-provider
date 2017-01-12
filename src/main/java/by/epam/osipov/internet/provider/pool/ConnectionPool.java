package by.epam.osipov.internet.provider.pool;

import by.epam.osipov.internet.provider.command.factory.CommandFactory;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DatabaseConnectorException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Threadsafe connection pool
 */
public class ConnectionPool {
    private static final int POOL_SIZE = 2;
    private static final int TIMEOUT_NOT_APPLIED = 0;

    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static Lock poolSingleLock = new ReentrantLock();
    private BlockingQueue<ConnectionProxy> connectionsAvailable;
    private BlockingQueue<ConnectionProxy> connectionsInUse;
    private static ConnectionPool pool;


    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);


    private ConnectionPool() {
        connectionsAvailable = new ArrayBlockingQueue<>(POOL_SIZE);
        connectionsInUse = new ArrayBlockingQueue<>(POOL_SIZE);

        do {
            init();

            if (connectionsAvailable.isEmpty()) {
                LOGGER.fatal("Pool wasn't initialized");
                throw new RuntimeException("Pool wasn't initialized");
            }
        }
        while (connectionsAvailable.size() != POOL_SIZE);
    }

    private void init() {
        int needConnectionsNumber = POOL_SIZE - connectionsAvailable.size();

        for (int i = 0; i < needConnectionsNumber; i++) {
            try {
                ConnectionProxy connectionProxy = new ConnectionProxy(DatabaseConnector.getConnection());
                connectionProxy.setAutoCommit(true);
                connectionsAvailable.put(connectionProxy);
            } catch (DatabaseConnectorException | InterruptedException | SQLException e) {
                LOGGER.error(e);
            }
        }
    }

    /**
     * Check if pool was initialized
     *
     * @return true - pool was initialized<br>false - pool wasn't initialized
     */
    public static boolean isInitialized() {
        return initialized.get();
    }

    /**
     * Singleton method to get ConnectionPool instance
     *
     * @return ConnectionPool object
     */
    public static ConnectionPool getInstance() {
        if (!initialized.get()) {
            poolSingleLock.lock();

            try {
                if (pool == null) {
                    pool = new ConnectionPool();
                    initialized.getAndSet(true);
                }
            } finally {
                poolSingleLock.unlock();
            }
        }

        return pool;
    }

    /**
     * Get connection from pool
     *
     * @return ConnectionProxy object
     * @throws ConnectionPoolException if some exception occurred inside
     */
    public ConnectionProxy getConnection() throws ConnectionPoolException {
        ConnectionProxy connectionProxy;

        try {
            connectionProxy = connectionsAvailable.take();
            connectionsInUse.put(connectionProxy);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Exception in ConnectionPool while trying to get connection", e);
        }

        return connectionProxy;
    }

    /**
     * Return connection into pool (or recreate it if there are soe problems with it)
     *
     * @param connectionProxy ConnectionProxy object
     * @throws ConnectionPoolException if some exception occurred inside
     */
    public void closeConnection(ConnectionProxy connectionProxy) throws ConnectionPoolException {
        boolean success = connectionsInUse.remove(connectionProxy);

        try {
            if (success && connectionProxy.isValid(TIMEOUT_NOT_APPLIED)) {
                connectionsAvailable.put(connectionProxy);
            } else {
                ConnectionProxy newConnection = new ConnectionProxy(DatabaseConnector.getConnection());
                newConnection.setAutoCommit(true);
                connectionsAvailable.put(newConnection);
            }
        } catch (SQLException | DatabaseConnectorException | InterruptedException e) {
            throw new ConnectionPoolException("Exception in ConnectionPool while returning " +
                    "a connection to pool", e);
        }
    }

    /**
     * Close all free connections and wait for occupied connections to close them
     */
    public void closeAll() {
        if (initialized.get()) {
            initialized.getAndSet(false);

            for (int i = 0; i < POOL_SIZE; i++) {
                try {
                    ConnectionProxy connectionProxy = connectionsAvailable.take();

                    if (!connectionProxy.getAutoCommit()) {
                        connectionProxy.setAutoCommit(true);
                    }

                    connectionProxy.finallyClose();
                    LOGGER.info(String.format("closed successfully (#%d)", i));
                } catch (SQLException | InterruptedException e) {
                    LOGGER.warn("problem with connection closing (#%d)");
                }
            }
        }
    }
}