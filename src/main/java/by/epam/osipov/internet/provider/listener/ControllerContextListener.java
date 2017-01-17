package by.epam.osipov.internet.provider.listener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Created by Lenovo on 17.01.2017.
 */
public class ControllerContextListener implements ServletContextListener {


    private static final Logger LOGGER = Logger.getLogger(ControllerContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            System.out.println(String.format("deregistering jdbc driver: %s", driver));
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                System.out.println(String.format("Error deregistering driver %s", driver));
            }
        }

        // MySQL driver leaves around a thread. This static method cleans it up.
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            System.out.println("Error deregistering driver");
        }

        
    }
}
