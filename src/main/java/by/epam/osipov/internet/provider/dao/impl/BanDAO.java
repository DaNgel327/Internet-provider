package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Ban;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

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
    private final static String SELECT_PASSPORT_AND_DESCRIPTION = "SELECT passport, description FROM ban\n" +
            "JOIN user ON ban.idUser = user.idUser";

    public BanDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public int getIdByKey(Object key) throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public boolean deleteByKey(Object key) throws UnsupportedOperationException {
        return false;
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

    public Map<String, String> getPassportAndDescription(){
        Map<String, String> result = new HashMap<>();

        try (Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                result.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
