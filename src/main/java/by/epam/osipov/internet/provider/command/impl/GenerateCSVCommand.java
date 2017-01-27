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
import by.epam.osipov.internet.provider.generator.CoverageFileGenerator;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 27.01.2017.
 */
public class GenerateCSVCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {

        try {
            return tryExecute(content);
        } catch (ConnectionPoolException | IOException | SAXException | ParserConfigurationException | DAOException e) {
            throw new CommandException("Error while trying to generate csv-file", e);
        }
    }

    private String tryExecute(RequestContent content) throws ConnectionPoolException, DAOException, ParserConfigurationException, SAXException, IOException {










        List<String> addresses = null;

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            CoverageDAO coverageDAO = new CoverageDAO(connection);

            CityDAO cityDAO = new CityDAO(connection);

            List<Coverage> coverageList = coverageDAO.findAll();
            List<City> cityList = cityDAO.findAll();

            addresses = CoverageFileGenerator.getFullAddresses(coverageList, cityList);

        }

        List<String> lines = new ArrayList<>();

        for (String s : addresses) {
            String coordinates = CoverageFileGenerator.getLatLongPositions(s);
            String line = s + "," + coordinates;
            lines.add(line);
        }

        CoverageFileGenerator.createFile(lines);

        return null;
    }
}
