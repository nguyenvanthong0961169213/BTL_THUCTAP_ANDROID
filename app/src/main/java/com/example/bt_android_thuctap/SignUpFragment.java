package com.example.bt_android_thuctap;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bt_android_thuctap.databinding.FragmentSignUpBinding;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.viewmodel.LoginViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    FragmentSignUpBinding fragmentSignUpBinding;
    LoginViewModel loginViewModel;
    ViewPagerLoginAdaper viewPagerLoginAdaper;
    FirebaseFirestore firebaseFirestore;
    String confirm, pw,phone;

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
        fragmentSignUpBinding = FragmentSignUpBinding.inflate(inflater, container, false);
        View mview = fragmentSignUpBinding.getRoot();
        firebaseFirestore = FirebaseFirestore.getInstance();
        loginViewModel = new LoginViewModel();
        fragmentSignUpBinding.setLoginViewModel(loginViewModel);
        fragmentSignUpBinding.btnSignUp1.setOnClickListener(v -> SignUpClick());
        fragmentSignUpBinding.btnSignininfragsignup.setOnClickListener(v -> SignInClick());
        return mview;
    }

    private void SignInClick() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.view_pager_login, SignInFragment.newInstance());
        fragmentTransaction.commit();
    }

    private void SignUpClick() {
        User user = new User(loginViewModel.getPhoneNumber(), loginViewModel.getPassword(), "user");
        pw = fragmentSignUpBinding.txtPassword.getText().toString().trim();
        confirm = fragmentSignUpBinding.txtConfirm.getText().toString().trim();
        phone=fragmentSignUpBinding.txtPhoneSignup.getText().toString().trim();
        boolean test = isValidPassWord();
        if (test == true && isValidPhone(user.getPhoneNumber()) == true) {
//           loginViewModel.validate.set("Đăng kí thành công");
            Map<String, Object> newUser = new HashMap<>();
            newUser.put(Constants.key_Name, "new User");
            newUser.put(Constants.key_Phone, loginViewModel.getPhoneNumber());
            newUser.put(Constants.key_Password, loginViewModel.getPassword());
            newUser.put(Constants.key_Status, Constants.key_Status_Off);
            firebaseFirestore.collection(Constants.key_User_Col)
                    .add(newUser);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.view_pager_login, SignInFragment.newInstance());
            transaction.commit();
        } else {
           // loginViewModel.validate.set("Đăng kí thất bại");

        }
    }

    public boolean isValidPassWord() {
        if (pw.length() < 6)
        {
            Toast.makeText(getActivity(),"Password phải lớn hơn 6 kí tự!",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!pw.equals(confirm))
        {
            Toast.makeText(getActivity(),"Password và RepeatPassword không trùng nhau",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isValidPhone(String str) {
        if(phone.length()<10)
        {
            Toast.makeText(getActivity(),"Số điện thoại không hợp lệ",Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return true;
        }

    }
}