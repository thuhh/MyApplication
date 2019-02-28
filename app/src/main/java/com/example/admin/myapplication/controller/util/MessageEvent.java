package com.example.admin.myapplication.controller.util;

public class MessageEvent {
    public String mMessage;
    public String image;

    public MessageEvent(String message) {
        mMessage = message;
    }

    public MessageEvent(String message, String image) {
        mMessage = message;
        this.image = image;
    }

}
