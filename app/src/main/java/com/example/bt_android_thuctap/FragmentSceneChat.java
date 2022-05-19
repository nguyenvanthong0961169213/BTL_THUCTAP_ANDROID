package com.example.bt_android_thuctap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bt_android_thuctap.adpter.ChatSenseAdapter;
import com.example.bt_android_thuctap.databinding.FragmentSceneChatBinding;
import com.example.bt_android_thuctap.model.ChatMessage;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSceneChat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSceneChat extends Fragment {
    public  FragmentSceneChatBinding fragmentSceneChatBinding;
    public List<ChatMessage> data;
    ChatSenseAdapter adpater;
    PreferenceManager preferenceManager;
    public User receiverUser;
    FirebaseFirestore firebaseFirestore;
    String conversionsId = null;

    public FragmentSceneChat() {
    }

    public static FragmentSceneChat newInstance() {
        FragmentSceneChat fragment = new FragmentSceneChat();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSceneChatBinding= FragmentSceneChatBinding.inflate(inflater, container, false);
        View mview=fragmentSceneChatBinding.getRoot();
        receiverUser = (User) getArguments().getSerializable("haha");
        fragmentSceneChatBinding.txtNameFriendChatSense.setText(receiverUser.getName());
        init();
        updateMessage();
        fragmentSceneChatBinding.layoutsend.setOnClickListener(v-> SendMessage());
        return mview;
    }

    public void SendMessage(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.key_Sender_Id,preferenceManager.getString(Constants.key_UserId));
        message.put(Constants.key_Receiver_Id,receiverUser.getId());
        message.put(Constants.key_Message,fragmentSceneChatBinding.txtinputMessage.getText().toString());
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
//            Log.e("", "SendMessage: "+receiverUser.getImage() );
            conversion.put(Constants.key_Receiver_Image,receiverUser.getImage());
            conversion.put(Constants.key_Last_Message,fragmentSceneChatBinding.txtinputMessage.getText().toString());
            conversion.put(Constants.key_Time,new Date());
            addConversion(conversion);

        }
        fragmentSceneChatBinding.txtinputMessage.setText(null);
    }

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
        firebaseFirestore.collection(Constants.key_Message_Col)
                .whereEqualTo(Constants.key_Sender_Id,receiverUser.getId())
                .whereEqualTo(Constants.key_Receiver_Id,preferenceManager.getString(Constants.key_UserId))
                .addSnapshotListener(eventListener);
    }

    public void  init(){
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        data = new ArrayList<>();
        adpater = new ChatSenseAdapter(data,this);
        fragmentSceneChatBinding.rcvChatSense.setAdapter(adpater);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private String getReableDateTime(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy- hh:mm a" , Locale.getDefault()).format(date);
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
                    chatMessage.setTime(getReableDateTime(documentChange.getDocument().getDate(Constants.key_Time)));
                    chatMessage.setDateObject(documentChange.getDocument().getDate(Constants.key_Time));
                    data.add(chatMessage);
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