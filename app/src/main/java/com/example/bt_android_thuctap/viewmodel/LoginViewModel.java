package com.example.bt_android_thuctap.viewmodel;



import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


import com.example.bt_android_thuctap.BR;

import com.example.bt_android_thuctap.common.Common;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginViewModel extends BaseObservable {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID,phone,password,name;
    private String PhoneNumber;
    private String Password;




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

    public void Onclick()
    {

       firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Log.w("FCM","error");
                    return;
                }
                String token = task.getResult();
                Log.d("FCM",token);
            }
        });


            firebaseFirestore.collection("User").whereEqualTo("phone", PhoneNumber)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            /*   validate.set("Dang nhap thanh cong");*/
                            Log.d("String", "" + doc.getData());
                            if (Objects.equals(Password, doc.get("password").toString())) {
                                validate.set("Dang nhap thanh cong");
                                User user = new User(phone,doc.get("name").toString(),password);
                                Common.user = user;

                            }



                        }
                    }


                }
            });

        }

    public void OnClickSignUp(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        HashMap<String, String> data = new HashMap<>();
        data.put(Constants.key_Name,"");
        data.put(Constants.key_Password,Password.toString());
        data.put(Constants.key_Phone,PhoneNumber.toString());
        firebaseFirestore.collection("User").add(data).addOnSuccessListener(documentReference -> {
                validate.set("Add thanh cong");
        }).addOnFailureListener(e -> {
            validate.set("That bai");
        });



        /*Login user = new Login(getPhoneNumber(), getPassword());*/
      /*  if (user.isValidPhone() && user.isValidPassWord() && getPassword().equals(RePassword.get().toString()) ) {
            validate.set("thanh cong");
        } else {
            validate.set("that bai");
        }*/
    }
    public boolean isValidPhone(String str){
        Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher match = ptrn.matcher(str);
        return (match.find() && match.group().equals(str));
    }

    public boolean isValidPassWord(String str){
        if(str.length()>=6){
            return true;

        }
        return false;
    }


}
