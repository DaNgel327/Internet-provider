package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Ban;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 17.01.2017.
 */
public class BanDAO extends AbstractDAO {

    private final static String SELECT_ALL = "SELECT * FROM ban";
    private final static String INSERT_BY_USER_ID = "INSERT INTO ban (idUser, description) " +
            "VALUES ( " +
            "(SELECT idUser FROM user WHERE passport = ?),?)";
    private final static String DELETE_BY_USER_ID =
            "DELETE FROM ban WHERE idUser = (SELECT idUser FROM user WHERE passport = ?)";

    public BanDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public int getIdByKey(Object key) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("BanDAO doesn't support 'getIdByKey'");
    }

    /**
     * Deletes ban from user by user's id
     *
     * @param key user's id
     */
    @Override
    public void deleteByKey(Object key) throws DAOException {
        try (PreparedStatement st = connection.prepareStatement(DELETE_BY_USER_ID)) {
            st.setString(1, (String) key);
            st.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while deleting ban by user id '" + key + "'", e);
        }
    }

    /**
     * Returns all bans from database
     *
     * @return list of bans
     */
    @Override
    public List<Ban> findAll() throws DAOException {
        List<Ban> bans = new ArrayList<>();

        try (Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                int banId = rs.getInt(1);
                String description = rs.getString(2);
                bans.add(new Ban(banId, description));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding all bans", e);
        }
        return bans;
    }

    /**
     * Creates new ban by user's passport
     *
     * @param passport    user's passport
     * @param description description of ban
     */
    public void createByUserPassport(String passport, String description) throws DAOException {
        try (PreparedStatement ps = this.connection.prepareCall(INSERT_BY_USER_ID)) {

            ps.setString(1, passport);
            ps.setString(2, description);
            ps.executeUpdate();
            if (ps.getUpdateCount() != 1) {
                throw new DAOException("User '" + passport + "' wasn't banned");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to ban user '" + passport + "'", e);
        }
    }
}
