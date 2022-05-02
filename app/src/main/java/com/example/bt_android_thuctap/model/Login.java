package com.example.bt_android_thuctap.model;

import android.text.TextUtils;
import android.util.Patterns;

public class Login {
    //Attributes
    private String PhoneNumber;
    private String Password;
    //Contructer
    public Login(String phoneNumber, String password) {
        PhoneNumber = phoneNumber;
        Password = password;
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
    public boolean isValidPhone(){
        return !TextUtils.isEmpty(PhoneNumber) && Patterns.PHONE.matcher(PhoneNumber).matches();
    }
    public boolean isValidPassWord(){
        return !TextUtils.isEmpty(Password) && Password.length() >= 6;
    }
}
