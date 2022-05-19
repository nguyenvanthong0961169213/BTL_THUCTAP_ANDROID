package com.example.bt_android_thuctap;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.TransactionTooLargeException;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bt_android_thuctap.adpter.ChatSenseAdapter;
import com.example.bt_android_thuctap.common.CloudStorage;
import com.example.bt_android_thuctap.databinding.FragmentSceneChatBinding;
import com.example.bt_android_thuctap.model.ChatMessage;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSceneChat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSceneChat extends Fragment {
    private final String TAG = "Debuger";
    public  FragmentSceneChatBinding fragmentSceneChatBinding;
    public List<ChatMessage> data;
    ChatSenseAdapter adpater;
    PreferenceManager preferenceManager;
    public User receiverUser;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    String conversionsId = null;
    private String pathImage;
    Uri file;

    //constructor
    public FragmentSceneChat() {
    }

    public static FragmentSceneChat newInstance() {
        FragmentSceneChat fragment = new FragmentSceneChat();
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(outState != null){
            outState.clear();
        }
    }

    //inflateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSceneChatBinding= FragmentSceneChatBinding.inflate(inflater, container, false);
        View mview=fragmentSceneChatBinding.getRoot();
        receiverUser = (User) getArguments().getSerializable("haha");
        fragmentSceneChatBinding.txtNameFriendChatSense.setText(receiverUser.getName());
        init();
        updateMessage();
        fragmentSceneChatBinding.btnOpenImage.setOnClickListener(v -> openImage ());
        fragmentSceneChatBinding.btnCamera.setOnClickListener(v -> captureImage());
        fragmentSceneChatBinding.layoutsend.setOnClickListener(v-> SendMessage());

        return mview;
    }


    public void  init(){
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        data = new ArrayList<>();
        adpater = new ChatSenseAdapter(data,this);
        fragmentSceneChatBinding.rcvChatSense.setAdapter(adpater);
        firebaseFirestore = FirebaseFirestore.getInstance();
        //add tham chiếu
        FirebaseStorage storage = FirebaseStorage.getInstance ();
        storageReference = storage.getReference ();
    }


    public void openImage() {
        mGetContent.launch("image/*");
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if(uri!=null) {
                        pathImage = uri.toString ();
                        file = uri;
                        fragmentSceneChatBinding.imageView2.setImageURI (uri);
                        fragmentSceneChatBinding.imageView2.setVisibility (View.VISIBLE);
                    }
                }
            });
    private File output = null;
    private static final int CONTENT_REQUEST=1337;
    public void captureImage() {
    }

    public void SendMessage(){
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.key_Sender_Id,preferenceManager.getString(Constants.key_UserId));
        message.put(Constants.key_Receiver_Id,receiverUser.getId());
        if(fragmentSceneChatBinding.txtinputMessage.getText().toString().isEmpty()){
            message.put(Constants.key_Message,"");
        }
        else{
            message.put(Constants.key_Message,fragmentSceneChatBinding.txtinputMessage.getText().toString());
        }
        if(pathImage == null){
            message.put(Constants.key_Image_Mess,"like");
        }
        else{
            message.put(Constants.key_Image_Mess,pathImage);
            fragmentSceneChatBinding.imageView2.setVisibility (View.INVISIBLE);
        }


        message.put(Constants.key_Time,new Date());
        firebaseFirestore.collection(Constants.key_Message_Col).add(message);
        if(conversionsId != null){
            updateConversion(fragmentSceneChatBinding.txtinputMessage.getText().toString());
        }
        else{
            HashMap<String , Object> conversion = new HashMap<>();
            conversion.put(Constants.key_Sender_Id,preferenceManager.getString(Constants.key_UserId));
            conversion.put(Constants.key_Sender_Name,preferenceManager.getString(Constants.key_Name));
            conversion.put(Constants.key_Sender_Image,preferenceManager.getString(Constants.key_Image));
            conversion.put(Constants.key_Receiver_Id,receiverUser.getId());
            conversion.put(Constants.key_Receiver_Name,receiverUser.getName());
            conversion.put(Constants.key_Receiver_Image,receiverUser.getImage());
            conversion.put(Constants.key_Last_Message,fragmentSceneChatBinding.txtinputMessage.getText().toString());
            conversion.put(Constants.key_Time,new Date());
            addConversion(conversion);

        }
        pathImage = null;
    }

    //adapter
    public User setDataSender(){
        Layout_Home layout_home = (Layout_Home) getActivity();
        User user = layout_home.SetDataUser();
        Log.e("TAG", "setDataSender: "+ user.getName() );
        return user;
    }

    public void updateMessage(){
        firebaseFirestore.collection(Constants.key_Message_Col)
                .whereEqualTo(Constants.key_Sender_Id,preferenceManager.getString(Constants.key_UserId))
                .whereEqualTo(Constants.key_Receiver_Id,receiverUser.getId())
                .addSnapshotListener(eventListener);
    }


    private final EventListener<QuerySnapshot> eventListener = (value, error) ->{
        if(error != null){
            return;
        }
        if(value!= null){

            int count = data.size();
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setIdSend(documentChange.getDocument().getString(Constants.key_Sender_Id));
                    chatMessage.setIdReceiver(documentChange.getDocument().getString(Constants.key_Receiver_Id));
                    chatMessage.setMessage(documentChange.getDocument().getString(Constants.key_Message));
                    chatMessage.setUri(documentChange.getDocument().getId ());
                    chatMessage.setTime(getReableDateTime(documentChange.getDocument().getDate(Constants.key_Time)));
                    chatMessage.setDateObject(documentChange.getDocument().getDate(Constants.key_Time));
                    data.add(chatMessage);
                    if(file!=null && pathImage==null)
                        new CloudStorage ().uploadFromLocal (file,chatMessage.getUri ());
                }
            }
            Collections.sort(data, (obj1,obj2) -> obj1.getDateObject().compareTo(obj2.getDateObject()));
            if(count == 0){
                adpater.notifyDataSetChanged();
            }
            else{
                adpater.notifyItemRangeInserted(data.size(),data.size());
                fragmentSceneChatBinding.rcvChatSense.smoothScrollToPosition(data.size()-1);
            }
            if (conversionsId == null){
                checkForConversion();
            }
        }
    };


    private String getReableDateTime(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy- hh:mm a" , Locale.getDefault()).format(date);
    }

    //
    private void addConversion(HashMap<String ,Object> conversion){
        firebaseFirestore.collection(Constants.key_Conversion_Col)
                .add(conversion)
                .addOnSuccessListener(DocumentReference -> conversionsId = DocumentReference.getId());
    }

    private void updateConversion(String message){
        DocumentReference documentReference = firebaseFirestore.collection(Constants.key_Conversion_Col)
                .document(conversionsId);
        documentReference.update(
          Constants.key_Last_Message, message ,
          Constants.key_Time , new Date()
        );
    }

    private void checkForConversion(){
        if(data.size() != 0){
            checkConversionsRemotely(preferenceManager.getString(Constants.key_UserId),
                    receiverUser.getId());
            checkConversionsRemotely(receiverUser.getId(),
                    preferenceManager.getString(Constants.key_UserId));
        }
    }

    private void checkConversionsRemotely(String senderId , String receiverId){
        firebaseFirestore.collection(Constants.key_Conversion_Col).
                whereEqualTo(Constants.key_Sender_Id,senderId).
                whereEqualTo(Constants.key_Receiver_Id,receiverId).get().
                addOnCompleteListener(conversionOnCompleteListener);
    }

    private final OnCompleteListener<QuerySnapshot> conversionOnCompleteListener = task -> {
        if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size()>0 ){
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversionsId = documentSnapshot.getId();
        }
    };

}