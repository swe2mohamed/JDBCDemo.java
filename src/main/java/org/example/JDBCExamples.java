package org.example;

import com.mysql.cj.MysqlConnection;
import org.example.db.MySQLConnection;
import org.example.db.Querries;
import org.example.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCExamples {
    public static void main(String[] args) throws SQLException {
        ex4();
    }

    // Get all students data (FindAll)
    public static void ex1() {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/school_db","root","password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from student");
            List<Student> studentList = new ArrayList<>();

        while (resultSet.next()){
            int studentID = resultSet.getInt(1); // resultSet.getInt("student_id");
            String firstName = resultSet.getString(2); // resultSet.getString("first_name");
            String lastName = resultSet.getString(3); // resultSet.getString("last_name");
            int age = resultSet.getInt(4); // resultSet.getInt("age");
            String email = resultSet.getString(5); // resultSet.getString("email");
            LocalDate localDate = resultSet.getDate(6).toLocalDate(); // resultSet.getDate("create_date").toLocalDate();

            Student student = new Student(studentID, firstName, lastName, age, email, localDate);
            studentList.add(student);
        }
        studentList.forEach(System.out::println);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Get student by their id (findById)
    public static void ex2(){
        try(
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(Querries.FIND_BY_ID);
                ) {
            int inputId = 1;
            preparedStatement.setInt(1, inputId);
            try (ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                Student student = null;
                if (resultSet.next()) {
                    int studentID = resultSet.getInt(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    int age = resultSet.getInt(4);
                    String email = resultSet.getString(5);
                    LocalDate localDate = resultSet.getDate(6).toLocalDate();
                    student = new Student(studentID, firstName, lastName, age, email, localDate);
                }
                System.out.println(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    // Create a student (create)
    public static void ex3(){
        Student student = new Student("XXX", "ZZZ", 35, "xxx.zzz@example.com");
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(Querries.INSERT_STUDENT,PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            preparedStatement.setString(1,student.getfirstName());
            preparedStatement.setString(2,student.getLastName());
            preparedStatement.setInt(3,student.getAge());
            preparedStatement.setString(4,student.getEmail());

            // Execute insert query

            int rowsInserted = preparedStatement.executeUpdate();

            // Check if created successfully
            if (rowsInserted > 0){
                System.out.println("Student created successfully");
            }

            try (
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    ){
                if (generatedKeys.next()){
                    int generatedStudentId = generatedKeys.getInt(1);
                    System.out.println("Generated Student Id : " + generatedStudentId);
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void ex4(){
        Student student = new Student("Ali", "Sami", 22, "ali.sami@example.com");

        try(
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(Querries.INSERT_STUDENT,PreparedStatement.RETURN_GENERATED_KEYS);
                ){

            connection.setAutoCommit(false);
            preparedStatement.setString(1,student.getfirstName());
            preparedStatement.setString(2,student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getEmail());

            int rawInserted = preparedStatement.executeUpdate();
            if (rawInserted > 0){
                System.out.println("Student created successfully");
            } else{
                connection.rollback();
                throw new IllegalArgumentException("Insert operation student table failed");
            }

            try (
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys()
                    ){
                if (generatedKeys.next()){
                    int generatedStudentId = generatedKeys.getInt(1);
                    System.out.println("Generated key is: " + generatedStudentId);

                    int courseId = 1;
                    try(
                            PreparedStatement preparedStatementForStudentCourses = connection.prepareStatement(Querries.INSERT_STUDENTS_COURSES);
                    ){
                        preparedStatementForStudentCourses.setInt(1,generatedStudentId);
                        preparedStatementForStudentCourses.setInt(2,courseId);

                        int rowsInserted = preparedStatementForStudentCourses.executeUpdate();
                        if (rowsInserted > 0){
                            System.out.println("Assigning course to student is done successfully.");
                        }else {
                            connection.rollback();
                            throw new IllegalArgumentException("Insert operation is students_courses table failed.");
                        }
                    }
                }
            }
            connection.setAutoCommit(true);
        }catch (SQLException e){
            e.printStackTrace();

        }
    }



}

