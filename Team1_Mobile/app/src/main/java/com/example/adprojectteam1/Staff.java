package com.example.adprojectteam1;

import java.io.Serializable;

public class Staff implements Serializable {
    private int staffId;
    private String name;
    private String email;
    private String password;
    private int age;
    private String gender;
    private String position;

    public Staff() {
    }

    public Staff(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Staff(int staffId, String name, String position) {
        this.staffId = staffId;
        this.name = name;
        this.position = position;
    }

    public Staff(int staffId, String name, String email, String password, int age, String gender, String position) {
        this.staffId = staffId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.position = position;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


}