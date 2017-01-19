package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.ServiceDAO;
import by.epam.osipov.internet.provider.entity.impl.Service;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

/**
 * Created by Lenovo on 19.01.2017.
 */
public class AddServiceCommand implements Command {
    @Override
    public String execute(RequestContent content) throws CommandException {

        String name = content.getParameter("name");
        String description = content.getParameter("description");
        String validity = content.getParameter("validity").replaceAll("\\+", " ");
        double cost = Double.parseDouble(content.getParameter("cost"));

        Service service = new Service(name, description, validity, cost);

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            ServiceDAO serviceDAO = new ServiceDAO(connection);
            serviceDAO.create(service);
        } catch (Exception e) {
            System.out.println("ex");
        }
        return new ShowServiceCommand().execute(content);
    }
}
