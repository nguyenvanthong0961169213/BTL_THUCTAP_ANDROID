package com.example.bt_android_thuctap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bt_android_thuctap.databinding.FragmentSignUpBinding;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {



    FragmentSignUpBinding fragmentSignUpBinding;
    LoginViewModel loginViewModel;
    ViewPagerLoginAdaper viewPagerLoginAdaper;
    FirebaseFirestore firebaseFirestore;


    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSignUpBinding= FragmentSignUpBinding.inflate(inflater, container, false);
        View mview=fragmentSignUpBinding.getRoot();

        loginViewModel = new LoginViewModel();
        fragmentSignUpBinding.setLoginViewModel(loginViewModel);
        fragmentSignUpBinding.btnSignUp1.setOnClickListener(v-> SignUpClick());
        return mview;
    }


    private void SignUpClick() {
        User user = new User(loginViewModel.getPhoneNumber(),loginViewModel.getPassword(),"user");
        if(isValidPassWord(user.getPassword())  == true && isValidPhone(user.getPhoneNumber()) == true)
        {
            firebaseFirestore = FirebaseFirestore.getInstance();
            HashMap<String, String> data = new HashMap<>();
            data.put("name","");
            data.put("password",loginViewModel.getPassword().toString());
            data.put("phone",loginViewModel.getPhoneNumber().toString());
            firebaseFirestore.collection("User").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    loginViewModel.validate.set("Add thanh cong");
                    FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.view_pager_login,SignInFragment.newInstance());
                    transaction.commit();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
//           loginViewModel.validate.set("Đăng kí thành công");



        }
        else{
              loginViewModel.validate.set("Đăng kí thất bại");


       }
    }
    public boolean isValidPassWord(String str){
        if(str.length()>=6){
            return true;

        }
        return false;
    }

    public boolean isValidPhone(String str){
        Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher match = ptrn.matcher(str);
        return true;
    }

}