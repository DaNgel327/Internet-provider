package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.ServiceDAO;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Lenovo on 19.01.2017.
 */
public class DeleteServiceCommand implements Command {

    private static final String NAME_PARAM = "name";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            return tryExecute(content);
        } catch (CommandException | DAOException | ConnectionPoolException e) {
            throw new CommandException("Error while trying to execute Delete Service command", e);
        }
    }

    private String tryExecute(RequestContent content) throws CommandException, DAOException, ConnectionPoolException {
        String name = content.getParameter(NAME_PARAM);
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            ServiceDAO serviceDAO = new ServiceDAO(connection);
            serviceDAO.deleteByKey(name);
        }
        return new ShowServiceCommand().execute(content);
    }
}
