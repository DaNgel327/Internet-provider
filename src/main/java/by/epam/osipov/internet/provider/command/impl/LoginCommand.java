package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.AccessDAO;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.exception.EntityNotFoundException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class LoginCommand implements Command {

    public String execute(RequestContent content) throws CommandException {
        try {
            return tryExecute(content);
        } catch (ConnectionPoolException | DAOException | EntityNotFoundException e) {
            throw new CommandException("Error while trying to execute Login command", e);
        }
    }

    private String tryExecute(RequestContent content) throws ConnectionPoolException, EntityNotFoundException, DAOException {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            String login = content.getParameter("user");
            String password = content.getParameter("password");

            AccessDAO accessDAO = new AccessDAO(connection);
            Access access = accessDAO.findByLogin(login);
            if (verifyAccess(access, password)) {
                content.setSessionAttribute("user", login);
                content.setSessionAttribute("role", access.getRole());
                //change from session to normal attr
                content.setSessionAttribute("loginError", null);
            } else {
                //change from session to normal attr
                content.setSessionAttribute("loginError", "Ошибка авторизации.\nНеверное имя пользователя или пароль");
            }
        }
        return "/";
    }


    public boolean verifyAccess(Access access, String password) {
        return access != null && access.getPassword().equals(password);
    }


}
