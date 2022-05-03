package com.example.bt_android_thuctap.viewmodel;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


import com.example.bt_android_thuctap.BR;
import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.model.Login;


public class LoginViewModel extends BaseObservable {

    private String PhoneNumber;
    private String Password;
    public ObservableField<String> validate=new ObservableField<>();


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
        Login user = new Login(getPhoneNumber(), getPassword());
        if (user.isValidPhone() && user.isValidPassWord()) {
            validate.set("thanh cong");
        } else {
            validate.set("that bai");
        }
    }


}
