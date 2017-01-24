package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.ServiceDAO;
import by.epam.osipov.internet.provider.entity.impl.Service;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Lenovo on 19.01.2017.
 */
public class AddServiceCommand implements Command {

    private static final String NAME_PARAM = "name";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String VALIDITY_PARAM = "validity";
    private static final String COST_PARAM = "cost";


    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(RequestContent content) throws CommandException {

        String name = content.getParameter(NAME_PARAM);
        String description = content.getParameter(DESCRIPTION_PARAM);
        String validity = content.getParameter(VALIDITY_PARAM).replaceAll("\\+", " ");
        double cost = Double.parseDouble(content.getParameter(COST_PARAM));

        Service service = new Service(name, description, validity, cost);

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            ServiceDAO serviceDAO = new ServiceDAO(connection);
            serviceDAO.create(service);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Error while trying to execute add service command " + e);
        }
        return new ShowServiceCommand().execute(content);
    }
}
