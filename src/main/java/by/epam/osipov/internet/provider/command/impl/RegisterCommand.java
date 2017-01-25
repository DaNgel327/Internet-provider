package by.epam.osipov.internet.provider.command.impl;

import by.epam.osipov.internet.provider.command.Command;
import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.*;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.Contract;
import by.epam.osipov.internet.provider.entity.impl.Coverage;
import by.epam.osipov.internet.provider.exception.CommandException;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.exception.DAOException;
import by.epam.osipov.internet.provider.exception.EntityNotFoundException;
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

    private Access addNewAccess() {
        AccessService accessService = new AccessService();
        Access access = accessService.generateUniqueAccess();
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            AccessDAO accessDAO = new AccessDAO(connection);
            accessDAO.create(access);
            access = accessDAO.findByLogin(access.getLogin());
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return access;
    }

    private int addNewUser(RequestContent content) {
        String name = content.getParameter("name");
        String surname = content.getParameter("surname");
        String patronymic = content.getParameter("patronymic");
        String passport = content.getParameter("passport");

        UserService userService = new UserService();
        if (userService.userExist(passport)) {
            return -1;
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

    private void sendAccessToUser(Access access) {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(connection);

            String email = userDAO.getEmailByAccess(access);

            EmailSender emailSender = new EmailSender();
            emailSender.sendAccess(access, email);

        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

    }

    private int defineCoverage(RequestContent content) {

        City city = new City(content.getParameter("city"));
        int idCity = 0;
        String street = content.getParameter("street");
        int houseNumber = Integer.parseInt(content.getParameter("house"));

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {

            CityDAO cityDAO = new CityDAO(connection);
            idCity = cityDAO.getIdByKey(city.getName());

        } catch (Exception e) {
            System.out.println("ex");
            return -1;
        }

        // CHAAANGE 1
        Coverage coverage = new Coverage(idCity, street, houseNumber, 1);

        int idCoverage = -1;
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            CoverageDAO coverageDAO = new CoverageDAO(connection);

            idCoverage = coverageDAO.getIdByParameters(coverage, city);
        } catch (Exception e) {
            System.out.println("ex");
            return -1;
        }

        return idCoverage;
    }

    private boolean addNewContract(int idUser, int idCoverage, int apt, int idService,
                                   int idAccess, java.sql.Timestamp serviceProvisionDate) {

        Contract contract = new Contract(idUser, idCoverage, apt, idService, idAccess, serviceProvisionDate);

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            ContractDAO contractDAO = new ContractDAO(connection);

            contractDAO.create(contract);

        } catch (ConnectionPoolException e) {
            e.printStackTrace();
            return false;
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
