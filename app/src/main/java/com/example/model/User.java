package com.example.model;

public class User {

    private String Name, Email, Phone;

    public User(String name, String email, String phone) {
        Name = name;
        Email = email;
        Phone = phone;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }


}
