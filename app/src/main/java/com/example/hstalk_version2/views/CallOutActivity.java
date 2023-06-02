package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hstalk_version2.R;
import com.stringee.call.StringeeCall2;
import com.stringee.common.StringeeAudioManager;
import com.stringee.listener.StatusListener;
import com.stringee.video.StringeeVideoTrack;

import org.json.JSONObject;

import java.util.Set;

public class CallOutActivity extends AppCompatActivity {
    private StringeeCall2 stringeeCall2;
    private StringeeCall2.SignalingState state;
    FrameLayout v_remote,v_local;
    TextView trangthai;
    ImageView huy;
    private boolean check_video = true;
    private String from;
    private String to;
    private String token_device_to,name;
    private StringeeCall2.MediaState mMediaState;
    private ImageView mic,doicam,tatcam;
    private StringeeAudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_out);
        v_remote = findViewById(R.id.video1);
        v_local = findViewById(R.id.video2);
        trangthai = findViewById(R.id.trangthai);
        huy = findViewById(R.id.huy);
        doicam = findViewById(R.id.doicam);
        mic = findViewById(R.id.mic);
        tatcam = findViewById(R.id.tatcam);
        from =getIntent().getStringExtra("from");
        to =getIntent().getStringExtra("to");
        name = getIntent().getStringExtra("name");
        goithoi();
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stringeeCall2 != null)
                {
                    stringeeCall2.hangup(new StatusListener() {
                        @Override
                        public void onSuccess() {
                            audioManager.stop();

                        }
                    });
                    finish();
                }

            }
        });
        doicam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringeeCall2.switchCamera(null);
            }
        });
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stringeeCall2.isMute())
                {
                    stringeeCall2.mute(false);
                    mic.setImageResource(getResources().getIdentifier("mic","mipmap",getPackageName()));
                }else {
                    stringeeCall2.mute(true);
                    mic.setImageResource(getResources().getIdentifier("unmic","mipmap",getPackageName()));

                }
            }
        });
        tatcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_video)
                {
                    stringeeCall2.enableVideo(false);
                    tatcam.setImageResource(getResources().getIdentifier("unvideo_came","mipmap",getPackageName()));
                    check_video = false;
                }else {
                    stringeeCall2.enableVideo(true);
                    tatcam.setImageResource(getResources().getIdentifier("video_came","mipmap",getPackageName()));
                    check_video = true;
                }
            }
        });

    }
    private void goithoi()
    {
        stringeeCall2 = new StringeeCall2(MainActivity.stringeeClient,from,to);

        stringeeCall2.setCallListener(new StringeeCall2.StringeeCallListener() {
            @Override
            public void onSignalingStateChange(StringeeCall2 stringeeCall2, StringeeCall2.SignalingState signalingState, String s, int i, String s1) {
                runOnUiThread(() -> {
                    Log.d("hehe", "onSignalingStateChange: " + signalingState);
                    state = signalingState;
                    switch (signalingState) {
                        case CALLING:
                            trangthai.setText("Outgoing call");
                            break;
                        case RINGING:
                            trangthai.setText("Ringing");
                            break;
                        case ANSWERED:
                            trangthai.setText("Starting");
                            if (mMediaState == StringeeCall2.MediaState.CONNECTED) {
                                trangthai.setText("Started");
                            }
                            break;
                        case BUSY:
                            trangthai.setText("Busy");
                            stringeeCall2.hangup(null);
                            audioManager.stop();
                            finish();
                            break;
                        case ENDED:
                            trangthai.setText("Ended");
                            stringeeCall2.hangup(null);
                            audioManager.stop();
                            finish();
                            break;
                    }
                });
            }

            @Override
            public void onError(StringeeCall2 stringeeCall2, int i, String s) {

            }

            @Override
            public void onHandledOnAnotherDevice(StringeeCall2 stringeeCall2, StringeeCall2.SignalingState signalingState, String s) {

            }

            @Override
            public void onMediaStateChange(StringeeCall2 stringeeCall2, StringeeCall2.MediaState mediaState) {
                runOnUiThread(() -> {
                    Log.d("trang thai", "onMediaStateChange: " + mediaState);
                    mMediaState = mediaState;
                    if (mediaState == StringeeCall2.MediaState.CONNECTED) {
                        if (state == StringeeCall2.SignalingState.ANSWERED) {
                            trangthai.setText("");
                        }
                    } else {
                        trangthai.setText("Reconnecting...");
                    }
                });
            }

            @Override
            public void onLocalStream(StringeeCall2 stringeeCall22) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(stringeeCall22.isVideoCall())
                        {
                            v_local.removeAllViews();
                            v_local.addView(stringeeCall22.getLocalView());
                            stringeeCall22.renderLocalView(true);
                        }

                    }
                });
            }

            @Override
            public void onRemoteStream(StringeeCall2 stringeeCall22) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(stringeeCall22.isVideoCall())
                        {
                            v_remote.removeAllViews();
                            v_remote.addView(stringeeCall22.getRemoteView());
                            stringeeCall22.renderRemoteView(false);
                        }

                    }
                });
            }

            @Override
            public void onVideoTrackAdded(StringeeVideoTrack stringeeVideoTrack) {

            }

            @Override
            public void onVideoTrackRemoved(StringeeVideoTrack stringeeVideoTrack) {

            }

            @Override
            public void onCallInfo(StringeeCall2 stringeeCall2, JSONObject jsonObject) {
                runOnUiThread(() -> Log.d("info", "onCallInfo: " + jsonObject.toString()));

            }

            @Override
            public void onTrackMediaStateChange(String s, StringeeVideoTrack.MediaType mediaType, boolean b) {

            }
        });
        audioManager = StringeeAudioManager.create(this);
        audioManager.start(new StringeeAudioManager.AudioManagerEvents() {
            @Override
            public void onAudioDeviceChanged(StringeeAudioManager.AudioDevice audioDevice, Set<StringeeAudioManager.AudioDevice> set) {

            }
        });
        stringeeCall2.setVideoCall(true);
        audioManager.setSpeakerphoneOn(stringeeCall2.isVideoCall());
        stringeeCall2.setQuality(StringeeCall2.VideoQuality.QUALITY_720P);
        stringeeCall2.makeCall(null);
    }
}