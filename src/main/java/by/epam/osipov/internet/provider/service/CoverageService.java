package by.epam.osipov.internet.provider.service;

import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.CityDAO;
import by.epam.osipov.internet.provider.dao.impl.CoverageDAO;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.Coverage;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

/**
 * Created by Lenovo on 01.02.2017.
 */
public class CoverageService {

    public int defineCoverage(City city, String street, int houseNumber, int buildig) throws DAOException, ConnectionPoolException {

        int idCity;
        int idCoverage;

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {

            CityDAO cityDAO = new CityDAO(connection);
            idCity = cityDAO.getIdByKey(city.getName());


            // CHAAANGE 1
            Coverage coverage = new Coverage(idCity, street, houseNumber, buildig);

            CoverageDAO coverageDAO = new CoverageDAO(connection);

            idCoverage = coverageDAO.getIdByParameters(coverage, city);
        }

        return idCoverage;
    }

}
