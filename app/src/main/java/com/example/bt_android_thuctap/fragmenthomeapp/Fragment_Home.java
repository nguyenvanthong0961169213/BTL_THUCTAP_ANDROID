package com.example.bt_android_thuctap.fragmenthomeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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

import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fragment_Home extends Fragment {

    User user;
    NavController navigation;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    UserAdapter userAdapter;
    List<User> data;
    FragmentHomeBinding fragmentHomeBinding;
    PreferenceManager preferenceManager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentHomeBinding= FragmentHomeBinding.inflate(inflater, container, false);

        navigation = NavHostFragment.findNavController(this);
//        fragmentHomeBinding = DataBindingUtil.setContentView(getActivity(),R.layout.fragment_home);

        LoadingData();
        userAdapter = new UserAdapter(data,this);
        fragmentHomeBinding.rectanglesUser.setAdapter(userAdapter);
        fragmentHomeBinding.rectanglesUser.setVisibility(View.VISIBLE);

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
                    UserAdapter userAdapter = new UserAdapter(data,this);
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

    public void LoadingFriend(User Friend) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("haha",Friend);
        navigation.navigate(R.id.action_fragment_Home_to_fragmentSceneChat,bundle);


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
