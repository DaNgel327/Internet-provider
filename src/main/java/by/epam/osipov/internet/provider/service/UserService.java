package by.epam.osipov.internet.provider.service;

import by.epam.osipov.internet.provider.dao.impl.UserDAO;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 15.01.2017.
 */
public class UserService {

    public int registerNew(String surname, String name, String patronymic, String passport, String phone, double balance, String email) {

        User user = new User(surname, name, patronymic, passport, phone, balance, email);

        int idUser = -1;

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(connection);

            if(userDAO.create(user)){
                idUser = userDAO.getIdByKey(passport);
            }
        } catch (Exception e) {
            System.out.println("ex");
            return -1;
        }

        return idUser;
    }

    public boolean userExist(String passport) {

        List<User> users = new ArrayList<>();

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()){
            UserDAO userDAO = new UserDAO(connection);

            users = userDAO.findAll();

            for (User user : users) {
                if (user.getPassport().equals(passport)) {
                    return true;
                }
            }

        }  catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
        return false;
    }

}
