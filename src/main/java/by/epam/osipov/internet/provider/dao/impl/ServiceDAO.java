package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Service;
import by.epam.osipov.internet.provider.entity.impl.User;
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

    public ServiceDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public int getIdByKey(Object key) throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public boolean deleteByKey(Object key){
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_NAME)) {
            ps.setString(1, (String)key);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Service> findAll() {
        List<Service> services = new ArrayList<>();

        try (Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String description = rs.getString(3);
                String validity = rs.getString(4);
                double cost = rs.getDouble(5);
                services.add(new Service(id, name, description, validity,cost));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
}
