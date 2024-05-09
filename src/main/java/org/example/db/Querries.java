package org.example.db;

public class Querries {
    public static final String FIND_BY_ID = "SELECT * FROM student WHERE student_id = ?";
    public static final  String INSERT_STUDENT = "INSERT INTO student (first_name, last_name, age, email) VALUES (?, ? ,? ,?)";
}
