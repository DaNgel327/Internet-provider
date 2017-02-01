package by.epam.osipov.internet.provider.service;

import by.epam.osipov.internet.provider.dao.impl.AccessDAO;
import by.epam.osipov.internet.provider.dao.impl.UserDAO;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.exception.ServiceException;
import by.epam.osipov.internet.provider.mail.ssl.EmailSender;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Random;

/**
 * Created by Lenovo on 16.01.2017.
 */
public class AccessService {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int LOGIN_SIZE = 8;
    private static final int PASSWORD_SIZE = 10;
    private static final byte USER_ROLE = 1;

    /**
     * Generates Access object with unique fields.
     * If something wrong catch exceptions
     */
    public Access generateUniqueAccess() throws ServiceException {
        try {
            return tryGenerateUniqueAccess();
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException("Error while trying to generate unique access", e);
        }
    }

    private Access tryGenerateUniqueAccess() throws ConnectionPoolException, DAOException {
        Access access = null;
        boolean exist = true;

        //while access exist in DB, try to generate new
        while (exist) {
            access = generateAccess();
            exist = tryCheckIsLoginExist(access.getLogin());
        }

        return access;
    }

    // совпадают ли pass1 и pass2 проверит js
    public void changePassword(String login, String newPass) throws ServiceException {


        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {

            AccessDAO accessDAO = new AccessDAO(connection);
            Access access = accessDAO.findByLogin(login);

            access.setPassword(newPass);
            accessDAO.update(access);
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException("Error while trying to change password", e);
        }

    }

    public void changeLogin(String curLogin, String newLogin) throws ServiceException {

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {

            AccessDAO accessDAO = new AccessDAO(connection);
            Access access = accessDAO.findByLogin(curLogin);

            access.setLogin(newLogin);
            accessDAO.update(access);

        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException("Error while trying to change login", e);
        }
    }

    public boolean isOldPassCorrect(String login, String password) throws ServiceException {
        Access access;
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            AccessDAO accessDAO = new AccessDAO(connection);
            access = accessDAO.findByLogin(login);
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException("Error while trying check old password", e);
        }
        return access.getPassword().equals(password);
    }


    /**
     * Generates Access object with random fields.
     */
    private Access generateAccess() {
        StringBuilder login = generateSequence(LOGIN_SIZE, true);
        StringBuilder password = generateSequence(PASSWORD_SIZE, false);

        return new Access(login.toString(), password.toString(), USER_ROLE);
    }

    /**
     * Generates sequence of letters and numbers
     * using random.
     *
     * @param length length of sequence
     * @return sequence of letters and numbers.
     */
    private StringBuilder generateSequence(int length, boolean onlyNumbers) {
        StringBuilder sequence = new StringBuilder();

        Random random = new Random();

        if (onlyNumbers) {
            for (int i = 0; i < length; i++) {
                sequence.append(random.nextInt(10));
            }
            return sequence;
        }

        for (int i = 0; i < length; i++) {
            boolean letter = random.nextBoolean();
            if (letter) {
                boolean lowerCase = random.nextBoolean();
                sequence.append(getLetter(random.nextInt(ALPHABET.length()), lowerCase));
            } else {
                sequence.append(random.nextInt(10));
            }
        }

        return sequence;
    }

    /**
     * Returns letter from English ALPHABET
     * by index, not ignored case.
     *
     * @param index     letter's index in ALPHABET
     * @param lowerCase true if letter lowercase.
     *                  False if letter uppercase.
     * @return symbol from ALPHABET
     */
    private char getLetter(int index, boolean lowerCase) {
        return lowerCase ? ALPHABET.charAt(index) : ALPHABET.toUpperCase().charAt(index);
    }

    /**
     * Returns true if input login
     * exist in database
     *
     * @param login login to check
     * @return true if login exist. Otherwise - false
     */
    public boolean checkIsLoginExist(String login) throws ServiceException {
        try {
            return tryCheckIsLoginExist(login);
        } catch (ConnectionPoolException | DAOException e) {
            throw new ServiceException("Error while trying to check if login exis", e);
        }
    }

    private boolean tryCheckIsLoginExist(String login) throws ConnectionPoolException, DAOException {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            AccessDAO accessDAO = new AccessDAO(connection);
            List<Access> accesses;

            accesses = accessDAO.findAll();

            for (Access access : accesses) {
                if (access.getLogin().equals(login)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void sendAccessToUser(Access access) throws DAOException, ConnectionPoolException, MessagingException {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(connection);

            String email = userDAO.getEmailByAccess(access);

            EmailSender emailSender = new EmailSender();
            emailSender.sendAccess(access, email);
        }
    }
}
