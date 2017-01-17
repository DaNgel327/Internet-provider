package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public int getIdByKey(Object key) {
        {
            int id = -1;

            try (PreparedStatement ps = connection.prepareStatement(SELECT_ID_BY_PASSPORT)) {
                ps.setString(1, (String)key);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("bad close connection");
                }
            }
            return id;
        }
    }

    @Override
    public boolean deleteByKey(Object key) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)) {
            ps.setString(1, (String)key);
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


    public UserDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getString(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } //catch (PoolException e) {
            //  e.printStackTrace();
            //}
            catch (Exception e) {
                System.out.println("error");
            }
        }
        return users;
    }

    public boolean create(User user) {

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
                System.out.println("user was not added");
            }
        } catch (SQLException e) {
            System.out.println("Sql exception with inserting new user" + e);
            return false;
        }

        return true;

    }

    public String getEmailByAccess(Access access) {

        String email = null;

        try (PreparedStatement ps = connection.prepareStatement(GET_EMAIL_BY_LOGIN)) {
            ps.setString(1, access.getLogin());
            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                email = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("bad close connection");
            }
        }
        return email;
    }
}
