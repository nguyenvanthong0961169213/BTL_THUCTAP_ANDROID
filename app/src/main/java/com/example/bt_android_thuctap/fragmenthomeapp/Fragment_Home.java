package com.example.bt_android_thuctap.fragmenthomeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.adpter.UserAdapter;
import com.example.bt_android_thuctap.databinding.FragmentHomeBinding;
import com.example.bt_android_thuctap.databinding.FragmentSignUpBinding;
import com.example.bt_android_thuctap.model.User;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends Fragment {

//    RecyclerView rcvFramentHome;
    UserAdapter userAdapter;
    List<User> data;
    FragmentHomeBinding fragmentHomeBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentHomeBinding= FragmentHomeBinding.inflate(inflater, container, false);
        View mview=fragmentHomeBinding.getRoot();
        data = new ArrayList<>();
        data.add(new User("0001","0001","0001"));
        data.add(new User("0001","0001","0001"));
        data.add(new User("0001","0001","0001"));
        Log.e("AAAAAAAA",""+data.size());
        userAdapter = new UserAdapter(data);
        fragmentHomeBinding.rectanglesUser.setAdapter(userAdapter);
        fragmentHomeBinding.rectanglesUser.setVisibility(View.VISIBLE);

        return mview;
    }
}
