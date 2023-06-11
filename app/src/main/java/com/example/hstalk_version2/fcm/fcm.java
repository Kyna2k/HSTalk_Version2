package com.example.hstalk_version2.fcm;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.hstalk_version2.MyApplication;
import com.example.hstalk_version2.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import io.reactivex.annotations.NonNull;

public class fcm extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            Log.d("data", "Message Notification Body: " + remoteMessage.getData());
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            NotificationChannel notificationChannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                notificationChannel = new NotificationChannel("notification", "Message Notification", NotificationManager.IMPORTANCE_HIGH);
//                notificationChannel.setSound(uri,audioAttributes);
//                getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
                Notification.Builder notification = new Notification.Builder(this, MyApplication.CHANNEL_ID)
                        .setSmallIcon(R.mipmap.logohstalk2)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                NotificationManagerCompat.from(this).notify((int) new Date().getTime(), notification.build());
            }


        }
        super.onMessageReceived(remoteMessage);
    }
}
