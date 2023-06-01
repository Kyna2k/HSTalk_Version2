package com.example.hstalk_version2.services;

import static com.example.hstalk_version2.services.API_SERVICES.BASE_Service;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private API_SERVICES requestInterface;
    public synchronized API_SERVICES getAPI() {
        return requestInterface;
    }
    public API(){
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_Service)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(API_SERVICES.class);
    }
}
