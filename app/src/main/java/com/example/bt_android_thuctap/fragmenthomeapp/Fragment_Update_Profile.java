package com.example.bt_android_thuctap.fragmenthomeapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.common.Common;
import com.example.bt_android_thuctap.common.Convert;
import com.example.bt_android_thuctap.databinding.FragmentUpdateProfileBinding;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class Fragment_Update_Profile extends Fragment {
    private String encodeImage;
    FragmentUpdateProfileBinding fragmentUpdateProfileBinding;
    PreferenceManager preferenceManager;
    User user;
    Uri saveUri;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    Bitmap bitmap;
    StorageReference storageReference;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* return inflater.inflate(R.layout.fragment_update_profile,container,false);*/

        fragmentUpdateProfileBinding = FragmentUpdateProfileBinding.inflate(inflater, container, false);
        View mview = fragmentUpdateProfileBinding.getRoot();
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        setF(user);
        setListeners();

        return mview;
    }

    private void setF(User user) {
        user = new User(preferenceManager.getString(Constants.key_Name), preferenceManager.getString(Constants.key_Phone));
        fragmentUpdateProfileBinding.textNameDisplay.setText(user.getName().toString());
        fragmentUpdateProfileBinding.textNameUpdate.setText(user.getName().toString());
        fragmentUpdateProfileBinding.textPhoneUpdate.setText(user.getPhoneNumber().toString());
        fragmentUpdateProfileBinding.textPhoneDisplay.setText(user.getPhoneNumber().toString());
        if(preferenceManager.getString(Constants.key_Image)!=null){
            fragmentUpdateProfileBinding.imgUserprofile.setImageBitmap(Convert.base64ToBitmap(preferenceManager.getString(Constants.key_Image)));

        }

        Common.user = user;

    }


    private void setListeners() {
        fragmentUpdateProfileBinding.btnUpdate.setOnClickListener(v -> {
            if (isValidSignUpDetail()) {
                update();
            }
        });
        fragmentUpdateProfileBinding.imagePhotoUpdate.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent,"Choose picture"),1);


        });
        fragmentUpdateProfileBinding.imageCameraUpdate.setOnClickListener(view -> {
            askCameraPermission();
        });
    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},CAMERA_PERM_CODE);
        }else{
            openCamera();
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera,CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length<0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else
            {
                Toast.makeText(getActivity().getApplicationContext(),"Camera need permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK && requestCode == 1){
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                fragmentUpdateProfileBinding.imgUserprofile.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }
        if(requestCode == CAMERA_REQUEST_CODE){
            bitmap =(Bitmap) data.getExtras().get("data");
            fragmentUpdateProfileBinding.imgUserprofile.setImageBitmap(bitmap);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSignUpDetail() {
        if (fragmentUpdateProfileBinding.textNameUpdate.getText().toString().trim().isEmpty()) {
            showToast("Enter name");
            return false;
        } else if (fragmentUpdateProfileBinding.textPhoneUpdate.getText().toString().trim().isEmpty()) {
            showToast("Enter phone");
            return false;
        } else {
            return true;

        }
    }


    private void update() {
        encodeImage = Convert.bitmapToBase64(bitmap);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        String name = fragmentUpdateProfileBinding.textNameUpdate.getText().toString();
        String phone = fragmentUpdateProfileBinding.textPhoneUpdate.getText().toString();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.key_Image, encodeImage);
        user.put(Constants.key_Name,name);
        user.put(Constants.key_Phone,phone);
        Log.d("Test",""+preferenceManager.getString(Constants.key_UserId));
        database.collection(Constants.key_User_Col).document(preferenceManager.getString(Constants.key_UserId)).update(user).addOnSuccessListener(documentReference -> {
            preferenceManager.putString(Constants.key_Image, encodeImage);
            preferenceManager.putString(Constants.key_Name, name);
            preferenceManager.putString(Constants.key_Image, encodeImage);
        }).addOnFailureListener(e -> {
            showToast(e.getMessage());
        });
    }

}