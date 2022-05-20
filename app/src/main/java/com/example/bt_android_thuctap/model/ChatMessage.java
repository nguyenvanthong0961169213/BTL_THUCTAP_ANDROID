package com.example.bt_android_thuctap.model;

import java.util.Date;

public class ChatMessage {
    String idReceiver;
    String idSend;
    String message;
    String Time;
    Date dateObject;

    public String getUri () {
        return uri;
    }

    public void setUri (String uri) {
        this.uri = uri;
    }

    String uri;

    public String conversionId , conversionName , conversionImage ;

    public ChatMessage(){}

    public ChatMessage(String idReceiver, String idSend, String message, String time,
                       Date dateObject, String conversionId, String conversionName, String conversionImage) {
        this.idReceiver = idReceiver;
        this.idSend = idSend;
        this.message = message;
        Time = time;
        this.dateObject = dateObject;
        this.conversionId = conversionId;
        this.conversionName = conversionName;
        this.conversionImage = conversionImage;
    }

    public ChatMessage(String idReceiver, String idSend, String message, String time, Date dateObject) {
        this.idReceiver = idReceiver;
        this.idSend = idSend;
        this.message = message;
        Time = time;
        this.dateObject = dateObject;
    }
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

    public Date getDateObject() {
        return dateObject;
    }

    public void setDateObject(Date dateObject) {
        this.dateObject = dateObject;
    }

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public String getConversionName() {
        return conversionName;
    }

    public void setConversionName(String conversionName) {
        this.conversionName = conversionName;
    }

    public String getConversionImage() {
        return conversionImage;
    }

    public void setConversionImage(String conversionImage) {
        this.conversionImage = conversionImage;
    }
}
