package com.example.hstalk_version2.views;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.model.trangthai.BaseTrangThai;
import com.example.hstalk_version2.model.trangthai.TrangThaiRe;
import com.example.hstalk_version2.model.user.BaseUser;
import com.example.hstalk_version2.model.user.User;
import com.example.hstalk_version2.services.API;
import com.example.hstalk_version2.ultis.Loading;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class KiemTraBaiTapActivity extends AppCompatActivity implements ImageAnalysis.Analyzer{
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    private ImageCapture imageCapture;
    Camera camera;
    ImageView chuphinh,reload,layhinh;
    ImageView switch_camera;
    CameraControl cameraControl;
    CameraInfo cameraInfo;
    SeekBar zoom;
    LinearLayout viewchup;
    TextView noidung,countdown,ketqua,debai;
    Boolean check = false;
    String uri;
    ImageLabeler labeler;
    TextToSpeech speak;
    ImageProxy get;
    Loading loading;
    API api;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiem_tra_bai_tap);
        api = new API();
        loading = new Loading();
        noidung = findViewById(R.id.noidung);
        countdown = findViewById(R.id.countdown);
        debai = findViewById(R.id.debai);
        debai.setText("Đề bài kiểm tra là: " + getIntent().getExtras().getString("debai"));
        //Image
        LocalModel localModel = new LocalModel.Builder()
                .setAssetFilePath("model.tflite")
                .build();
        CustomImageLabelerOptions customImageLabelerOptions =
                new CustomImageLabelerOptions.Builder(localModel)
                        .setConfidenceThreshold(0.45f)
                        .setMaxResultCount(1)

                        .build();
        labeler = ImageLabeling.getClient(customImageLabelerOptions);
        //ánh xạ các các thành phần
        previewView = findViewById(R.id.previewView);
        zoom = findViewById(R.id.zoom);
        switch_camera = findViewById(R.id.switch_camera);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height_screen = displayMetrics.heightPixels;
//        int width_screen = displayMetrics.widthPixels; //4 : 3 //16 : 9
//        int height_new = (width_screen/9)*16;
//        previewView.setLayoutParams(new ConstraintLayout.LayoutParams(width_screen,height_new));
        start(check);
        zoom.setMax(100);
        zoom.setProgress(0);




        switch_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check)
                {
                    check = false;
                    start(check);
                }else {
                    check = true;
                    start(check);
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void start(boolean check_laycamera)
    {
        //Ở đây chúng ta sẽ ràng buộc luồng xử lý vào vòng đời camera, và cameraX sẽ tự hiểu vòng đòi của chính thứ chứa nó ở đây là vòng đời hàm main
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                //Hàm có thể coi như là setview từ camera lên previewView
                bindPreview(cameraProvider,check_laycamera);
            } catch (ExecutionException | InterruptedException e) {
            }
        }, ContextCompat.getMainExecutor(this));
    }
    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.R)
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider, boolean check_laycamera) {
        //Hủy mọi liên kết vòng đời của cameraX trước khi liên kết lại
        cameraProvider.unbindAll();
        //Build ImageCapture
        imageCapture = new ImageCapture.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                //tốc độ luồng xử lý và chất lượng hiình ảnh
                .build();
        //Phân tích và xử lý hình ảnh nếu muốn dùng thì có thể đọc thêm doc của google về cameraX
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        //Build preview hình ảnh
        Preview preview = new Preview.Builder()
                .build();
        CameraSelector cameraSelector = null;
        if(check_laycamera){
            cameraSelector= new CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build();
        }else {
            cameraSelector= new CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                    .build();
        }


        //kết nối Preview vào PreviewView
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(KiemTraBaiTapActivity.this), this);




        //cameraProvider.bindToLifecycle sẽ trả về một đối tượng camera và đồng thời sẽ liên kết vòng đợi camera để xử dụng
        //ở đây họ dùng thuật ngữ use_case, nhưng bạn có trẻ truyền đối số theo mẫu dưới là đc
        camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector,preview,imageCapture,imageAnalysis);

        //Ở camera chúng ta sẽ có 2 đối tượng để xử lý ảnh đầu ra và vào đồng thời các tính năng mặc định như set độ phơi sáng, bật đèn flash, zoom ra zoom vào
        cameraControl = camera.getCameraControl();
        cameraInfo = camera.getCameraInfo();


        zoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // ở đây tôi dùng seekbar để zoom ra zoom vào camera, hàm setLinearZoom nhận giá trị  từ 0-1
                cameraControl.setLinearZoom(i/100f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        get = imageProxy;
        @SuppressLint("UnsafeOptInUsageError") Image mediaImage = imageProxy.getImage();
        if(mediaImage != null)
        {
            InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());

            labeler.process(image).addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                        @Override
                        public void onSuccess(List<ImageLabel> imageLabels) {
                            time(time_yes);
                            time_yes = false;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(imageLabels.size() > 0)
                                    {
                                        for(ImageLabel im : imageLabels) {
                                            noidung.setText(im.getText());
                                            Log.e("nganhlon_custom", im.getText());
                                            String debai = getIntent().getExtras().getString("debai");
                                            int x = 2;
                                            if(noidung.getText().toString().equalsIgnoreCase(debai))
                                            {

                                                noidung.setText( "Làm tốt lắm");
                                                loading.LoadingShow(KiemTraBaiTapActivity.this,"Lưu điểm");
                                                //chua fix diem
                                                TaiDiemlen(100);
                                            }
                                            break;
                                        }
                                    }else {
                                        noidung.setText("Không tìm thấy");
                                    }
                                    time_yes = true;
                                    imageProxy.close();
                                }
                            }, 3000);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("nganhlon_custom","loi roi   " + e.toString());
                            imageProxy.close();
                            noidung.setText("");
                        }
                    });


        }
    }
    Boolean time_yes = false;
    int z = 4;
    public void time(boolean yes)
    {
        if(yes)
        {
            z=4;
        }
        CountDownTimer countDownTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                z = z- 1;
                countdown.setText("Hãy giữ kí hiệu trong: " + z+" giây");
            }

            @Override
            public void onFinish() {

            }
        }.start();


    }
    private void TaiDiemlen(int diem)
    {
        TrangThaiRe trangThaiRe = new TrangThaiRe();
        trangThaiRe.setTrangThai(1);
        trangThaiRe.setDiem(diem);
        trangThaiRe.setMauser(getSharedPreferences("HocVien",MODE_PRIVATE).getString("_id",""));
        trangThaiRe.setMabaihoc(getIntent().getStringExtra("mabaihoc"));
        trangThaiRe.setCaphoc(getIntent().getStringExtra("caphoc"));
        new CompositeDisposable().add(api.getAPI().addtranthaihoc(trangThaiRe)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::dangnhap, this::loidangnhap)
        );
    }

    private void loidangnhap(Throwable throwable)
    {
        loading.LoadingDismi();
        Log.e("TAG", "loidangnhap: ",throwable );
    }

    private void dangnhap(BaseTrangThai baseUser) {
        if(baseUser.getResult() != null)
        {
            Toast.makeText(this, "Bạn đã kiểm tra", Toast.LENGTH_SHORT).show();
            loading.LoadingDismi();

        }else {
            loading.LoadingDismi();
        }
    }

}