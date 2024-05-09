package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private final static String JDBC_URL = "jdbc:mysql://localhost:3306/school_db";
    private final static String JDBC_USER = "root";
    private final static String JDBC_PWD = "password";

    public static Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER,JDBC_PWD);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
}
