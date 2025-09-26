package Utilitaire;

import javax.xml.crypto.Data;
import java.sql.*;
import java.sql.Connection;


public class ConnectionDatabase{
     private static ConnectionDatabase instance;
     private Connection connection;

     private final String url ="jdbc:postgresql://localhost:5432/cryptowallet";
     private final String user = "postgres";
     private final String password = "mohamed@1230";

     private ConnectionDatabase(){
        try {
            connection =DriverManager.getConnection(url,user,password);
            System.out.println("Connected to database successfully");

        }catch (SQLException e){
            e.printStackTrace();

        }
     }

     public static ConnectionDatabase getInstance(){
         if(instance == null){
             instance = new ConnectionDatabase();
         }
         return instance;
     }
     public Connection getConnection(){
         return connection;
     }






}