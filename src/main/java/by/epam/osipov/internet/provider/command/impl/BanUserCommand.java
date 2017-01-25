package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.BanDAO;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Lenovo on 18.01.2017.
 */
public class BanUserCommand implements Command {

    private static final String PASSPORT_PARAM = "passport";
    private static final String DESCRIPTION_PARAM = "description";

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            return tryExecute(content);
        } catch (ConnectionPoolException | DAOException | CommandException e) {
            throw new CommandException("Error while trying to execute Ban User command", e);
        }
    }

    private String tryExecute(RequestContent content) throws ConnectionPoolException, DAOException, CommandException {
        String passport = content.getParameter(PASSPORT_PARAM);
        String description = content.getParameter(DESCRIPTION_PARAM);

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            BanDAO banDAO = new BanDAO(connection);
            banDAO.createByUserPassport(passport, description);
        }

        ShowUsersCommand showUsersCommand = new ShowUsersCommand();

        return showUsersCommand.execute(content);
    }
}
