package by.epam.osipov.internet.provider.pool;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by DaryaKolyadko on 13.07.2016.
 */

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

    private ConnectionPool() {
        connectionsAvailable = new ArrayBlockingQueue<>(POOL_SIZE);
        connectionsInUse = new ArrayBlockingQueue<>(POOL_SIZE);

        do {
            init();

            if (connectionsAvailable.isEmpty()) {
                System.out.println("Pool wasn't initialized");
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
            } catch ( InterruptedException | SQLException e) {
                System.out.println(e);
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


    public ConnectionProxy getConnection() {
        ConnectionProxy connectionProxy = null;

        try {
            connectionProxy = connectionsAvailable.take();
            connectionsInUse.put(connectionProxy);
        } catch (InterruptedException e) {
            System.out.println("Exception in ConnectionPool while trying to get connection");
        }

        return connectionProxy;
    }


    public void closeConnection(ConnectionProxy connectionProxy) {
        boolean success = connectionsInUse.remove(connectionProxy);

        try {
            if (success && connectionProxy.isValid(TIMEOUT_NOT_APPLIED)) {
                connectionsAvailable.put(connectionProxy);
            } else {
                ConnectionProxy newConnection = new ConnectionProxy(DatabaseConnector.getConnection());
                newConnection.setAutoCommit(true);
                connectionsAvailable.put(newConnection);
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println("Exception in ConnectionPool while returning " +
                    "a connection to pool");
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
                    System.out.println(String.format("closed successfully (#%d)", i));
                } catch (SQLException | InterruptedException e) {
                    System.out.println("problem with connection closing (#%d)");
                }
            }
        }
    }
}