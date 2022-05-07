package com.example.bt_android_thuctap.fragmenthomeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.FragmentSceneChat;
import com.example.bt_android_thuctap.Layout_Home;
import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.adpter.UserAdapter;
import com.example.bt_android_thuctap.databinding.FragmentHomeBinding;
import com.example.bt_android_thuctap.databinding.FragmentSignUpBinding;
import com.example.bt_android_thuctap.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fragment_Home extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    UserAdapter userAdapter;
    List<User> data;
    FragmentHomeBinding fragmentHomeBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentHomeBinding= FragmentHomeBinding.inflate(inflater, container, false);
        View mview=fragmentHomeBinding.getRoot();
        data = new ArrayList<>();
        Log.e("AAAAAAAA",""+data.size());
        LoadingData();
        userAdapter = new UserAdapter(data,this);
        fragmentHomeBinding.rectanglesUser.setAdapter(userAdapter);
        fragmentHomeBinding.rectanglesUser.setVisibility(View.VISIBLE);
//        fragmentHomeBinding.edtSearch.setOnClickListener(v-> LoadingFriend());


        return mview;
    }

    public void LoadingFriend() {
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, FragmentSceneChat.newInstance());
        transaction.commit();
    }

    public void LoadingData() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        data.add(new User(doc.get("name").toString(),doc.get("password").toString()
                                ,doc.get("name").toString()));

                    }

                }
            }
        });
    }
}
