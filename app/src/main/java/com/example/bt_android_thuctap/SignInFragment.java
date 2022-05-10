package com.example.bt_android_thuctap;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

//import com.example.bt_android_thuctap.databinding.FragmentSignInBinding;
import com.example.bt_android_thuctap.databinding.FragmentSignInBinding;
import com.example.bt_android_thuctap.databinding.UserContainerBinding;

import com.example.bt_android_thuctap.model.User;

import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.example.bt_android_thuctap.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

import java.util.Objects;

public class SignInFragment extends Fragment {

    PreferenceManager preferenceManager;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    LoginViewModel loginViewModel;
    private FragmentSignInBinding fragmentSignInBinding;
    public SignInFragment() {
        // Required empty public constructor
    }
    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        if(preferenceManager.getBoolean(Constants.key_Is_Sign_In)){
            Intent intent = new Intent(getActivity(),Layout_Home.class);
            startActivity(intent);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UserContainerBinding userContainerBinding;
        // Inflate the layout for this fragment
        fragmentSignInBinding= FragmentSignInBinding.inflate(inflater, container, false);
        View mview=fragmentSignInBinding.getRoot();

        loginViewModel = new LoginViewModel();
        fragmentSignInBinding.setLoginViewModel(loginViewModel);
        fragmentSignInBinding.dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                if (is_night) {
                    fragmentSignInBinding.sun.animate().translationY(30).setDuration(1000);
                    fragmentSignInBinding.dayBCkground.animate().alpha(0).setDuration(1300);
                } else {
                    fragmentSignInBinding.sun.animate().translationY(-30).setDuration(1000);
                    fragmentSignInBinding.dayBCkground.animate().alpha(1).setDuration(1300);
                }
            }
        });
        fragmentSignInBinding.btnSignin.setOnClickListener(v-> SignInClick());
        fragmentSignInBinding.btnCreateaccount.setOnClickListener(v-> CreateAccountClick());
        return mview;
    }

    public void SignInClick() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("User").whereEqualTo("phone",loginViewModel.getPhoneNumber())
                .whereEqualTo("password",loginViewModel.getPassword()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult()!= null && task.getResult().getDocuments().size()>0){
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                preferenceManager.putBoolean(Constants.key_Is_Sign_In,true);
                preferenceManager.putString(Constants.key_UserId,documentSnapshot.getId());
                preferenceManager.putString(Constants.key_Name,documentSnapshot.getString(Constants.key_Name));

                preferenceManager.putString(Constants.key_Phone,documentSnapshot.getString(Constants.key_Phone));

                loginViewModel.validate.set("Dang nhap thanh cong");
                Intent intent = new Intent(getActivity(),Layout_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else{
                loginViewModel.validate.set("Dang nhap that bai");
            }
        });

    }

    public void  CreateAccountClick(){
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.view_pager_login,SignUpFragment.newInstance());
        transaction.commit();
    }




}