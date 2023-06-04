package com.example.hstalk_version2.services;

import com.example.hstalk_version2.model.baihoc.BaseBaiHoc;
import com.example.hstalk_version2.model.khoahoc.CapHoc;
import com.example.hstalk_version2.model.khoahoc.ResultKhoaHoc;
import com.example.hstalk_version2.model.trangthai.BaseTrangThai;
import com.example.hstalk_version2.model.trangthai.TrangThaiRe;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API_SERVICES {
    public static final String BASE_Service = "http://192.168.0.105:3000/api/";
    //public static final String BASE_Service = "https://kynalab.com/api/";

    @POST("login-with-email")
    Observable<BaseUser> LoginWithMail(@Body User user);

    @POST("login-with-phone")
    Observable<BaseUser> LoginWithPhone(@Body User user);

    @POST("sign-in-check")
    Observable<BaseUser> SignInCheck(@Body User user);

    @POST("sign-in")
    Observable<BaseUser> SignIn(@Body User user);

    @GET("get-ds-hoc-vien")
    Observable<BaseUser> GetDSHocVien();

    @POST("send-otp-code")
    Observable<BaseUser> sendOtpCode(@Body User user);

    @POST("check-otp-code")
    Observable<BaseUser> checkOTP(@Body User user);

    @POST("reset-password")
    Observable<BaseUser> resetpass(@Body User user);

    @POST("get-danh-sach-cap-hoc")
    Observable<ResultKhoaHoc> danhsachcaphoc(@Body User user);

    @POST("get-danh-sach-bai-hoc-theo-cap")
    Observable<BaseBaiHoc> danhsachbaihoctheocap(@Body CapHoc capHoc);
    @POST("add-trang-thai-hoc")
    Observable<BaseTrangThai> addtranthaihoc(@Body TrangThaiRe trangThaiRe);

}
