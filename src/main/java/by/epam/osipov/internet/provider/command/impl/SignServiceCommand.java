package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.AccessDAO;
import by.epam.osipov.internet.provider.dao.impl.ContractDAO;
import by.epam.osipov.internet.provider.dao.impl.CoverageDAO;
import by.epam.osipov.internet.provider.dao.impl.ServiceDAO;
import by.epam.osipov.internet.provider.entity.impl.*;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;
import by.epam.osipov.internet.provider.service.ContractService;

/**
 * Created by Lenovo on 01.02.2017.
 */
public class SignServiceCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            return tryExecute(content);
        } catch (ConnectionPoolException | DAOException e) {
           throw new CommandException(e);
        }
    }

    private String tryExecute(RequestContent content) throws ConnectionPoolException, DAOException, CommandException {

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            CoverageDAO coverageDAO = new CoverageDAO(connection);
            int userId = ((User)content.getSessionAttribute("user")).getId();
            Coverage coverage = coverageDAO.getByUserId(userId);
            if(coverage==null){
                throw new CommandException("coverage not founded");
            }
            int coverageId=coverage.getId();
            ContractService contractService = new ContractService();
            int apt = contractService.defineAptByUserId(userId);
            ServiceDAO serviceDAO = new ServiceDAO(connection);
            String serviceName = content.getParameter("service_name");
            Service service = serviceDAO.getByName(serviceName);
            if(service==null){
                throw new CommandException("service not founded");
            }
            int serviceId = service.getId();
            String userPassport = ((User) content.getSessionAttribute("user")).getPassport();
            AccessDAO accessDAO = new AccessDAO(connection);
            Access access = accessDAO.getByUserPassport(userPassport);
            int accessId = access.getId();

            Contract contract = new Contract(userId,coverageId,apt,serviceId,accessId, null);

            ContractDAO contractDAO = new ContractDAO(connection);
            contractDAO.create(contract);

            return "/jsp/service-page.jsp";
        }
    }

}
