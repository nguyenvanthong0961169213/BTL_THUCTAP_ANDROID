package com.example.bt_android_thuctap.model;

import android.text.TextUtils;
import android.util.Patterns;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable {
    //Attributes
    private String Name;
    private String PhoneNumber;
    private String Password;
    private String Id;
    private String Token;

    public User(){}


    public User(String name, String phoneNumber, String password, String id, String token) {
        Name = name;
        PhoneNumber = phoneNumber;
        Password = password;
        Id = id;
        Token = token;
    }

    public User(String name, String phoneNumber) {
        Name = name;
        PhoneNumber = phoneNumber;
    }

    //Contructer
    public User(String phoneNumber, String password, String name) {
        PhoneNumber = phoneNumber;
        Password = password;
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public void setPassword(String password) { Password = password; }



}
