package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.BanDAO;
import by.epam.osipov.internet.provider.dao.impl.ServiceDAO;
import by.epam.osipov.internet.provider.dao.impl.UserDAO;
import by.epam.osipov.internet.provider.entity.impl.Ban;
import by.epam.osipov.internet.provider.entity.impl.Service;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;
import by.epam.osipov.internet.provider.service.UserService;

import java.util.List;

/**
 * Created by Lenovo on 18.01.2017.
 */
public class ShowServiceCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {

        List<Service> services = null;
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            ServiceDAO serviceDAO = new ServiceDAO(connection);
            services = serviceDAO.findAll();
        } catch (Exception e) {
            System.out.println("ex");
        }

        content.setSessionAttribute("services", services);
        return "/jsp/service-page.jsp";
    }
}
