package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.AccessDAO;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class LoginCommand implements Command {

    public String execute(RequestContent content) {
        try {
            String login = content.getParameter("user");
            String password = content.getParameter("password");
            ConnectionProxy connection = null;
            try {
                connection = ConnectionPool.getInstance().getConnection();
            } catch (Exception e) {
                System.out.println("Exception while trying to get connection in service");
            }
            AccessDAO accessDAO = new AccessDAO(connection);
            Access access = accessDAO.findByLogin(login);
            if (verifyAccess(access, password)) {
                content.setSessionAttribute("user", login);
                content.setSessionAttribute("role", access.getRole());
                //change from session to normal attr
                content.setSessionAttribute("loginError", null);
            }else{
                //change from session to normal attr
                content.setSessionAttribute("loginError", "Ошибка авторизации.\nНеверное имя пользователя или пароль");
            }
        } catch (Exception e) {
            System.out.println("ex");
        }

        return "/";
    }


    public boolean verifyAccess(Access access, String password) {
        return access != null && access.getPassword().equals(password);
    }


}
