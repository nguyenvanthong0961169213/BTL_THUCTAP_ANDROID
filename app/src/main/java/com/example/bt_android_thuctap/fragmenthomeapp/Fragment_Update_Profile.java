package com.example.bt_android_thuctap.fragmenthomeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.model.User;

public class Fragment_Update_Profile extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_update_profile,container,false);
    }
    public void setF(User user){
        Log.e("dasda" , "setF: "+user.getName() );
    }
}
