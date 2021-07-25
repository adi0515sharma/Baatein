package com.example.myapplication.Adapters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

public class FirebaseUsersForMessage {
    public String key;
    public String token;
    public Map<String, Boolean> message_sent = new HashMap<String, Boolean>();
    public Map<String, Boolean> message_receive = new HashMap<String, Boolean>();

    public void addMessageSent(String message, boolean seen){
        message_sent.put(message, seen);
    }

    public void setToken(String token){
        this.token = token;
    }
    public void addMessageReceive(String message, boolean seen){
        message_receive.put(message, seen);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSeenRece(String key, boolean seen){
        message_receive.replace(key, seen);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSeenSend(String key, boolean seen){
        message_sent.replace(key, seen);
    }
}
