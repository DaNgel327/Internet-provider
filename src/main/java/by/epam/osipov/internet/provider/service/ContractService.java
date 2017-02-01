package by.epam.osipov.internet.provider.service;

import by.epam.osipov.internet.provider.dao.impl.ContractDAO;
import by.epam.osipov.internet.provider.entity.impl.Contract;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.util.List;

/**
 * Created by Lenovo on 01.02.2017.
 */
public class ContractService {

    public void addNewContract(int idUser, int idCoverage, int apt, int idService, int idAccess,
                                   java.sql.Timestamp serviceProvisionDate) throws DAOException, ConnectionPoolException {

        Contract contract = new Contract(idUser, idCoverage, apt, idService, idAccess, serviceProvisionDate);

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            ContractDAO contractDAO = new ContractDAO(connection);

            contractDAO.create(contract);

        }
    }

    public int defineAptByUserId(int userId) throws ConnectionPoolException, DAOException {
        try(ConnectionProxy connection = ConnectionPool.getInstance().getConnection()){
            ContractDAO contractDAO = new ContractDAO(connection);
            List<Contract> contracts = contractDAO.getByUserId(userId);

            return contracts.get(0).getApartmentNumber();
        }
    }
}
