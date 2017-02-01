package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Operation;
import by.epam.osipov.internet.provider.entity.impl.Service;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 01.02.2017.
 */
public class OperationDAO extends AbstractDAO {

    private static final String SELECT_ALL = "SELECT * FROM operation";
    private static final String SELECT_LAST = "SELECT operation.idOperation, operation.type, operation.date, operation.sum, operation.idUser from operation\n" +
            "JOIN user ON operation.idUser = user.idUser\n" +
            " WHERE operation.date >= DATE_SUB(CURRENT_DATE, INTERVAL 2 MONTH) AND operation.idUser=?";

    public OperationDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public int getIdByKey(Object key) throws UnsupportedOperationException, DAOException {
        return 0;
    }

    @Override
    public void deleteByKey(Object key) throws UnsupportedOperationException, DAOException {

    }

    @Override
    public List findAll() throws UnsupportedOperationException, DAOException {
        List<Operation> operations = new ArrayList<>();

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {
                int id = rs.getInt(1);
                int type = rs.getInt(2);
                java.sql.Timestamp date = rs.getTimestamp(3);
                double sum = rs.getDouble(4);
                int userId = rs.getInt(5);

                operations.add(new Operation(id, type, date, sum, userId));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to find all operations", e);
        }
        return operations;
    }

    public List<Operation> getLastByUserId(int id) throws DAOException {

        List<Operation> operations = new ArrayList<Operation>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_LAST)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int operationId = rs.getInt(1);
                int type = rs.getInt(2);
                java.sql.Timestamp date = rs.getTimestamp(3);
                double sum = rs.getDouble(4);
                int userId = rs.getInt(5);

                operations.add(new Operation(operationId, type, date, sum, userId));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get last y id '" + id + "' city", e);
        }
        return operations;
    }
}
