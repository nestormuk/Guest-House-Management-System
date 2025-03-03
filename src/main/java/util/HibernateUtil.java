package util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import model.*;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSession() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();

                // PostgreSQL Database Configuration
                properties.put(Environment.DRIVER, "org.postgresql.Driver");
                properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/GuestHouseManagement");
                properties.put(Environment.USER, "Nestor");
                properties.put(Environment.PASS, "N");

                // Hibernate Settings
                properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.FORMAT_SQL, "true");
                properties.put(Environment.HBM2DDL_AUTO, "update"); // Automatically updates schema

                configuration.setProperties(properties);

                // Register Entity Classes
                configuration.addAnnotatedClass(Room.class);
                configuration.addAnnotatedClass(Booking.class);
                configuration.addAnnotatedClass(Guest.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
