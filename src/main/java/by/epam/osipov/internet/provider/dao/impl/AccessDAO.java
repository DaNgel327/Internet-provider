package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.exception.DAOException;
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
public class AccessDAO extends AbstractDAO {

    private final static String SELECT_ALL = "SELECT * FROM access";
    private final static String INSERT_NEW = "INSERT INTO access (login, password, role) " +
            "VALUES (?, ?, ?)";
    private final static String SET_CHECKS = "SET foreign_key_checks = 0";

    private final static String UPDATE_BY_ID = "UPDATE access " +
            "SET login = ?, " +
            "password = ?, " +
            "role = ? " +
            "WHERE idAccess = ?";

    private final static String SELECT_BY_LOGIN = "SELECT * FROM access WHERE BINARY login = ?";

    private final static String DELETE_BY_USER_ID = "DELETE FROM access\n" +
            "WHERE idAccess = (select idAccess FROM contract\n" +
            "WHERE idUser = ?)";

    public AccessDAO(ConnectionProxy connection) {
        super(connection);
    }


    @Override
    public int getIdByKey(Object key) {
        throw new UnsupportedOperationException("AccessDAO doesn't support 'getIdByKey'");
    }

    /**
     * Deletes access from database by user's id
     *
     * @param key user's id
     */
    @Override
    public void deleteByKey(Object key) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_USER_ID)) {
            ps.setInt(1, (Integer) key);
            ps.executeUpdate();

            if (ps.getUpdateCount() == -1) {
                throw new DAOException("Access of user '" + key + "' wasn't deleted");
            }

        } catch (SQLException e) {
            throw new DAOException("Error while trying to delete access of user '" + key + "'", e);
        }
    }

    /**
     * Returns list of accesses from database
     *
     * @return list of accesses
     */
    @Override
    public List<Access> findAll() throws DAOException {
        List<Access> accesses = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                int accessId = rs.getInt(1);
                String login = rs.getString(2);
                String password = rs.getString(3);
                byte role = rs.getByte(4);

                accesses.add(new Access(accessId, login, password, role));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all accesses");
        }
        return accesses;
    }

    /**
     * Returns user's access by login
     *
     * @param login user's login
     * @return user's access
     */
    public Access findByLogin(String login) throws DAOException {
        Access access = null;
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int accessId = rs.getInt(1);
                String password = rs.getString(3);
                byte role = rs.getByte(4);

                access = new Access(accessId, login, password, role);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to find access by login '" + login + "'", e);
        }
        return access;
    }

    /**
     * Inserts new access to database
     *
     * @param access access to insert
     */
    public void create(Access access) throws DAOException {
        try (PreparedStatement ps = this.connection.prepareCall(INSERT_NEW)) {
            ps.setString(1, access.getLogin());
            ps.setString(2, access.getPassword());
            ps.setByte(3, access.getRole());
            ps.executeUpdate();
            if (ps.getUpdateCount() == -1) {
                throw new DAOException("Access '" + access + "' wasn't added to database");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying create new access '" + access + "'", e);
        }
    }

    public void update(Access access) throws DAOException {
        try (PreparedStatement ps = this.connection.prepareCall(UPDATE_BY_ID)) {
            ps.setString(1, access.getLogin());
            ps.setString(2, access.getPassword());
            ps.setByte(3, access.getRole());
            ps.setInt(4, access.getId());
            ps.executeUpdate();
            if (ps.getUpdateCount() == -1) {
                throw new DAOException("Access '" + access + "' wasn't updated");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying update access '" + access + "'", e);
        }
    }

}
