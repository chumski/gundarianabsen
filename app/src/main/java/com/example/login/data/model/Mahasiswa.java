package com.example.login.data.model;

public class Mahasiswa {
    int npm;
    String name;
    String faculty;
    String classYear;

    public Mahasiswa(int npm, String name, String faculty, String classYear) {
        this.npm = npm;
        this.name = name;
        this.faculty = faculty;
        this.classYear = classYear;
    }

    public int getNpm() {
        return npm;
    }

    public void setNpm(int npm) {
        this.npm = npm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getClassYear() {
        return classYear;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }
}
