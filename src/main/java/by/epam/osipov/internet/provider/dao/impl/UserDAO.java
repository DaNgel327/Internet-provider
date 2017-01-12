package by.epam.osipov.internet.provider.dao.impl;

import by.epam.osipov.internet.provider.dao.AbstractDAO;
import by.epam.osipov.internet.provider.entity.Entity;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.util.List;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class UserDAO{

    private final static String SELECT_PASSWORD_BY_LOGIN = "SELECT user.password FROM internet_provider.user" +
            " WHERE login = ?";
    private final static String INSERT_NEW_USER = "INSERT INTO internet_provider.account (login, password, email, id_role, firstname, secondname, lastname, address, city, datebirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String SELECT_USER_BY_LOGIN = "SELECT * FROM `internet_provider`.`account` WHERE login = ?";
    private final static String SELECT_ALL = "SELECT * FROM `internet_provider`.`account`";
    private final static String UPDATE_PASS = "UPDATE account SET password= ? WHERE id_account = ?";


}
