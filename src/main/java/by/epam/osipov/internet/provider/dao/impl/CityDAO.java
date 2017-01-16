package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lenovo on 16.01.2017.
 */
public class CityDAO extends AbstractDAO {

    private static final String GET_ID = "SELECT idCity FROM city\n" +
            "WHERE name = ?";


    public CityDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public List findAll() {
        return null;
    }

    public int getId(String cityName){

        int id = -1;

        try (PreparedStatement ps = connection.prepareStatement(GET_ID);) {
            ps.setString(1, cityName);

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
