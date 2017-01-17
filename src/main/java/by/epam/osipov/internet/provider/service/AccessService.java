package by.epam.osipov.internet.provider.service;

import by.epam.osipov.internet.provider.dao.impl.AccessDAO;
import by.epam.osipov.internet.provider.dao.impl.UserDAO;
import by.epam.osipov.internet.provider.entity.impl.Access;
import by.epam.osipov.internet.provider.entity.impl.User;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lenovo on 16.01.2017.
 */
public class AccessService {

    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static final int LOGIN_SIZE = 8;
    private static final int PASSWORD_SIZE = 10;
    private static final byte USER_ROLE = 1;

    public Access generateUniqueAccess(){
        Access access = null;
        boolean exist = true;

        //while access exist in DB, try to generate new
        while(exist){
            access = generateAccess();
            exist = loginExist(access.getLogin());
        }

        return access;
    }

    private  Access generateAccess() {
        StringBuilder login = generateSequence(LOGIN_SIZE);
        StringBuilder password = generateSequence(PASSWORD_SIZE);


        return new Access(login.toString(), password.toString(), USER_ROLE);
    }

    private StringBuilder generateSequence(int length){
        StringBuilder sequence = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length - 1; i++) {
            boolean letter = random.nextBoolean();
            if (letter) {
                boolean lowerCase = random.nextBoolean();
                sequence.append(getLetter(random.nextInt(alphabet.length()), lowerCase));
            }else{
                sequence.append(random.nextInt(10));
            }
        }

        return sequence;
    }

    private char getLetter(int index, boolean lowerCase) {
        return lowerCase ? alphabet.charAt(index) : alphabet.toUpperCase().charAt(index);
    }

    public boolean loginExist(String login) {
        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            AccessDAO accessDAO = new AccessDAO(connection);
            List<Access> accesses = new ArrayList<>();

            accesses = accessDAO.findAll();

            for (Access access : accesses) {
                if (access.getLogin().equals(login)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("ex");
        }

        return false;
    }

}
