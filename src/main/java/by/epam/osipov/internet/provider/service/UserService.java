package by.epam.osipov.internet.provider.service;

import by.epam.osipov.internet.provider.dao.impl.UserDAO;
import by.epam.osipov.internet.provider.entity.impl.Ban;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.exception.EntityNotFoundException;
import by.epam.osipov.internet.provider.exception.RegistrationException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 15.01.2017.
 */
public class UserService {


    /**
     * Register new user. If something
     * wrong - catch errors
     *
     * @param surname    user's surname
     * @param name       user's name
     * @param patronymic user's patronymic (third name)
     * @param passport   user's passport
     * @param phone      user's phone
     * @param balance    user's start balance
     * @param email      user's email
     * @return user's id
     */
    public int registerNew(String surname, String name, String patronymic, String passport,
                           String phone, double balance, String email) throws RegistrationException {
        try {
            return tryRegisterNew(surname, name, patronymic, passport, phone, balance, email);
        } catch (ConnectionPoolException | EntityNotFoundException | DAOException e) {
            throw new RegistrationException("Error while trying to register new use", e);
        }
    }

    /**
     * Try to register new user.
     * If something wrong throws an exception.
     *
     * @param surname    user's surname
     * @param name       user's name
     * @param patronymic user's patronymic (third name)
     * @param passport   user's passport
     * @param phone      user's phone
     * @param balance    user's start balance
     * @param email      user's email
     * @return user's id
     */
    private int tryRegisterNew(String surname, String name, String patronymic, String passport,
                               String phone, double balance, String email) throws ConnectionPoolException, DAOException, EntityNotFoundException {

        User user = new User(surname, name, patronymic, passport, phone, balance, email);
        int userId;

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(connection);

            userDAO.create(user);
            userId = userDAO.getIdByKey(passport);
        }

        return userId;
    }

    /**
     * Checks if user exist in database by passport
     *
     * @param passport user's passport to check
     * @return true if user exist in database. Otherwise returns false
     */
    public boolean userExist(String passport) throws ConnectionPoolException, DAOException {

        List<User> users;

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(connection);

            users = userDAO.findAll();

            for (User user : users) {
                if (user.getPassport().equals(passport)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Returns list of banned users
     *
     * @param users list of all users
     * @param bans  list of bans
     * @return list of banned users
     */
    public List<User> getBannedUsers(List<User> users, List<Ban> bans) {

        List<User> result = new ArrayList<>();

        List<Integer> bannedIds = new ArrayList<>();

        for (Ban ban : bans) {
            bannedIds.add(ban.getIdUser());
        }

        for (User user : users) {
            int idUser = user.getId();
            boolean flag = bannedIds.contains(idUser);
            if (flag) {
                result.add(user);
            }
        }

        return result;
    }

    /**
     * Returns simple users from database.
     * Simple users - users which are not banned.
     *
     * @param users list of all users
     * @param bans  list of bans
     * @return list of simple users
     */
    public List<User> getSimpleUsers(List<User> users, List<Ban> bans) {
        List<User> banned = getBannedUsers(users, bans);

        users.removeAll(banned);

        return users;
    }
}
