package com.example.bt_android_thuctap.firebase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class FireBase {

    public void visibleImage(String path, ImageView imageView){
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://w6chat.appspot.com").child("images/"+path);
        try{
            final File localFile = File.createTempFile(path,"jpeg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void uploadFromLocal(Uri uri, String idChatMessage){
        // StorageReference mStorageReference = storageRef.child ("images/"+ idChatMessage);
        StorageReference storageRef = FirebaseStorage.getInstance ().getReferenceFromUrl ("gs://w6chat.appspot.com");
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
                Log.e ("mTag","uploadsucces");
            }
        });
    }
    
}
