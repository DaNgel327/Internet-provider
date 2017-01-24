package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.impl.Contract;
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
public class ContractDAO extends AbstractDAO {

    private static final String SELECT_ALL = "SELECT * FROM contract";

    private static final String SELECT_BY_ID = "SELECT * FROM contract WHERE user.id = ?";

    private static final String INSERT_NEW = "INSERT INTO contract (idUser, idCoverage, apartmentNumber, idService, " +
            "idAccess, serviceProvisionDate) " + "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String DELETE_BY_ID_USER = "DELETE FROM contract\n" + "WHERE idUser = ?";


    public ContractDAO(ConnectionProxy connection) {
        super(connection);
    }

    @Override
    public int getIdByKey(Object key) {
        throw new UnsupportedOperationException("ContractDAO doesn't support 'getIdByKey'");
    }

    /**
     * Deletes contract by user's id
     *
     * @param key user's id.
     */
    @Override
    public void deleteByKey(Object key) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID_USER)) {
            ps.setInt(1, (Integer) key);
            ps.executeUpdate();

            if (ps.getUpdateCount() == -1) {
                throw new DAOException("Contract of user '" + key + "doesn't deleted");
            }

        } catch (SQLException e) {
            throw new DAOException("Erorr while trying delete contract of user '" + key + "'", e);
        }
    }


    /**
     * Returns list of all contracts in database
     *
     * @return list of contracts
     */
    @Override
    public List<Contract> findAll() throws DAOException {
        List<Contract> contracts = new ArrayList<>();

        try (Statement st = connection.createStatement();) {
            ResultSet rs = st.executeQuery(SELECT_ALL);
            while (rs.next()) {

                int idContract = rs.getInt(1);
                int idUser = rs.getInt(2);
                int idCoverage = rs.getInt(3);
                int apartmentNumber = rs.getInt(4);
                int idService = rs.getInt(5);
                int idAccess = rs.getInt(6);
                java.sql.Timestamp serviceProvisionDate = rs.getTimestamp(7);

                contracts.add(new Contract(idContract, idUser, idCoverage, apartmentNumber, idService, idAccess,
                        serviceProvisionDate));

            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying find all contracts", e);
        }
        return contracts;
    }

    /*
    public Contract findById(int id) {

        Contract contract = null;

        try (Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(SELECT_BY_ID);
            int idContract = rs.getInt(1);
            int idUser = rs.getInt(2);
            int idCoverage = rs.getInt(3);
            int apartmentNumber = rs.getInt(4);
            int idService = rs.getInt(5);
            int idAccess = rs.getInt(6);
            java.sql.Timestamp serviceProvisionDate = rs.getTimestamp(7);

            contract = new Contract(idContract, idUser, idCoverage, apartmentNumber, idService, idAccess,
                    serviceProvisionDate);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contract;
    }
    */

    /**
     * Inserts new contract to database
     *
     * @param contract contract to insert
     */
    public void create(Contract contract) throws DAOException {

        try (PreparedStatement ps = this.connection.prepareCall(INSERT_NEW)) {

            ps.setInt(1, contract.getIdUser());
            ps.setInt(2, contract.getIdCoverage());
            ps.setInt(3, contract.getApartmentNumber());
            ps.setInt(4, contract.getIdService());
            ps.setInt(5, contract.getIdAccess());

            ps.setTimestamp(6, contract.getServiceProvisionDate());

            ps.executeUpdate();
            if (ps.getUpdateCount() != 1) {
                throw new DAOException("Contract '" + contract + "' wasn't added to database");
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying insert contract '" + contract + "'", e);
        }
    }

}
