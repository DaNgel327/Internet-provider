package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Access;
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
    private final static String SET_CHECKS="SET foreign_key_checks = 0";

    private final static String SELECT_BY_ID = "SELECT * FROM access WHERE id = ?";
    private final static String UPDATE_PASS = "UPDATE access SET password = ? WHERE login = ?";
    private final static String DELETE_BY_ID = "DELETE FROM ACCESS WHERE login = ?";

    private final static String SELECT_BY_LOGIN = "SELECT * FROM access WHERE login = ?";

    private final static String DELETE_BY_USER_ID = "DELETE FROM access\n" +
            "where idAccess = (select idAccess FROM contract\n" +
            "where idUser = ?)";

    public AccessDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public int getIdByKey(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    //delete by userID
    public boolean deleteByKey(Object key) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_USER_ID)) {
            ps.setInt(1, (Integer)key);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("bad close connection");
            }
        }
        return true;
    }

    @Override
    public List<Access> findAll() {
        List<Access> accesses = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                accesses.add(new Access(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getByte(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accesses;
    }

    public Access findByLogin(String login) {
        Access access = null;

        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                access = new Access(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getByte(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return access;
    }

    public boolean create(Access access) {
        try (PreparedStatement ps = this.connection.prepareCall(INSERT_NEW)) {

            ps.setString(1, access.getLogin());
            ps.setString(2, access.getPassword());
            ps.setInt(3, access.getRole());
            ps.executeUpdate();
            System.out.println("execute update");
        } catch (SQLException e) {
            System.out.println("Sql exception with inserting new user" + e);
            return false;
        } finally {
            connection.close();
        }

        return true;
    }

}
