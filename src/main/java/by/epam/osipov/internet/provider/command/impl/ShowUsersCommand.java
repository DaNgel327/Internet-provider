package by.epam.osipov.internet.provider.command.impl;


import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.AccessDAO;
import by.epam.osipov.internet.provider.dao.impl.UserDAO;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 13.01.2017.
 */
public class ShowUsersCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()){

            UserDAO userDAO = new UserDAO(connection);
            List<User> users = userDAO.findAll();

            //session?
            content.setSessionAttribute("users", users);

        } catch (Exception e) {
            System.out.println("ex");
        }

        return "/jsp/admin-page.jsp";
    }
}
