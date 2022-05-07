package com.example.bt_android_thuctap.fragmenthomeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.adpter.UserAdapter;
import com.example.bt_android_thuctap.databinding.FragmentHomeBinding;
import com.example.bt_android_thuctap.databinding.FragmentSignUpBinding;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends Fragment {

//    RecyclerView rcvFramentHome;
    UserAdapter userAdapter;
    List<User> data;
    FragmentHomeBinding fragmentHomeBinding;
    PreferenceManager preferenceManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentHomeBinding= FragmentHomeBinding.inflate(inflater, container, false);
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        View mview=fragmentHomeBinding.getRoot();
        Toast.makeText(getContext(),preferenceManager.getString(Constants.key_UserId),Toast.LENGTH_LONG).show();
        FirebaseFirestore dataUser =  FirebaseFirestore.getInstance();
        dataUser.collection("User").get().addOnCompleteListener(task -> {
            String currentUserId = preferenceManager.getString(Constants.key_UserId);
            if(task.isSuccessful() && task.getResult()!= null){
                data = new ArrayList<>();
                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                    if(currentUserId.equals(queryDocumentSnapshot.getId())){
                        continue;
                    }
                    User user = new User(queryDocumentSnapshot.getString(Constants.key_Phone),queryDocumentSnapshot.getString(Constants.key_Name),
                            queryDocumentSnapshot.getString(Constants.key_FCM_Token));
                    data.add(user);


                }
                if(data.size()>0){
                    UserAdapter userAdapter = new UserAdapter(data);
                    fragmentHomeBinding.rectanglesUser.setAdapter(userAdapter);
                    fragmentHomeBinding.rectanglesUser.setVisibility(View.VISIBLE);
                }else{

                }
            }
            else{

            }
        });

        return mview;
    }
}
