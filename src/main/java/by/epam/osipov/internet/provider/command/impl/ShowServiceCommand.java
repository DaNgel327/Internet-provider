package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.ServiceDAO;
import by.epam.osipov.internet.provider.entity.impl.Service;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.util.List;

/**
 * Created by Lenovo on 18.01.2017.
 */
public class ShowServiceCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            return tryExecute(content);
        } catch (DAOException | ConnectionPoolException e) {
            throw new CommandException("Error while trying to execute Show Users command", e);
        }
    }

    private String tryExecute(RequestContent content) throws CommandException, DAOException, ConnectionPoolException {

        List<Service> services = null;
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            ServiceDAO serviceDAO = new ServiceDAO(connection);
            services = serviceDAO.findAll();
        }

        content.setSessionAttribute("services", services);
        return "/jsp/service-page.jsp";
    }

}
