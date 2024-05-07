package org.example.model;

import java.time.LocalDate;

public class Student {
    private int studentId;
    private String firstNae;
    private String lastName;
    private int age;
    private String email;
    private LocalDate createDate;

    // Is used to create student in the student table
    public Student(String firstNae, String lastName, int age, String email) {
        this.firstNae = firstNae;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    // Is used when we want to fetch data from the student table
    public Student(int studentId, String firstNae, String lastName, int age, String email, LocalDate createDate) {
        this.studentId = studentId;
        this.firstNae = firstNae;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.createDate = createDate;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getFirstNae() {
        return firstNae;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstNae='" + firstNae + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
