package com.example.bt_android_thuctap.common;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class CloudStorage {
   // private StorageReference storageRef = FirebaseStorage.getInstance ().getReference ();
    private StorageReference storageRef = FirebaseStorage.getInstance ().getReferenceFromUrl ("gs://w6chat.appspot.com");
    private String idChatMessage;
    public void uploadFromLocal(Uri uri,String idChatMessage){
       // StorageReference mStorageReference = storageRef.child ("images/"+ idChatMessage);
       StorageReference mStorageReference = storageRef.child ("images/"+ idChatMessage);
        UploadTask uploadTask = mStorageReference.putFile (uri);

        uploadTask.addOnFailureListener (new OnFailureListener () {
            @Override
            public void onFailure (@NonNull Exception e) {
                Log.e ("mTag", "onFailure: ");
            }
        }).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onSuccess (UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("mTag","onSuccess");
            }
        });
    }

}
