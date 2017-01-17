package by.epam.osipov.internet.provider.dao;

import by.epam.osipov.internet.provider.entity.Entity;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 11.01.2017.
 */
public abstract class AbstractDAO<T extends Entity> {
    /**
     * Database connection (used with ConnectionProxy wrapper)
     * Logic layer
     */
    protected ConnectionProxy connection;

    public AbstractDAO(ConnectionProxy connection) {
        this.connection = connection;
    }


    public abstract int getIdByKey(Object key) throws UnsupportedOperationException;

    public abstract boolean deleteByKey(Object key) throws UnsupportedOperationException;

    public abstract List<T> findAll() throws UnsupportedOperationException;
}
