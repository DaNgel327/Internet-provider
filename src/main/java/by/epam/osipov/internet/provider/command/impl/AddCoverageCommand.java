package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.CityDAO;
import by.epam.osipov.internet.provider.dao.impl.CoverageDAO;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.Coverage;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

/**
 * Created by Lenovo on 29.01.2017.
 */
public class AddCoverageCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {

        try {
            return tryExecute(content);
        } catch (ConnectionPoolException | DAOException e) {
            throw new CommandException("error while trying to execute Add coverage command", e);
        }
    }

    private String tryExecute(RequestContent content) throws ConnectionPoolException, DAOException {

        String cityName = content.getParameter("city");
        String street = content.getParameter("street");
        int house = Integer.parseInt(content.getParameter("house"));
        int building = Integer.parseInt(content.getParameter("building"));

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {

            CityDAO cityDAO = new CityDAO(connection);
            int cityId = cityDAO.getIdByKey(cityName);

            City city = new City(cityId, cityName);
            Coverage coverage = new Coverage(cityId, street, house, building);

            if(coverageExist(coverage, city)){
                content.setAttribute("complete", false);
                return "/jsp/coverage.jsp";
            }

            if (!cityExist(cityName)) {
                cityDAO.create(cityName);
            }

            CoverageDAO coverageDAO = new CoverageDAO(connection);
            coverageDAO.create(coverage);
        }

        content.setAttribute("complete", true);
        return "/jsp/coverage.jsp";
    }

    private boolean cityExist(String cityName) throws ConnectionPoolException, DAOException {

        int cityId = 0;
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {

            CityDAO cityDAO = new CityDAO(connection);
            cityId = cityDAO.getIdByKey(cityName);
        }
        ;
        return cityId != 0;
    }

    private boolean coverageExist(Coverage coverage, City city) throws ConnectionPoolException, DAOException {

        if (!cityExist(city.getName())) {
            return false;
        }
        int cityId = 0;

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            CoverageDAO coverageDAO = new CoverageDAO(connection);

            cityId = coverageDAO.getIdByParameters(coverage, city);
        }
        return cityId != 0;
    }
}
