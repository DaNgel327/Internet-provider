package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Service;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 18.01.2017.
 */
public class ServiceDAO extends AbstractDAO {

    private final static String SELECT_ALL = "SELECT * FROM service";
    private final static String DELETE_BY_NAME = "DELETE FROM service " +
            "WHERE name = ?";
    private final static String INSERT_NEW = "INSERT INTO service (name, description, validity, cost) " +
            "VALUES (?, ?, ?, ?)";


    public ServiceDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public int getIdByKey(Object key) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("ServiceDAO doesn't support 'getIdByKey'");
    }

    /**
     * Deletes service by its name
     *
     * @param key name of service to deleting
     */
    @Override
    public void deleteByKey(Object key) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_NAME)) {
            ps.setString(1, (String) key);
            ps.executeUpdate();

            if (ps.getUpdateCount() == -1) {
                throw new DAOException("Service '"+key+"' doesn't deleted");
            }

        } catch (SQLException e) {
            throw new DAOException("Error while trying delete service '" + key + "'", e);
        }
    }

    /**
     * Returns list of all service from database
     *
     * @return list of service from database
     */
    @Override
    public List<Service> findAll() throws DAOException {
        List<Service> services = new ArrayList<>();

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String description = rs.getString(3);
                String validity = rs.getString(4);
                double cost = rs.getDouble(5);

                services.add(new Service(id, name, description, validity, cost));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to find all service", e);
        }
        return services;
    }


    /**
     * Inserts new service to database
     *
     * @param service service to insert
     */
    public void create(Service service) throws DAOException {
        try (PreparedStatement ps = this.connection.prepareCall(INSERT_NEW)) {

            ps.setString(1, service.getName());
            ps.setString(2, service.getDescription());
            ps.setString(3, service.getValidity());
            ps.setDouble(4, service.getCost());
            ps.executeUpdate();
            if (ps.getUpdateCount() != 1) {
                throw new DAOException("Service '" + service + "' wasn't added to database");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to insert new service '" + service + "' to database", e);
        }
    }
}
