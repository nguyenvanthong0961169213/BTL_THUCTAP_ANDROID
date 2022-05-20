package com.example.bt_android_thuctap.fragmenthomeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.common.Common;
import com.example.bt_android_thuctap.databinding.FragmentChangePasswordBinding;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;

import java.util.HashMap;

public class Fragment_Changer_Password extends Fragment {

    FragmentChangePasswordBinding fragmentChangePasswordBinding;
    PreferenceManager preferenceManager;
    String pw;
    String newPassword;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_change_password,container,false);
        fragmentChangePasswordBinding = FragmentChangePasswordBinding.inflate(inflater,container,false);
        View mview = fragmentChangePasswordBinding.getRoot();
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        getCurrentPassword();
        setListeners();

        return mview;
    }
    private void getCurrentPassword(){
        DocumentReference docRef = firebaseFirestore.collection(Constants.key_User_Col).document(preferenceManager.getString(Constants.key_UserId));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot!=null){
                        String password = documentSnapshot.getString(Constants.key_Password);
                        Log.i("Logger","Name"+ documentSnapshot.getString("name"));
                        Log.i("Logger","Phone"+ documentSnapshot.getString("phone"));
                        Log.i("Logger","Pass"+ documentSnapshot.getString("password"));
                        pw = password;
                    }
                }
            }
        });
    }
    private void cancel() {
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,new HomeAppFragment());
        transaction.commit();
    }
    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean Validation() {
        if (fragmentChangePasswordBinding.OldPW.getText().toString().trim().isEmpty()) {
            showToast("Old password field must be fill!");
            return false;
        }
        else if (fragmentChangePasswordBinding.OldPW.getText().toString().trim().equals(pw) == false ) {
            showToast("Incorrect password!");
            return false;
        }
        else if (fragmentChangePasswordBinding.NewPW.getText().toString().trim().isEmpty()) {
            showToast("New password field must be fill!");
            return false;
        }
        else if (fragmentChangePasswordBinding.NewPW.getText().toString().trim().length()<6) {
            showToast("Your new password must contain 6 or more characters!");
            return false;
        }
        else if (fragmentChangePasswordBinding.ConfirmPW.getText().toString().trim().isEmpty()) {
            showToast("You must confirm your new password!");
            return false;
        }
        else if (fragmentChangePasswordBinding.NewPW.getText().toString().trim().
                equals(fragmentChangePasswordBinding.ConfirmPW.getText().toString().trim()) == false ) {
            showToast("Confirmation is incorrect!");
            return false;
        }
        else {
            return true;
        }
    }

    private void updatePassword(){
        newPassword = fragmentChangePasswordBinding.NewPW.getText().toString();
        HashMap<String,Object> user = new HashMap<>();
        user.put(Constants.key_Password,newPassword);
        firebaseFirestore.collection(Constants.key_User_Col).document(preferenceManager.getString(Constants.key_UserId)).
                update(user).addOnSuccessListener(documentReference -> {
                    preferenceManager.putString(Constants.key_Password,newPassword);
                    showToast("You have successfully changed your password!");
        }).addOnFailureListener(e ->
                showToast(e.getMessage()));
    }
    private void setListeners(){
        fragmentChangePasswordBinding.btnUpdate.setOnClickListener(v->{
            if(Validation()){
                updatePassword();
                Log.i("newpass",newPassword);
                cancel();
            }
        });
    }

}
