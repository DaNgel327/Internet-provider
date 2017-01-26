package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.AccessDAO;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import javax.servlet.http.HttpSession;

/**
 * Created by Lenovo on 26.01.2017.
 */
public class ShowAccountCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {

        try(ConnectionProxy connection = ConnectionPool.getInstance().getConnection()){
/*
            String curPass = content.getParameter("currentPassword");
            String newPass = content.getParameter("newPassword");
            String newPassConfirm = content.getParameter("confirm");

            String login= content.getSessionAttribute("user").toString();

            UserService userService = new UserService();
            User updatedUser = userService.changePassword(user, curPass, newPass);
            boolean isCommandFailed = (updatedUser == null);
            packAttributes(session, isCommandFailed, curPass, newPass, updatedUser);
            String page = MappingManager.getProperty(KEY_FOR_PAGE);
            return page;
*/
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
        return null;
    }
}
