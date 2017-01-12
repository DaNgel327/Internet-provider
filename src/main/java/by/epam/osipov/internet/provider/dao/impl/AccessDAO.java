package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.Entity;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class AccessDAO extends AbstractDAO{

    private final static String SELECT_ALL = "SELECT * FROM access";
    private final static String SELECT_BY_ID = "SELECT * FROM access WHERE id = ?";
    private final static String INSERT_NEW = "INSERT INTO access (idAccess, login, password, role) VALUES (?,?,?,?)";
    private final static String UPDATE_PASS = "UPDATE access SET password = ? WHERE login = ?";
    private final static String DELETE_BY_ID = "DELETE FROM ACCESS WHERE login = ?";

    private final static String SELECT_BY_LOGIN = "SELECT * FROM access WHERE login = ?";

    public AccessDAO(ConnectionProxy connection) {
        super(connection);
    }


    @Override
    public List<Access> findAll() {
        List<Access> accesses = new ArrayList<>();
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()){
                accesses.add(new Access(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getByte(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(st != null){
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                connection.close();
            } //catch (PoolException e) {
              //  e.printStackTrace();
            //}
            catch(Exception e){
                System.out.println("error");
            }
        }
        return accesses;
    }

    public Access findByLogin(String login){
        Access access = null;

        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_LOGIN)){
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                access = new Access(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getByte(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            }catch (Exception e){
                System.out.println("sss");
            }
        }
        return access;
    }

}
