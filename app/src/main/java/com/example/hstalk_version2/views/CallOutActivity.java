package com.example.hstalk_version2.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.adapter.Adapter_List_Chat;
import com.example.hstalk_version2.model.Chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.stringee.call.StringeeCall2;
import com.stringee.common.StringeeAudioManager;
import com.stringee.listener.StatusListener;
import com.stringee.video.StringeeVideoTrack;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
    FirebaseFirestore database;
    ArrayList<Chat> ds = new ArrayList<>();
    Adapter_List_Chat adapter_list_chat;
    RecyclerView listchat;
    EditText edt_chat;
    String id_rom_chat = "";
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
        listchat = findViewById(R.id.list_chat);
        edt_chat = findViewById(R.id.chat);
        from =getIntent().getStringExtra("from");
        to =getIntent().getStringExtra("to");
        name = getIntent().getStringExtra("name");
        //Kết nối với database hiện tại
        database = FirebaseFirestore.getInstance();
        //ReadDatabaseOnTime();

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
        edt_chat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Log.e("DEOHIEU", "onEditorAction: " + i);
                if(i == EditorInfo.IME_ACTION_SEND)
                {
                    if(!edt_chat.getText().toString().equals(""))
                    {
                        addChat();
                    }
                    return true;
                }else {
                    Log.e("DEOHIEU", "onEditorAction: ");
                }
                return false;
            }
        });

        getData();

    }
    public void getData()
    {
        adapter_list_chat = new Adapter_List_Chat(this,ds);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        listchat.setLayoutManager(linearLayoutManager);
        listchat.setAdapter(adapter_list_chat);
    }
    private void ReadDatabaseRealTime() {
        database.collection(id_rom_chat)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("LoiRealtime", "Listen failed.", error);
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges())
                        {
                            switch (dc.getType())
                            {
                                case ADDED:
                                    ds.add(dc.getDocument().toObject(Chat.class));
                                    adapter_list_chat.notifyDataSetChanged();
                                    listchat.smoothScrollToPosition(ds.size()-1);
                                    break;
                            }
                        }
                    }
                });
    }
    private void addChat()
    {
        if(id_rom_chat.equals("")) return;
        String noidung = edt_chat.getText().toString();
        String ten = getSharedPreferences("HocVien",MODE_PRIVATE).getString("name","LAN ANH");
        String id = UUID.randomUUID().toString();
        Chat chat = new Chat(id,ten,noidung);
        Map mapchat = chat.ToMap();
        database.collection(id_rom_chat).document(id).set(mapchat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("CHAT", "onSuccess: ");
                        edt_chat.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CallOutActivity.this, "That bai", Toast.LENGTH_SHORT).show();
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

                id_rom_chat = stringeeCall22.getCallId();
                ReadDatabaseRealTime();

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

    @Override
    public void finish() {
        super.finish();

    }
}