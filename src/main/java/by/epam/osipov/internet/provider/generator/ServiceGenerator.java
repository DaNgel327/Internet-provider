package by.epam.osipov.internet.provider.generator;

import by.epam.osipov.internet.provider.dao.impl.BanDAO;
import by.epam.osipov.internet.provider.dao.impl.ServiceDAO;
import by.epam.osipov.internet.provider.dao.impl.UserDAO;
import by.epam.osipov.internet.provider.entity.impl.Service;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lenovo on 19.01.2017.
 */
public class ServiceGenerator {

    private static List<String> validity = new ArrayList<>();


    static{
        validity.add("day");
        validity.add("week");
        validity.add("month");
        validity.add("2 weeks");
        validity.add("3 maonth");
        validity.add("6 month");
        validity.add("year");
        validity.add("forever");
    }


    public static void main(String[] args) {

        for(int i = 0; i < 100; i++){
            generate();
        }
    }

    public static void generate(){

        String name="NAME_";
        for(int i=0;i<5;i++){
            name+=new Random().nextInt(9);
        }
        String Validity = validity.get(new Random().nextInt(validity.size()-1));
        String description = "description";
        double cost = new Random().nextDouble();

        Service service = new Service(name, description, Validity, cost);

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection();) {
            ServiceDAO serviceDAO = new ServiceDAO(connection);
            serviceDAO.create(service);
        } catch (Exception e) {
            System.out.println("ex");
        }
    }

}
