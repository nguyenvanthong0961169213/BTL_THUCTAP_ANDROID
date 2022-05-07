package com.example.bt_android_thuctap.model;

import android.text.TextUtils;
import android.util.Patterns;

public class User {
    //Attributes
    private String Name;
    private String PhoneNumber;
    private String Password;
    private String Token;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    //Contructer
    public User(String phoneNumber, String password, String name,String token) {
        PhoneNumber = phoneNumber;
        Password = password;
        Name = name;
        Token = token;
    }
    public User(String phoneNumber, String name,String token) {
        PhoneNumber = phoneNumber;
        Name = name;
        Token = token;
    }

    //Getters and Setters
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
