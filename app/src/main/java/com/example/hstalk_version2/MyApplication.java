package com.example.hstalk_version2;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "MyNotification";

    @Override
    public void onCreate() {
        super.onCreate();
        createpushNotification();
        FirebaseMessaging.getInstance().subscribeToTopic("news").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "Subscribed";
                if (!task.isSuccessful()) {
                    msg = "Subscribe failed";
                }
                Toast.makeText(MyApplication.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createpushNotification()
    {
        //Đoạn mã đăng ký NotificationChannel được lấy từ tài liệu chính thức của Google
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Tên của NotificationChannel cần đăng ký
            CharSequence name = getString(R.string.channel_name);
            //Mô tả của NotificationChannel
            String description = getString(R.string.channel_description);
            //Độ ưu tiên của Notification
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //Ta sẽ sử dụng RingtoneManager để lấy uri của âm thanh notification theo máy
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            //Tạo thêm một audioAttributes
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            //Đăng ký NotificationChannel
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            //Set sound cho notifcation
            channel.setSound(uri,audioAttributes);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}
