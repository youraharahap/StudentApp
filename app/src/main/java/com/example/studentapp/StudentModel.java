package com.example.studentapp;

public class StudentModel {
    public static final String[] MAJORS = {"Informatics", "Information Systems"};

    String studentId, name, major;

    public StudentModel(String studentId, String name, String major) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}