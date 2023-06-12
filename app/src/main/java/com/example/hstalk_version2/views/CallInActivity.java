package com.example.hstalk_version2.views;

import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class CallInActivity extends AppCompatActivity {
    private StringeeCall2 stringeeCall2;
    FrameLayout v_remote,v_local;
    TextView trangthai;
    private StringeeCall2.SignalingState state;
    private StringeeAudioManager audioManager;
    private StringeeCall2.MediaState mMediaState;
    private LinearLayout layout_wait,layout_accept;
    private boolean check_video = true;
    private ImageView mic,doicam,tatcam;
    ImageView huy,traloi,kethuc;
    Ringtone r;
    FirebaseFirestore database;
    ArrayList<Chat> ds = new ArrayList<>();
    Adapter_List_Chat adapter_list_chat;
    RecyclerView listchat;
    EditText edt_chat;
    TextView noidung_speak;
    Button btn_speak;
    String id_rom_chat = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_in);
        v_remote = findViewById(R.id.video1_vao);
        v_local = findViewById(R.id.video2_vao);
        trangthai = findViewById(R.id.trangthai);
        huy = findViewById(R.id.huy);
        doicam = findViewById(R.id.doicam);
        mic = findViewById(R.id.mic);
        tatcam = findViewById(R.id.tatcam);
        kethuc = findViewById(R.id.kethuc);
        traloi = findViewById(R.id.traloi);
        listchat = findViewById(R.id.list_chat);
        edt_chat = findViewById(R.id.chat);
        layout_wait = findViewById(R.id.layout_wait);
        layout_accept = findViewById(R.id.layout_accept);
        noidung_speak = findViewById(R.id.noidung_speak);
        btn_speak = findViewById(R.id.btn_speak);
        //Kết nối với database hiện tại
        database = FirebaseFirestore.getInstance();
        //ReadDatabaseOnTime();
        
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Say something…");

                try {
                    activityResultLauncher.launch(intent);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(CallInActivity.this, "Loi roi", Toast.LENGTH_SHORT).show();
                }

            }
        });

        traloi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stringeeCall2 != null)
                {
                    stringeeCall2.answer(null);
                    layout_wait.setVisibility(View.GONE);
                    layout_accept.setVisibility(View.VISIBLE);
                    r.stop();
                }
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stringeeCall2 != null)
                {

                    stringeeCall2.reject(new StatusListener() {
                        @Override
                        public void onSuccess() {
                            audioManager.stop();

                        }
                    });
                    finish();
                }
            }
        });
        kethuc.setOnClickListener(new View.OnClickListener() {
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
        nhancuocgoi();
        ReadDatabaseRealTime();
        getData();
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if(result.getData() != null){
                            ArrayList<String> data = result.getData().getStringArrayListExtra(
                                    RecognizerIntent.EXTRA_RESULTS);
                            noidung_speak.setText(Objects.requireNonNull(data).get(0));
                        }else{
                            Toast.makeText(CallInActivity.this, "Oops! Dzui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
    private void nhancuocgoi() {
        String call_id = getIntent().getStringExtra("call_id");
        stringeeCall2 = MainActivity.callMap.get(call_id);
        id_rom_chat = call_id;
        assert stringeeCall2 != null;
        stringeeCall2.setCallListener(new StringeeCall2.StringeeCallListener() {
            @Override
            public void onSignalingStateChange(StringeeCall2 stringeeCall2, StringeeCall2.SignalingState signalingState, String s, int i, String s1) {
                runOnUiThread(() -> {
                    Log.d("Hehe", "onSignalingStateChange: " + signalingState);
                    state = signalingState;
                    if (signalingState == StringeeCall2.SignalingState.ANSWERED) {
                        trangthai.setText("Starting");
                        if (mMediaState == StringeeCall2.MediaState.CONNECTED) {
                            trangthai.setText("Started");
                        }
                    } else if (signalingState == StringeeCall2.SignalingState.ENDED) {
                        stringeeCall2.hangup(null);
                        audioManager.stop();
                        finish();
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
        stringeeCall2.ringing(new StatusListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CallInActivity.this, "Đang Đổ chuông", Toast.LENGTH_SHORT).show();
                        r = RingtoneManager.getRingtone(CallInActivity.this,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
                        r.play();
                    }
                });
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
        database.collection(stringeeCall2.getCallId())
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
                        Toast.makeText(CallInActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void finish() {
        super.finish();
        r.stop();
    }
}