package com.example.bt_android_thuctap.fragmenthomeapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.adpter.RecentConversationsAdapter;
import com.example.bt_android_thuctap.adpter.UserAdapter;
import com.example.bt_android_thuctap.databinding.FragmentConversionsBinding;
import com.example.bt_android_thuctap.databinding.FragmentHomeBinding;
import com.example.bt_android_thuctap.model.ChatMessage;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ConversionsFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    List<ChatMessage> conversions;
    RecentConversationsAdapter recentConversationsAdapter;
    FragmentConversionsBinding binding;
    PreferenceManager preferenceManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentConversionsBinding.inflate(inflater, container, false);
        View mview=binding.getRoot();
        init();
//        listenerConversions();



        return mview;

    }

        public void init(){
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        conversions = new ArrayList<>();
        recentConversationsAdapter = new RecentConversationsAdapter(conversions,this);
        binding.rectanglesUser.setAdapter(recentConversationsAdapter);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

//    private void listenerConversions(){
//        firebaseFirestore.collection(Constants.key_Conversion_Col)
//                .whereEqualTo(Constants.key_Sender_Id,preferenceManager.getString(Constants.key_UserId))
//                .addSnapshotListener(eventListener);
//        firebaseFirestore.collection(Constants.key_Conversion_Col)
//                .whereEqualTo(Constants.key_Receiver_Id,preferenceManager.getString(Constants.key_UserId))
//                .addSnapshotListener(eventListener);
//        Log.e("TAG aaaaaaaaaaaaaaaaaa", "onCreateView: "+conversions.size() );
//    }
//
//    private final EventListener<QuerySnapshot> eventListener = (value, error) ->{
//        if(error != null){
//            return;
//        }
//        if(value!= null){
//            for(DocumentChange documentChange : value.getDocumentChanges()){
//                if(documentChange.getType() == DocumentChange.Type.ADDED){
//                    String senderId = documentChange.getDocument().getString(Constants.key_Sender_Id);
//                    String recevierId = documentChange.getDocument().getString(Constants.key_Receiver_Id);
//                    ChatMessage chatMessage = new ChatMessage();
//                    chatMessage.setIdReceiver(recevierId);
//                    chatMessage.setIdSend(senderId);
//                    if(preferenceManager.getString(Constants.key_Sender_Id).equals(senderId)){
//                          chatMessage.setConversionId(documentChange.getDocument().getString(Constants.key_Sender_Id));
//                          chatMessage.setConversionName(documentChange.getDocument().getString(Constants.key_Sender_Name));
//                          chatMessage.setConversionImage(documentChange.getDocument().getString(Constants.key_Sender_Image));
//                    }
//                    else{
//                        chatMessage.setConversionId(documentChange.getDocument().getString(Constants.key_Receiver_Id));
//                        chatMessage.setConversionName(documentChange.getDocument().getString(Constants.key_Receiver_Name));
//                        chatMessage.setConversionImage(documentChange.getDocument().getString(Constants.key_Receiver_Image));
//                    }
//                    chatMessage.setMessage(documentChange.getDocument().getString(Constants.key_Last_Message));
//                    chatMessage.setDateObject(documentChange.getDocument().getDate(Constants.key_Time));
//                    conversions.add(chatMessage);
//
//
//                }
//                else if(documentChange.getType() == DocumentChange.Type.MODIFIED){
//                    for(int i = 0 ; i<= conversions.size() ; i++){
//                        String senderId = documentChange.getDocument().getString(Constants.key_Sender_Id);
//                        String recevierId = documentChange.getDocument().getString(Constants.key_Receiver_Id);
//                        if(conversions.get(i).getIdSend().equals(senderId) && conversions.get(i).getIdReceiver().equals(recevierId))
//                        {
//                            conversions.get(i).setMessage(documentChange.getDocument().getString(Constants.key_Last_Message));
//                            conversions.get(i).setDateObject(documentChange.getDocument().getDate(Constants.key_Time));
//                            break;
//                        }
//                    }
//                }
//            }
//            Collections.sort(conversions, (obj1,obj2) -> obj1.getDateObject().compareTo(obj2.getDateObject()));
//            recentConversationsAdapter.notifyDataSetChanged();
//            binding.rectanglesUser.smoothScrollToPosition(0);
//            binding.rectanglesUser.setVisibility(View.VISIBLE);
//
//        }
//    };

}