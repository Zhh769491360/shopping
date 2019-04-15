package com.example.pc.bottomlansu;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtilEila {
    public static void sendOkHttpRequester(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url( address )
                .build();
        client.newCall(request).enqueue( callback );
    }
    public static void PostSendHttpRequester(String address,String link,String content,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add(link, content)
                .build();
        Request request = new Request.Builder()
                .url( address )
                .post(requestBody)
                .build();
        client.newCall(request).enqueue( callback );
    }

}
