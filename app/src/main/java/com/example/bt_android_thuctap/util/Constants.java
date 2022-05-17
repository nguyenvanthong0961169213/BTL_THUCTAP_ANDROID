package com.example.bt_android_thuctap.util;

import java.util.HashMap;

public class Constants {
      public static final String key_User = "user";
    public static final String key_User_Col = "User";
    public static final String key_Status = "status";
    public static final String key_Status_Off = "offline";
    public static final String key_Status_Onl = "online";
    public static final String key_Name = "name";
    public static final String key_Password = "password";
    public static final String key_Phone = "phone";
    public static final String key_UserId = "UserId";
    public static final String key_Pre_Name = "chatAppPreference";
    public static final String key_Is_Sign_In = "isSignIn";
    public static final String key_FCM_Token = "fcmtoken";


    public static final String key_Message_Col = "ChatMessage";
    public static final String key_Sender_Id = "idSend";
    public static final String key_Receiver_Id = "idReceiver";
    public static final String key_Message = "message";
    public static final String key_Time = "Time";
    public static final String key_Image = "image";

    public static final String key_Conversion_Col = "Conversation";
    public static final String key_Sender_Name = "nameSend";
    public static final String key_Receiver_Name = "nameReceiver";
    public static final String key_Sender_Image = "imageSend";
    public static final String key_Receiver_Image = "imageReceiver";
    public static final String key_Last_Message = "lastMessage";

    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS_DATA = "registration_ids";

    public static HashMap<String,String> remoteMsgHeader = null;
    public static HashMap<String,String> getremoteMsgHeader(){
        if(remoteMsgHeader == null){
            remoteMsgHeader = new HashMap<>();
            remoteMsgHeader.put(REMOTE_MSG_AUTHORIZATION,
                    "key=AAAA1DJXVZU:APA91bEUSliROYzEs1qC0kfivvQ3W0LfFZWLuiNvyjrBHldg" +
                            "SJ3cCKX2-hKVwRtZtxDYGVudREwR_LbMhq9YEmEDKbPooDw2aRmSBK1P14vjBpFdHgiP2RySZksKw3vhinrrI6ceekzF");
            remoteMsgHeader.put(REMOTE_MSG_CONTENT_TYPE , "application/json");
        }
        return remoteMsgHeader;
    }



}
