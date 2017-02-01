package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.*;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.Contract;
import by.epam.osipov.internet.provider.entity.impl.Coverage;
import by.epam.osipov.internet.provider.exception.*;
import by.epam.osipov.internet.provider.mail.ssl.EmailSender;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;
import by.epam.osipov.internet.provider.service.AccessService;
import by.epam.osipov.internet.provider.service.ContractService;
import by.epam.osipov.internet.provider.service.CoverageService;
import by.epam.osipov.internet.provider.service.UserService;

import javax.jws.soap.SOAPBinding;
import javax.mail.MessagingException;

/**
 * Created by Lenovo on 15.01.2017.
 */
public class RegisterCommand implements Command {

    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            return tryExecute(content);
        } catch (ServiceException | DAOException | ConnectionPoolException | MessagingException e) {
            throw new CommandException("Error while trying to execute Register user command", e);
        }
    }

    private String tryExecute(RequestContent content) throws ConnectionPoolException, DAOException, ServiceException, MessagingException {


        String name = content.getParameter("name");
        String surname = content.getParameter("surname");
        String patronymic = content.getParameter("patronymic");
        String passport = content.getParameter("passport");
        String phone = content.getParameter("phone");
        String email = content.getParameter("email");
        String balance = content.getParameter("balance");

        UserService userService = new UserService();
        int idUser = userService.addNewUser(name, surname, patronymic, passport,phone,email,balance);

        Access access = addNewAccess();
        int idAccess = access.getId();


        City city = new City(content.getParameter("city"));
        String street = content.getParameter("street");
        int houseNumber = Integer.parseInt(content.getParameter("house"));
        int building = Integer.parseInt(content.getParameter("building"));

        CoverageService coverageService = new CoverageService();

        int idCoverage = coverageService.defineCoverage(city, street,houseNumber,building);
        int apt = Integer.parseInt(content.getParameter("apt"));
        //
        int idService = Integer.parseInt(content.getParameter("idService"));

        java.sql.Timestamp sqlTime = new java.sql.Timestamp(new java.util.Date().getTime());

        ContractService contractService = new ContractService();
        contractService.addNewContract(idUser, idCoverage, apt, idService, idAccess, sqlTime);

        AccessService accessService = new AccessService();
        accessService.sendAccessToUser(access);

        return "/";
    }

    private Access addNewAccess() throws ConnectionPoolException, DAOException, ServiceException {
        AccessService accessService = new AccessService();
        Access access = accessService.generateUniqueAccess();

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            AccessDAO accessDAO = new AccessDAO(connection);
            accessDAO.create(access);
            access = accessDAO.findByLogin(access.getLogin());
        }
        return access;
    }


}
