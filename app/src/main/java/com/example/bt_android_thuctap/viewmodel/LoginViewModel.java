package com.example.bt_android_thuctap.viewmodel;



import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


import com.example.bt_android_thuctap.BR;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class LoginViewModel extends BaseObservable {
    String userID,phone,password,name;
    private String PhoneNumber;
    private String Password;
    ArrayList arrayList = new ArrayList();

    public ObservableField<String> validate=new ObservableField<>();
    public ObservableField<String> RePassword=new ObservableField<>();

    @Bindable
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }

    @Bindable
    public String getPassword() {
        return Password;

    }
    public void setPassword(String password) {
        Password = password;
        notifyPropertyChanged(BR.password);
    }





}
