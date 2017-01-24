package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.AccessDAO;
import by.epam.osipov.internet.provider.dao.impl.ContractDAO;
import by.epam.osipov.internet.provider.dao.impl.UserDAO;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Lenovo on 17.01.2017.
 */
public class DeleteUserCommand implements Command {

    private static final String PASSPORT_PARAM = "passport";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(RequestContent content) throws CommandException {
        int idUser = deleteUser(content);
        deleteAccess(idUser);
        deleteContract(idUser);
        return new ShowUsersCommand().execute(content);
    }

    private boolean deleteAccess(int idUser) {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            AccessDAO accessDAO = new AccessDAO(connection);

            if (!accessDAO.deleteByKey(idUser)) {
                return false;
            }
        } catch (ConnectionPoolException e) {
            LOGGER.error("Error while trying to execute delete user command. Method: deleteAccess " + e);
        }
        return true;
    }

    private int deleteUser(RequestContent content) {
        String passport = content.getParameter(PASSPORT_PARAM);
        int idUser = -1;
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            UserDAO userDAO = new UserDAO(connection);
            idUser = userDAO.getIdByKey(passport);

            if (!userDAO.deleteByKey(passport)) {
                L
                return -1;
            }
        } catch (Exception e) {
            System.out.println("ex");
        }
        return idUser;
    }

    private boolean deleteContract(int idUser) {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {

            ContractDAO contractDAO = new ContractDAO(connection);

            if (!contractDAO.deleteByKey(idUser)) {
                System.out.println("exception");
                return false;
            }
        } catch (Exception e) {
            System.out.println("ex");
        }
        return true;
    }


}
