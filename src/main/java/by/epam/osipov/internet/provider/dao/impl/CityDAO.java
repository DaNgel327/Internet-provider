package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Lenovo on 16.01.2017.
 */
public class CityDAO extends AbstractDAO {

    private static final String GET_ID = "SELECT idCity FROM city\n" +
            "WHERE name = ?";
    private static final String SELECT_ALL = "SELECT * FROM city";


    public CityDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public int getIdByKey(Object key) {
        {
            int id = -1;

            try (PreparedStatement ps = connection.prepareStatement(GET_ID)) {
                ps.setString(1, (String)key);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return id;
        }
    }

    @Override
    public boolean deleteByKey(Object id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<City> findAll() {
        List<City> cities = new ArrayList<>();

        try (Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                cities.add(new City(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

}
