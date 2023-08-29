package shared;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import org.apache.logging.log4j.LogManager;

import java.sql.Driver;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class WebappListener implements ServletContextListener
{
        public void contextInitialized(ServletContextEvent sce) {}
        public void contextDestroyed(ServletContextEvent sce) {
                try {
                        Enumeration<Driver> enumer = DriverManager.getDrivers();
                        while (enumer.hasMoreElements()) {
                                DriverManager.deregisterDriver(enumer.nextElement());
                        }
                } catch (SQLException se) {
                        se.printStackTrace();
                }
                LogManager.getLogger(this.getClass()).info("Closing Database Session");
                shared.HibernateHelper.closeFactory();
        }
        
}
