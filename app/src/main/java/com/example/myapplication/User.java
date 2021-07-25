package com.example.myapplication;

public class User {
    String email,password,token,phone_no,name;

    public User() {
    }

    public User(String email, String password, String token, String number, String name) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.phone_no = number;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
