package org.example;

import org.example.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCExamples {
    public static void main(String[] args) {
    }

    public static void ex1() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://3306/school_db","root","1234");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from student");

        List<Student> studentList = new ArrayList<>();

        while (resultSet.next()){
            int studentID = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            int age = resultSet.getInt(4);
            String email = resultSet.getString(5);
            LocalDate localDate = resultSet.getDate(6).toLocalDate();

            Student student = new Student(studentID, firstName, lastName, age, email, localDate);
            studentList.add(student);
        }
        studentList.forEach(System.out::println);
    }
}
