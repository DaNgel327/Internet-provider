package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Ban;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 17.01.2017.
 */
public class BanDAO extends AbstractDAO {

    private final static String SELECT_ALL = "SELECT * FROM ban";
    private final static String INSERT_BY_USER_ID = "INSERT INTO ban (idUser, description)  \n" +
            "VALUES (\n" +
            "(SELECT idUser FROM user WHERE passport = ?),?)";
    private final static String DELETE_BY_USER_ID =
            "DELETE FROM ban WHERE idUser = (SELECT idUser FROM user WHERE passport = ?)";

    public BanDAO(ConnectionProxy connection) {
        super(connection);
    }

    public boolean createByUserPassport(String passport, String description){
        try (PreparedStatement ps = this.connection.prepareCall(INSERT_BY_USER_ID)) {

            ps.setString(1, passport);
            ps.setString(2, description);
            ps.executeUpdate();
            if (ps.getUpdateCount() != 1) {
                System.out.println("user was not added");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Sql exception with inserting new user" + e);
            return false;
        }

        return true;
    }

    @Override
    public int getIdByKey(Object key) throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public boolean deleteByKey(Object key) {

        try(PreparedStatement st = connection.prepareStatement(DELETE_BY_USER_ID)){

            st.setString(1,(String) key);
            st.execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public List<Ban> findAll() {
        List<Ban> bans = new ArrayList<>();

        try (Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                bans.add(new Ban(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bans;
    }

}
