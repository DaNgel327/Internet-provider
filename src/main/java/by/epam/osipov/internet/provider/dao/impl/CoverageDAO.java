package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.Coverage;
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
public class CoverageDAO extends AbstractDAO {

    private static final String GET_ID_BY_OTHER_PARAMS =
            "SELECT idCoverage FROM city\n" +
                    "JOIN coverage ON city.idCity = coverage.idCity\n" +
                    "WHERE name= ? && street= ? && houseNumber = ?";
    private static final String SELECT_ALL = "SELECT * FROM coverage";

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
    public List<Coverage> findAll() {
        List<Coverage> coverageList = new ArrayList<>();

        try (Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                coverageList.add(new Coverage(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coverageList;
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
        }
        return id;
    }
}
