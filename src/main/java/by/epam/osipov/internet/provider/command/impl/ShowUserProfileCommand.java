package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.OperationDAO;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.util.List;

/**
 * Created by Lenovo on 01.02.2017.
 */
public class ShowUserProfileCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            return tryExecute(content);
        } catch (ConnectionPoolException | DAOException e) {
            throw new CommandException("Error while trying to execute Sho user profile command", e);
        }
    }

    private String tryExecute(RequestContent content) throws ConnectionPoolException, DAOException {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {

            User user = (User) content.getSessionAttribute("user");
            OperationDAO operationDAO = new OperationDAO(connection);
            List operations = operationDAO.getLastByUserId(user.getId());



            content.setAttribute("operations", operations);
        }
        return "/jsp/profile.jsp";
    }


}
