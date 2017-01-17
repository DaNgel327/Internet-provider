package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.BanDAO;
import by.epam.osipov.internet.provider.dao.impl.UserDAO;
import by.epam.osipov.internet.provider.entity.impl.Ban;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

/**
 * Created by Lenovo on 18.01.2017.
 */
public class BanUserCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {

        String passport = content.getParameter("passport");
        String description = content.getParameter("description");

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            UserDAO userDAO = new UserDAO(connection);

            int idUser = userDAO.getIdByKey(passport);
            Ban ban = new Ban(idUser, description);

            BanDAO banDAO = new BanDAO(connection);
            banDAO.createByUserId(ban);
        } catch (Exception e) {
            System.out.println("ex");
        }
        return null;
    }
}
