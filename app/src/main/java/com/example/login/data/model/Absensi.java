package com.example.login.data.model;

import java.util.Date;

public class Absensi {
    int npm;
    String name;
    String class_year;
    String create_date;

    public Absensi(int npm, String name, String class_year, String create_date) {
        this.npm = npm;
        this.name = name;
        this.class_year = class_year;
        this.create_date = create_date;
    }

    public Absensi(){

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

    public String getClass_year() {
        return class_year;
    }

    public void setClass_year(String class_year) {
        this.class_year = class_year;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

}
