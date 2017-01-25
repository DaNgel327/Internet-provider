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
import by.epam.osipov.internet.provider.service.UserService;

/**
 * Created by Lenovo on 15.01.2017.
 */
public class RegisterCommand implements Command {

    @Override
    public String execute(RequestContent content) throws CommandException {
        try {
            return tryExecute(content);
        } catch (RegistrationException | EntityNotFoundException | DAOException | ConnectionPoolException e) {
            throw new CommandException("Error while trying to execute Register user command", e);
        }
    }

    private String tryExecute(RequestContent content) throws RegistrationException, ConnectionPoolException, DAOException, EntityNotFoundException {

        int idUser = addNewUser(content);
        Access access = addNewAccess();
        int idAccess = access.getId();
        int idCoverage = defineCoverage(content);
        int apt = Integer.parseInt(content.getParameter("apt"));
        //сделать что то с этим
        int idService = 0;

        java.sql.Timestamp sqlTime = new java.sql.Timestamp(new java.util.Date().getTime());

        addNewContract(idUser, idCoverage, apt, idService, idAccess, sqlTime);

        sendAccessToUser(access);

        return "/";
    }

    private Access addNewAccess() throws RegistrationException, ConnectionPoolException, DAOException, EntityNotFoundException {
        AccessService accessService = new AccessService();
        Access access = accessService.generateUniqueAccess();

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            AccessDAO accessDAO = new AccessDAO(connection);
            accessDAO.create(access);
            access = accessDAO.findByLogin(access.getLogin());
        }
        return access;
    }

    private int addNewUser(RequestContent content) throws ConnectionPoolException, DAOException, RegistrationException {
        String name = content.getParameter("name");
        String surname = content.getParameter("surname");
        String patronymic = content.getParameter("patronymic");
        String passport = content.getParameter("passport");

        UserService userService = new UserService();
        if (userService.userExist(passport)) {
            throw new RegistrationException("Error while trying to register new user. User exist");
        }


        String phone = content.getParameter("phone");
        String email = content.getParameter("email");
        String balance = content.getParameter("balance");

        if (balance.equals("")) {
            balance = "0.0";
        }

        return userService.registerNew(surname, name, patronymic, passport, phone,
                Double.parseDouble(balance), email);
    }

    private void sendAccessToUser(Access access) throws DAOException, EntityNotFoundException, ConnectionPoolException, RegistrationException {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(connection);

            String email = userDAO.getEmailByAccess(access);

            EmailSender emailSender = new EmailSender();
            emailSender.sendAccess(access, email);
        }
    }

    private int defineCoverage(RequestContent content) throws DAOException, EntityNotFoundException, ConnectionPoolException {

        City city = new City(content.getParameter("city"));
        int idCity = 0;
        String street = content.getParameter("street");
        int houseNumber = Integer.parseInt(content.getParameter("house"));

        int idCoverage = -1;

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {

            CityDAO cityDAO = new CityDAO(connection);
            idCity = cityDAO.getIdByKey(city.getName());


            // CHAAANGE 1
            Coverage coverage = new Coverage(idCity, street, houseNumber, 1);

            CoverageDAO coverageDAO = new CoverageDAO(connection);

            idCoverage = coverageDAO.getIdByParameters(coverage, city);
        }

        return idCoverage;
    }

    private boolean addNewContract(int idUser, int idCoverage, int apt, int idService, int idAccess,
                                   java.sql.Timestamp serviceProvisionDate) throws DAOException, ConnectionPoolException {

        Contract contract = new Contract(idUser, idCoverage, apt, idService, idAccess, serviceProvisionDate);

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            ContractDAO contractDAO = new ContractDAO(connection);

            contractDAO.create(contract);

        }
        return true;
    }

}
