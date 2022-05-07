package com.example.bt_android_thuctap.model;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {
    //Attributes
    private String Name;
    private String PhoneNumber;
    private String Password;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    //Contructer
    public Login(String phoneNumber, String password,String name) {
        PhoneNumber = phoneNumber;
        Password = password;
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
