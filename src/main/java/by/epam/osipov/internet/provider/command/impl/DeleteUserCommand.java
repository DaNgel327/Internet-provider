package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.AccessDAO;
import by.epam.osipov.internet.provider.dao.impl.ContractDAO;
import by.epam.osipov.internet.provider.dao.impl.UserDAO;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

/**
 * Created by Lenovo on 17.01.2017.
 */
public class DeleteUserCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {
        int idUser = deleteUser(content);
        deleteAccess(idUser);
        deleteContract(idUser);
        return new ShowUsersCommand().execute(content);
    }

    private boolean deleteAccess(int idUser){
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            AccessDAO accessDAO = new AccessDAO(connection);

            if(!accessDAO.deleteByKey(idUser)){
                return false;
            }

        } catch (Exception e) {
            System.out.println("ex");
        }
        return true;
    }

    private int deleteUser(RequestContent content) {
        String passport = content.getParameter("passport");
        int idUser = -1;
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            UserDAO userDAO = new UserDAO(connection);
            idUser = userDAO.getIdByKey(passport);

            if (!userDAO.deleteByKey(passport)) {
                System.out.println("exception");
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
