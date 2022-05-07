package com.example.bt_android_thuctap.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class FriendViewModel extends BaseObservable {

    private String Name;

    @Bindable
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
        notifyPropertyChanged(BR.name);
    }
}
