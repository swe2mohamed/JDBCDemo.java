package org.example;

import java.sql.*;
import java.time.LocalDate;

public class JDBCDemo {
    public static void main(String[] args) {
        ex1();
    }
    public static void ex1(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_db", "root","password");
            Statement statement = connection.createStatement();
            // executeQuery method is used for executing SELECT queries; (READ)
            ResultSet resultSet =  statement.executeQuery("select * from student");
            // executeUpdate method is used for INSERT , DELETE or UPDATE queries;
            // statement.executeUpdate();
            while (resultSet.next()){
                int studentId =  resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                LocalDate localDate = resultSet.getDate("create_date").toLocalDate();
                System.out.println(studentId + "," + firstName + "," +lastName + "," +  age + ","+ email + ","+localDate);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: ");
            e.printStackTrace();
        }
    }
}
