package com.example.bt_android_thuctap;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bt_android_thuctap.adpter.ChatSenseAdapter;
import com.example.bt_android_thuctap.databinding.FragmentSceneChatBinding;
import com.example.bt_android_thuctap.databinding.ItemContainerSentMessageBinding;
import com.example.bt_android_thuctap.firebase.FireBase;
import com.example.bt_android_thuctap.model.ChatMessage;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.network.ApiClient;
import com.example.bt_android_thuctap.network.ApiService;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSceneChat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSceneChat extends Fragment {
    public  FragmentSceneChatBinding fragmentSceneChatBinding;
    public List<ChatMessage> data;
    NavController navigation;
    public ChatSenseAdapter adpater;
    PreferenceManager preferenceManager;
    public User receiverUser;
    FirebaseFirestore firebaseFirestore;
    String conversionsId = null;
    Boolean isStatus;
    String pathImage;
    Uri file;

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
        receiverUser = (User) getArguments().getSerializable(Constants.key_Receiver_Id);
        fragmentSceneChatBinding.txtNameFriendChatSense.setText(receiverUser.getName());
        init();
        updateMessage();
        fragmentSceneChatBinding.btnCamera.setOnClickListener(v -> openCamera());
        fragmentSceneChatBinding.btnOpenImage.setOnClickListener (v -> openImage());
        fragmentSceneChatBinding.layoutsend.setOnClickListener(v-> SendMessage());
        fragmentSceneChatBinding.imageBack.setOnClickListener(v-> onBack());
        return mview;
    }

    void openCamera() {
        mGetContent.launch ("image/*");
    }
    ActivityResultLauncher<String> mGetContent = registerForActivityResult (new ActivityResultContracts.GetContent (),
            new ActivityResultCallback<Uri> () {
                @Override
                public void onActivityResult (Uri result) {
                    pathImage = result.toString ();
                    file = result;
                    fragmentSceneChatBinding.imageView2.setImageURI (result);
                    fragmentSceneChatBinding.imageView2.setVisibility (View.VISIBLE);
                }
            });

    void openImage() {

    }

    public void onBack(){
        navigation = NavHostFragment.findNavController(this);
        navigation.navigate(R.id.fragment_Home);
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
            message.put(Constants.key_Image,"");
        }
        else{
            message.put(Constants.key_Image,pathImage);
        }
        message.put(Constants.key_Time,new Date());
        firebaseFirestore.collection(Constants.key_Message_Col).add(message)
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //adpater.binding.checkSend.setImageResource(R.drawable.ic_send_unsuccess);
                Log.i ("MyTag","Fail to add message");
            }
        });

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
        if(!isStatus){
            try {
                JSONArray token = new JSONArray();
                token.put(receiverUser.getToken());
                Log.e("TAG", "SendMessage: aaaaaaaaaaaaaaa " +receiverUser.getToken() );

                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Constants.key_UserId,preferenceManager.getString(Constants.key_UserId));
                jsonObject.put(Constants.key_Name,preferenceManager.getString(Constants.key_Name));
                jsonObject.put(Constants.key_FCM_Token,preferenceManager.getString(Constants.key_FCM_Token));
                jsonObject.put(Constants.key_Message,fragmentSceneChatBinding.txtinputMessage.getText().toString());

                JSONObject body = new JSONObject();
                body.put(Constants.REMOTE_MSG_DATA,jsonObject);
                body.put(Constants.REMOTE_MSG_REGISTRATION_IDS_DATA,token);

                sendNotification(body.toString());
            }
            catch (Exception exception){
                Log.e("TAG", "SendMessage: aaaaaaaaaaaaaaa");
                show(exception.getMessage());
            }
        }
        pathImage = null;
        fragmentSceneChatBinding.imageView2.setVisibility (View.INVISIBLE);
        fragmentSceneChatBinding.txtinputMessage.setText(null);
    }

    public User setDataSender(){
        Layout_Home layout_home = (Layout_Home) getActivity();
        User user = layout_home.SetDataUser();
        Log.e("TAG", "setDataSender: "+ user.getName() );
        return user;
    }

    public void  init(){
        navigation = NavHostFragment.findNavController(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        data = new ArrayList<>();
        adpater = new ChatSenseAdapter(data,this);
        fragmentSceneChatBinding.rcvChatSense.setAdapter(adpater);
    }

    private String getReableDateTime(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy- hh:mm a" , Locale.getDefault()).format(date);
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
                    if(file != null & pathImage == null)
                        new FireBase ().uploadFromLocal (file,chatMessage.getUri ());

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

        }
        if(conversionsId == null){
            checkForConversion();
        }
    };

    private void addConversion(HashMap<String ,Object> conversion){
        firebaseFirestore.collection(Constants.key_Conversion_Col)
                .add(conversion)
                .addOnSuccessListener(documentReference -> conversionsId = documentReference.getId());
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

    private void listenerStatus(){
        firebaseFirestore.collection(Constants.key_User_Col)
                .document(receiverUser.getId())
                .addSnapshotListener(getActivity(),(value, error) -> {
                    if(error != null){
                        return;
                    }
                    if(value != null){
                        if(value.getString(Constants.key_Status) != null){
                            String availability = value.getString(Constants.key_Status);
                            isStatus = availability.equals("online");
                        }
                        receiverUser.setToken(value.getString(Constants.key_FCM_Token));
                    }
                    if(isStatus){
                        fragmentSceneChatBinding.imageOnl.setVisibility(View.VISIBLE);
                        fragmentSceneChatBinding.imageOff.setVisibility(View.GONE);
                    }
                    else{
                        fragmentSceneChatBinding.imageOnl.setVisibility(View.GONE);
                        fragmentSceneChatBinding.imageOff.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        listenerStatus();
    }

    private void show(String str){
        Toast.makeText(getActivity(),str, Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(String messageBody){
        ApiClient.getClient().create(ApiService.class).sendMessage(Constants.getremoteMsgHeader(),
                messageBody).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        if (response.body()!= null){
                            JSONObject resposneJson = new JSONObject(response.body());
                            JSONArray results = resposneJson.getJSONArray("results");
                            if(resposneJson.getInt("failure") == 1){
                                JSONObject error = (JSONObject) results.get(0);
                                show(error.getString("error"));
//                                Log.e("TAG", "SendMessage:eeeeeeeeeeee");
                                return;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    show("sent successfully");
                }
                else{
                    show("Error "+response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {
                    show(t.getMessage());
                Log.e("TAG", "SendMessage:dddddd");
            }
        });
    }

}