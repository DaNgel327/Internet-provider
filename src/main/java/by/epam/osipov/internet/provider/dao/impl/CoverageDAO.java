package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.Coverage;
import by.epam.osipov.internet.provider.exception.DAOException;
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
                    "WHERE name = ? AND street = ? AND houseNumber = ? AND building = ?";
    private static final String SELECT_ALL = "SELECT * FROM coverage";

    private static final String CREATE = "INSERT INTO coverage (idCity, street, house, building)" +
            "VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_USER_ID = "SELECT  coverage.idCoverage, coverage.idCity, coverage.street, coverage.houseNumber, coverage.building FROM\n" +
            "    coverage\n" +
            "        JOIN\n" +
            "    contract ON coverage.idCoverage = contract.idCoverage\n" +
            "        JOIN\n" +
            "    user ON user.idUser = contract.idUser\n" +
            "    where user.idUser = ?" +
            " LIMIT 1";

    public CoverageDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public int getIdByKey(Object key) {
        throw new UnsupportedOperationException("CoverageDAO doesn't support 'getIdByKey' operation");
    }

    @Override
    public void deleteByKey(Object id) {
        throw new UnsupportedOperationException("CoverageDAO doesn't support 'deleteByKey' operation");
    }

    /**
     * Returns list of all coverage zones from database
     *
     * @return list of coverage zones
     */
    @Override
    public List<Coverage> findAll() throws DAOException {
        List<Coverage> coverageList = new ArrayList<>();

        try (Statement st = connection.createStatement();) {
            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                int id = rs.getInt(1);
                int cityId = rs.getInt(2);
                String street = rs.getString(3);
                int houseNumber = rs.getInt(4);
                int building = rs.getInt(5);

                coverageList.add(new Coverage(id, cityId, street, houseNumber, building));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to find all coverage zones", e);
        }
        return coverageList;
    }

    /**
     * Returns id of coverage zone by other params
     *
     * @param coverage coverage zone which id we need
     * @param city     city of this coverage zone
     * @return id of coverage zone
     */
    public int getIdByParameters(Coverage coverage, City city) throws DAOException {

        int id = 0;

        String cityName = city.getName();
        String streetName = coverage.getStreet();
        int houseNumber = coverage.getHouseNumber();
        int building = coverage.getBuilding();

        try (PreparedStatement ps = connection.prepareStatement(GET_ID_BY_OTHER_PARAMS);) {
            ps.setString(1, cityName);
            ps.setString(2, streetName);
            ps.setInt(3, houseNumber);
            ps.setInt(4, building);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get id of '" + cityName + " " + streetName + " "
                    + houseNumber + "'");
        }
        return id;
    }

    public void create(Coverage coverage) throws DAOException {
        try (PreparedStatement ps = this.connection.prepareCall(CREATE)) {

            int cityId = coverage.getIdCity();
            String street = coverage.getStreet();
            int house = coverage.getHouseNumber();
            int building = coverage.getBuilding();

            ps.setInt(1, cityId);
            ps.setString(2, street);
            ps.setInt(3, house);
            ps.setInt(4, building);


            ps.executeUpdate();
            if (ps.getUpdateCount() != 1) {
                throw new DAOException("Coverage cityId = '" + cityId + "' wasn't created");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to create coverage", e);
        }
    }

    public Coverage getByUserId(int id) throws DAOException {

        Coverage coverage = null;
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_USER_ID);) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int coverageId = rs.getInt(1);
                int cityId = rs.getInt(2);
                String street = rs.getString(3);
                int house = rs.getInt(4);
                int building = rs.getInt(5);
                coverage = new Coverage(coverageId, cityId, street, house, building);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return coverage;
    }
}
