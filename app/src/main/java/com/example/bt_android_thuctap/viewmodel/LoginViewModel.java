package com.example.bt_android_thuctap.viewmodel;



import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


import com.example.bt_android_thuctap.BR;

import com.example.bt_android_thuctap.Layout_Home;
import com.example.bt_android_thuctap.MainActivity;
import com.example.bt_android_thuctap.model.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginViewModel extends BaseObservable {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
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
