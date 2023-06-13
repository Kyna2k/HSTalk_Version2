package com.example.hstalk_version2.services;

import com.example.hstalk_version2.model.baihoc.BaseBaiHoc;
import com.example.hstalk_version2.model.baiviet.BaiViet;
import com.example.hstalk_version2.model.baiviet.ResBaiViet;
import com.example.hstalk_version2.model.baiviet.ResBaiViet2;
import com.example.hstalk_version2.model.khoahoc.CapHoc;
import com.example.hstalk_version2.model.khoahoc.ResultKhoaHoc;
import com.example.hstalk_version2.model.notification.ResNotification;
import com.example.hstalk_version2.model.trangthai.BaseTrangThai;
import com.example.hstalk_version2.model.trangthai.TrangThaiRe;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @GET("danh-sach-bai-viet")
    Observable<ResBaiViet> getdanhsachbaiviet();

    @POST("danh-sach-bai-cua-toi")
    Observable<ResBaiViet> getdanhsachbaivietcuatoi(@Body User user);

    @Multipart
    @POST("add-bai-viet")
    Observable<ResBaiViet2> addbaiviet(@Part("Mauser")RequestBody Mauser, @Part("Noidung") RequestBody Noidung, @Part MultipartBody.Part Hinhanh);

    @POST("get-thong-tin")
    Observable<BaseUser> getthongtin(@Body User user);

    @Multipart
    @POST("cap-nhat-user")
    Observable<BaseUser> capnhatthongtin(@Part("Mauser")RequestBody Mauser,@Part("Tenhocvien") RequestBody Tenhocvien, @Part("Mota") RequestBody Mota,
                                         @Part("Gioitinh") RequestBody Gioitinh,@Part MultipartBody.Part Avt);


    @POST("danh-sach-noti")
    Observable<ResNotification> danhsachnoti(@Body User user);
}
