package com.example.bt_android_thuctap.model;

public class ChatMessage {
    String idReceiver;
    String idSend;
    String message;
    String Time;

    public ChatMessage(String idReceiver, String idSend, String message, String time) {
        this.idReceiver = idReceiver;
        this.idSend = idSend;
        this.message = message;
        Time = time;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getIdSend() {
        return idSend;
    }

    public void setIdSend(String idSend) {
        this.idSend = idSend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;

    }
}
