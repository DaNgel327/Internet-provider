package by.epam.osipov.internet.provider.generator;

import by.epam.osipov.internet.provider.content.RequestContent;
import by.epam.osipov.internet.provider.dao.impl.*;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.Contract;
import by.epam.osipov.internet.provider.entity.impl.Coverage;
import by.epam.osipov.internet.provider.exception.ConnectionPoolException;
import by.epam.osipov.internet.provider.mail.ssl.EmailSender;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;
import by.epam.osipov.internet.provider.service.AccessService;
import by.epam.osipov.internet.provider.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lenovo on 18.01.2017.
 */
public class UserGenerator{

    private static List<String> names;
    private static List<String> patronymic;
    private static List<String> surnames;

    static{
        names = new ArrayList<>();
        names.add("Ivanov");
        names.add("Petrov");
        names.add("Sidorov");
        names.add("Zinko");
        names.add("Chuzhba");
        names.add("Vorobey");
        names.add("Mirskiy");
        names.add("Evhuta");
        names.add("Cherednik");
        names.add("Baranovskaya");
        names.add("Chernyavskaya");
        names.add("Protsenko");
        names.add("Sergeev");
        names.add("Rabets");
        names.add("Kuznetsov");
        names.add("Dorohovich");
        names.add("Osipov");
        names.add("Sikorskiy");
        names.add("Luchina");
        names.add("Losev");
        names.add("Mihan");
        names.add("Manuylov");
        names.add("Bogdan");
    }

    static{
        patronymic = new ArrayList<>();
        patronymic.add("Iharavich/na");
        patronymic.add("Vladislavovich/na");
        patronymic.add("Konstantinovich/na");
        patronymic.add("Egorovich/na");
        patronymic.add("Andreevich/na");
        patronymic.add("Vadimovich/na");
        patronymic.add("Olegovich/na");
        patronymic.add("Alexeevich/na");
        patronymic.add("Rodionovich/na");
    }

    static{
        surnames = new ArrayList<>();
        surnames.add("Ihar");
        surnames.add("Vlad");
        surnames.add("Kostya");
        surnames.add("Egor");
        surnames.add("Andrey");
        surnames.add("Tolik");
        surnames.add("Vadim");
        surnames.add("Oleg");
        surnames.add("Alexey");
        surnames.add("Rodion");
        surnames.add("Zhenya");
        surnames.add("Vitya");
        surnames.add("Olga");
        surnames.add("Marina");
        surnames.add("Ludmila");
        surnames.add("Marina");
        surnames.add("Nastya");
        surnames.add("Tanya");
        surnames.add("Rita");
        surnames.add("Veronica");
        surnames.add("Vika");
        surnames.add("Dasha");
        surnames.add("Masha");
    }

    public static void main(String[] args) {
        for(int i=0;i<200;i++)
        new UserGenerator().generate();
    }


public void generate(){

    String name = names.get(new Random().nextInt(names.size()-1));
    String surname = surnames.get(new Random().nextInt(surnames.size()-1));
    String patronym = patronymic.get(new Random().nextInt(patronymic.size()-1));
    String passport = "MA";
    for(int i = 0;i<7;i++){
        passport+=new Random().nextInt(9);
    }
    int idUser = addNewUser(name, surname, patronym, passport);

    Access access = addNewAccess();

    int idAccess = access.getId();

    int idCoverage = defineCoverage("Minsk", "Kulman", 11, 1);

    int apt = 1;
    //сделать что то с этим
    int idService = 0;

    java.sql.Timestamp sqlTime = new java.sql.Timestamp(new java.util.Date().getTime());

    addNewContract(idUser, idCoverage, apt, idService, idAccess, sqlTime);

    sendAccessToUser(access);

}

    private Access addNewAccess() {
        AccessService accessService = new AccessService();
        Access access = accessService.generateUniqueAccess();
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            AccessDAO accessDAO = new AccessDAO(connection);
            accessDAO.create(access);
            access = accessDAO.findByLogin(access.getLogin());
        } catch (Exception e) {
            System.out.println("ex");
        }
        return access;
    }

    private int addNewUser(String name, String surname, String patronymic, String passport) {


        UserService userService = new UserService();
        if (userService.userExist(passport)) {
            return -1;
            //  return "registration page";
        }

        String phone = "+375-29-393-68-95";
        String email = "osipov.06@mail.ru";
        String balance = "0.0";
        if (balance.equals("")) {
            balance = "0.0";
        }

        int idUser = userService.registerNew(surname, name, patronymic, passport, phone,
                Double.parseDouble(balance), email);
        if (idUser < 1) {
            System.out.println("ошибка регистрации пользователя. хз какая. чет с user");
            return -1;
        }
        return idUser;
    }

    private void sendAccessToUser(Access access) {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(connection);

            String email = userDAO.getEmailByAccess(access);

            EmailSender emailSender = new EmailSender();
            emailSender.sendAccess(access, email);

        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }

    }

    private int defineCoverage(String City, String street, int houseNumber, int apt) {

        City city = new City(City);
        int idCity = 0;

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {

            CityDAO cityDAO = new CityDAO(connection);
            idCity = cityDAO.getIdByKey(city.getName());

        } catch (Exception e) {
            System.out.println("ex");
            return -1;
        }

        Coverage coverage = new Coverage(idCity, street, houseNumber);

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
        }
        return true;
    }

}
