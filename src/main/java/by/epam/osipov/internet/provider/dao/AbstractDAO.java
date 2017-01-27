package by.epam.osipov.internet.provider.dao;

import by.epam.osipov.internet.provider.entity.Entity;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.util.List;

/**
 * Created by Lenovo on 11.01.2017.
 */
public abstract class AbstractDAO<T extends Entity> {
    /**
     * Database connection (used with ConnectionProxy wrapper)
     */
    protected ConnectionProxy connection;

    public AbstractDAO(ConnectionProxy connection) {
        this.connection = connection;
    }

    public abstract int getIdByKey(Object key) throws UnsupportedOperationException, DAOException;

    public abstract void deleteByKey(Object key) throws UnsupportedOperationException, DAOException;

    public abstract List<T> findAll() throws UnsupportedOperationException, DAOException;
}
