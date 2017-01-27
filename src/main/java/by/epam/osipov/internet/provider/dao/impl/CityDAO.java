package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.exception.EntityNotFoundException;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 16.01.2017.
 */
public class CityDAO extends AbstractDAO {

    private static final String GET_ID_BY_NAME = "SELECT idCity FROM city " + "WHERE name = ?";
    private static final String SELECT_ALL = "SELECT * FROM city";


    public CityDAO(ConnectionProxy connection) {
        super(connection);
    }

    /**
     * Returns city id by name
     *
     * @param key city name
     */
    @Override
    public int getIdByKey(Object key) throws DAOException {

        int id;

        try (PreparedStatement ps = connection.prepareStatement(GET_ID_BY_NAME)) {
            ps.setString(1, (String) key);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new EntityNotFoundException("City '" + key + "' wasn't found");
            }
        } catch (SQLException | EntityNotFoundException e) {
            throw new DAOException("Error while trying to get id of '" + key + "' city", e);
        }
        return id;

    }

    @Override
    public void deleteByKey(Object id) {
        throw new UnsupportedOperationException("CityDAO doesn't support 'deleteByKey'");
    }

    /**
     * Returns list of all cities in database
     *
     * @return list of cities
     */
    @Override
    public List<City> findAll() throws DAOException {
        List<City> cities = new ArrayList<>();

        try (Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                int cityId = rs.getInt(1);
                String cityName = rs.getString(2);
                cities.add(new City(cityId, cityName));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to find all cities");
        }
        return cities;
    }
}
