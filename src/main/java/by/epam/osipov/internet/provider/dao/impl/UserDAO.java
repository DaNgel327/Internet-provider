package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.exception.EntityNotFoundException;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class UserDAO extends AbstractDAO {

    private final static String SELECT_ALL = "SELECT * FROM user";

    private final static String GET_EMAIL_BY_LOGIN = "SELECT email FROM user \n" +
            "JOIN contract ON user.idUser = contract.idUser\n" +
            "JOIN access ON access.idAccess = contract.idAccess\n" +
            "WHERE login = ?";

    private final static String SELECT_ID_BY_PASSPORT = "SELECT idUser FROM user\n" +
            "WHERE passport = ?";

    private final static String INSERT_NEW =
            "INSERT INTO user (sName, name, pName, passport, phone, balance, email) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final static String DELETE_BY_ID = "DELETE FROM user\n" +
            "WHERE passport = ?";

    public UserDAO(ConnectionProxy connection) {
        super(connection);
    }

    /**
     * Returns user's id from database by passport.
     *
     * @param key user's passport
     * @return user's id
     */
    @Override
    public int getIdByKey(Object key) throws EntityNotFoundException, DAOException {
        int id;

        try (PreparedStatement ps = connection.prepareStatement(SELECT_ID_BY_PASSPORT)) {
            ps.setString(1, (String) key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new EntityNotFoundException("User with key '" + key + "' not found");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to find user by key '" + key + "'", e);
        }

        return id;
    }

    /**
     * Deletes user from database by user's id
     *
     * @param key user's id
     */
    @Override
    public void deleteByKey(Object key) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            ps.setString(1, (String) key);
            ps.executeUpdate();

            if (ps.getUpdateCount() == -1) {
                throw new DAOException("User '" + key + "' doesn't deleted");
            }

        } catch (SQLException e) {
            throw new DAOException("Error while trying to delete user with key '" + key + "'", e);
        }
    }

    /**
     * Returns list with all users in database
     *
     * @return list with users
     */
    @Override
    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();

        try (Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {

                int id = rs.getInt(1);
                String surname = rs.getString(2);
                String name = rs.getString(3);
                String patronymic = rs.getString(4);
                String passport = rs.getString(5);
                String phone = rs.getString(6);
                double balance = rs.getDouble(7);
                String email = rs.getString(8);

                users.add(new User(id, surname, name, patronymic, passport, phone, balance, email));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to find all users", e);
        }
        return users;
    }

    /**
     * Inserts new user to database
     *
     * @param user the user to insert
     */
    public boolean create(User user) throws DAOException {

        try (PreparedStatement ps = this.connection.prepareCall(INSERT_NEW)) {
            ps.setString(1, user.getSurname());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPatronymic());
            ps.setString(4, user.getPassport());
            ps.setString(5, user.getPhone());
            ps.setDouble(6, user.getBalance());
            ps.setString(7, user.getEmail());
            ps.executeUpdate();

            if (ps.getUpdateCount() != 1) {
                throw new DAOException("User '" + user + "' doesn't inserted");
            }
        } catch (SQLException e) {
            throw new DAOException("Ero while trying insert user '" + user + "'", e);
        }
        return false;
    }

    /**
     * Returns user's email by access.
     *
     * @param access user's access
     * @return user's email
     */
    public String getEmailByAccess(Access access) throws EntityNotFoundException, DAOException {

        String email;

        try (PreparedStatement ps = connection.prepareStatement(GET_EMAIL_BY_LOGIN)) {
            ps.setString(1, access.getLogin());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                email = rs.getString(1);
            } else {
                throw new EntityNotFoundException("Access '" + access + "' not found in database");
            }
        } catch (SQLException e) {
            throw new DAOException("Eor while trying get email by user's access '" + access + "'", e);
        }
        return email;
    }
}
