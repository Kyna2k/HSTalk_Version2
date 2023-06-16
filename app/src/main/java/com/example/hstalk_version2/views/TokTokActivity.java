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
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hstalk_version2.KetQuaTokTokActivity;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.model.trangthai.BaseTrangThai;
import com.example.hstalk_version2.model.trangthai.TrangThaiRe;
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

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TokTokActivity extends AppCompatActivity implements ImageAnalysis.Analyzer{
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    private ImageCapture imageCapture;
    Camera camera;
    ImageView chuphinh,reload,layhinh,hinh1,hinh2,hinh3;
    ImageView switch_camera;
    CameraControl cameraControl;
    CameraInfo cameraInfo;
    SeekBar zoom;
    LinearLayout viewchup;
    TextView countdown,ketqua,debai,countdown_chuphinh;
    Boolean check = false;
    String uri1,uri2,uri3;
    ImageLabeler labeler;
    TextToSpeech speak;
    ImageProxy get;
    Loading loading;
    API api;
    CountDownTimer countDownTimer2;
    VideoCapture videoCapture;
    Boolean check_xem_the_nao = true;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tok_tok);
        api = new API();
        loading = new Loading();
        countdown = findViewById(R.id.countdown);
        //Image
        LocalModel localModel = new LocalModel.Builder()
                .setAssetFilePath("model2.tflite")
                .build();
        chuphinh = findViewById(R.id.chuphinh);
        countdown_chuphinh = findViewById(R.id.countdown_chuphinh);
        CustomImageLabelerOptions customImageLabelerOptions =
                new CustomImageLabelerOptions.Builder(localModel)
                        .setConfidenceThreshold(0.8f)
                        .setMaxResultCount(3)

                        .build();
        labeler = ImageLabeling.getClient(customImageLabelerOptions);
        //ánh xạ các các thành phần
        previewView = findViewById(R.id.previewView);
        hinh1 = findViewById(R.id.hinh1);
        hinh2 = findViewById(R.id.hinh2);
        hinh3 = findViewById(R.id.hinh3);
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
        chuphinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("hinh1",uri1);
                bundle.putString("hinh2",uri2);
                bundle.putString("hinh3",uri3);
                Intent intent = new Intent(TokTokActivity.this, KetQuaTokTokActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
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
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(TokTokActivity.this), this);




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
    public void chuphinh(String tenanh)
    {
        countDownTimer2.onFinish();

        //Tạo nơi mà ảnh sẽ lưu, ở đây tôi chọn vùng cache để lưu
        File z = new File(getCacheDir(),"images");
        z.mkdirs();
        File luu = new File(z, tenanh+".jpg");
        ImageCapture.OutputFileOptions outputFileOptions = null;
        if(!check)
        {
            ImageCapture.Metadata meta = new  ImageCapture.Metadata();
            meta.setReversedHorizontal(true);
            outputFileOptions = new ImageCapture.OutputFileOptions.Builder(luu).setMetadata(meta).build();

        }else {
            outputFileOptions = new ImageCapture.OutputFileOptions.Builder(luu).build();
        }
        //Chụp ảnh
        //ContextCompat.getMainExecutor(MainActivity.this) là luồng xử lý ảnh ở dây tôi dùng trên luồng chính
        //takePicture có 2 phương thức, nếu bạn không truyền biến outputFileoption (Khởi tạo nơi lưu) thì mặt định nó sẽ lưu ở file cache =]]
        //Ừ thì cái outputFileOptions cũng lưu ở file cache

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(TokTokActivity.this), new ImageCapture.OnImageSavedCallback() {
            @SuppressLint("RestrictedApi")
            @Override
            //sau khi chụp thành công các sự kiện xảy ra, như kiểu nếu thành công bạn sẽ làm gì
            public void onImageSaved(@io.reactivex.annotations.NonNull ImageCapture.OutputFileResults outputFileResults) {
                //lấy uri nơi lưu hình


                try {
                    //sao khi chụp thành công tôi dùng khung hình lại  tạo hiệu ứng lấy ảnh
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    cameraProvider.unbindAll();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Toast.makeText(TokTokActivity.this, "Lưu hình thành công"  , Toast.LENGTH_SHORT).show();
                switch (tenanh){
                    case "hinh1" :
                        uri1 = luu.toString();
                        Glide.with(TokTokActivity.this).load(luu.toString()).diskCacheStrategy(DiskCacheStrategy.NONE )
                                .skipMemoryCache(true).into(hinh1);
                        break;
                    case "hinh2" :
                        uri2 = luu.toString();
                        Glide.with(TokTokActivity.this).load(luu.toString()).diskCacheStrategy(DiskCacheStrategy.NONE )
                                .skipMemoryCache(true).into(hinh2);

                        break;
                    case "hinh3" :
                        uri3  = luu.toString();
                        Glide.with(TokTokActivity.this).load(luu.toString()).diskCacheStrategy(DiskCacheStrategy.NONE )
                                .skipMemoryCache(true).into(hinh3);

                        break;
                }
                start(check);
                check_xem_the_nao = true;
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull ImageCaptureException exception) {

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
//                            for(ImageLabel im : imageLabels) {
//                                Log.e("nganhlon_custom", im.getText());
//
//                                int x = 2;
//                                if(im.getText().equalsIgnoreCase("E"))
//                                {
//                                    time(time_yes);
//                                    time_yes = false;
//                                    new Handler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if(imageLabels.size() > 0)
//                                            {
//                                                chuphinh("hinh1");
//                                            }else {
//                                            }
//                                            time_yes = true;
//                                            imageProxy.close();
//                                        }
//                                    }, 3000);
//
//                                }
//                                break;
//                            }
                            time(time_yes);
                            time_yes = false;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(check_xem_the_nao)
                                    {
                                        if(imageLabels.size() > 0)
                                        {
                                            for(ImageLabel im : imageLabels) {

                                                Log.e("nganhlon_custom", imageLabels.toString());

                                                int x = 2;
                                                if(imageLabels.size() > 0)
                                                {
                                                    if(im.getText().equalsIgnoreCase("I"))
                                                    {
                                                        check_xem_the_nao = false;
                                                        timechup(timechup);
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                chuphinh("hinh1");
                                                            }
                                                        },2000);
                                                        timechup = true;
                                                    }else if(im.getText().equalsIgnoreCase("LOVE"))
                                                    {
                                                        check_xem_the_nao = false;
                                                        timechup(timechup);
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                chuphinh("hinh2");
                                                            }
                                                        },2000);
                                                        timechup = true;
                                                    }
                                                    else if(im.getText().equalsIgnoreCase("Y"))
                                                    {
                                                        check_xem_the_nao = false;
                                                        timechup(timechup);
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                chuphinh("hinh3");
                                                            }
                                                        },2000);
                                                        timechup = true;
                                                    }




                                                }else {
                                                }
                                                break;
                                            }
                                        }else {

                                        }
                                        time_yes = true;
                                        imageProxy.close();
                                    }else {

                                    }

                                }
                            }, 3000);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("nganhlon_custom","loi roi   " + e.toString());
                            imageProxy.close();
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
    Boolean timechup = false;
    int x = 3;
    public void timechup(boolean yes)
    {
        if(yes)
        {
            x=3;
        }
        countDownTimer2 = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                x = x- 1;
                countdown_chuphinh.setText(x+"");
            }

            @Override
            public void onFinish() {
                countdown_chuphinh.setText("");
            }
        }.start();



    }

}