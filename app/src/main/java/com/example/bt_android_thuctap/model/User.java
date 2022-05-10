package com.example.bt_android_thuctap.model;

import android.text.TextUtils;
import android.util.Patterns;


import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable{
    //Attributes
    private String Name;
    private String PhoneNumber;
    private String Password;
    private String Token;

    public User(String phoneNumber, String password, String name) {
        PhoneNumber = phoneNumber;
        Password = password;
        Name = name;

    }

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

    public User(String name, String phoneNumber) {
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public User(String phoneNumber, String password, String name,String token) {
        PhoneNumber = phoneNumber;
        Password = password;
        Name = name;
        Token = token;
    }


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
