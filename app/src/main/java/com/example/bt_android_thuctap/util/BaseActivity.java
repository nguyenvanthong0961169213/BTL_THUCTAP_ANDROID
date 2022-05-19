package com.example.bt_android_thuctap.util;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseActivity extends AppCompatActivity {
    PreferenceManager preferenceManager;
    FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    public void init(){
        preferenceManager = new PreferenceManager(this.getApplicationContext());
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection(Constants.key_User_Col)
                .document(preferenceManager.getString(Constants.key_UserId));
    }

    @Override
    public void onPause() {
        super.onPause();
        documentReference.update(Constants.key_Status, Constants.key_Status_Off);
    }

    @Override
    public void onResume() {
        super.onResume();
        documentReference.update(Constants.key_Status, Constants.key_Status_Onl);
    }
}
