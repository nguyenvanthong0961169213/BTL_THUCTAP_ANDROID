package com.example.bt_android_thuctap.model;

public class ChatMessage {
    private String IdReciver,IdSend,Timestamp;

    public ChatMessage(String idReciver, String idSend, String timestamp) {
        IdReciver = idReciver;
        IdSend = idSend;
        Timestamp = timestamp;
    }

    public String getIdReciver() {
        return IdReciver;
    }

    public void setIdReciver(String idReciver) {
        IdReciver = idReciver;
    }

    public String getIdSend() {
        return IdSend;
    }

    public void setIdSend(String idSend) {
        IdSend = idSend;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }
}
