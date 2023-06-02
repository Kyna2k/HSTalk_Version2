package com.example.hstalk_version2.services;

import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API_SERVICES {
    public static final String BASE_Service = "http://192.168.0.101:3000/api/";

    @POST("login-with-email")
    Observable<BaseUser> LoginWithMail(@Body User user);

    @POST("login-with-phone")
    Observable<BaseUser> LoginWithPhone(@Body User user);

    @POST("sign-in-check")
    Observable<BaseUser> SignInCheck(@Body User user);

    @POST("sign-in")
    Observable<BaseUser> SignIn(@Body User user);

}
