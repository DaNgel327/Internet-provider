package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.Coverage;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lenovo on 16.01.2017.
 */
public class CoverageDAO extends AbstractDAO {

    private static final String GET_ID_BY_OTHER_PARAMS =
            "SELECT idCoverage FROM city\n" +
                    "JOIN coverage ON city.idCity = coverage.idCity\n" +
                    "WHERE name= ? && street= ? && houseNumber = ?";

    public CoverageDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public int getIdByKey(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteByKey(Object id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List findAll() {
        return null;
    }

    public int getIdByParameters(Coverage coverage, City city) {

        int id = -1;

        String cityName = city.getName();
        String streetName = coverage.getStreet();
        int houseNumber = coverage.getHouseNumber();

        try (PreparedStatement ps = connection.prepareStatement(GET_ID_BY_OTHER_PARAMS);) {
            ps.setString(1, cityName);
            ps.setString(2, streetName);
            ps.setInt(3, houseNumber);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("bad close connection");
            }
        }
        return id;
    }
}
