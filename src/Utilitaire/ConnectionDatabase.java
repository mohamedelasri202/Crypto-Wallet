package Utilitaire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class ConnectionDatabase {
    private static ConnectionDatabase instance;
    private Connection connection;

    private ConnectionDatabase() {
        try {

            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);

            String url = props.getProperty("DB_URL");
            String user = props.getProperty("DB_USER");
            String password = props.getProperty("DB_PASSWORD");

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database successfully");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    public static ConnectionDatabase getInstance() {
        if (instance == null) {
            instance = new ConnectionDatabase();
        }
        return instance;
    }


    public Connection getConnection() {
        return connection;
    }
}
