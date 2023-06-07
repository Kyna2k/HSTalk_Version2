package com.example.hstalk_version2.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hstalk_version2.R;
import com.example.hstalk_version2.adapter.Adapter_List_Comment;
import com.example.hstalk_version2.databinding.ActivityBinhLuanBinding;
import com.example.hstalk_version2.model.comment.BaseComment;
import com.example.hstalk_version2.model.comment.Comment;
import com.example.hstalk_version2.model.comment.ResComment;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class BinhLuanActivity extends AppCompatActivity {
    private ActivityBinhLuanBinding binding;
    private Adapter_List_Comment adapter_list_comment;
    private ArrayList<BaseComment> ds = new ArrayList<>();
    final String BASE_URL_Local = "http://192.168.0.104:3000";
    final String Base_URL = "https://kynalab.com";
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Base_URL);
        } catch (URISyntaxException e) {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBinhLuanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mSocket.connect();
        mSocket.on("addComment",onNewComment);
        mSocket.emit("GETLISTBAIVIET",getIntent().getExtras().getString("id_baiviet"));
        mSocket.on("GETLISTBAIVIET",getList);
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noidung = binding.noidung.getText().toString();
                Comment comment = new Comment();
                comment.setMabaiviet(getIntent().getExtras().getString("id_baiviet"));
                comment.setNoidung(noidung);
                comment.setMauser(getSharedPreferences("HocVien",MODE_PRIVATE).getString("_id","LAN ANH"));
                Gson gson = new Gson();
                mSocket.emit("addComment",gson.toJson(comment));
                binding.noidung.setText("");
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private Emitter.Listener getList  = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            BinhLuanActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i("socket.io", "run: " + data.toString());
                    Gson gson = new Gson();
                    ResComment resComment = gson.fromJson(data.toString(),ResComment.class);
                    Log.i("socket.io", "run: " + resComment.getResult());

                    ds = resComment.getResult();
                    getData();
                }
            });
        }
    };
    private Emitter.Listener onNewComment  = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            BinhLuanActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i("socket.io", "run: " + data.toString());
                    Gson gson = new Gson();
                    BaseComment resComment = gson.fromJson(data.toString(),BaseComment.class);
                    ds.add(resComment);
                    adapter_list_comment.notifyDataSetChanged();
                }
            });
        }
    };
    public void getData()
    {
        if(ds.size() != 0)
        {
            adapter_list_comment = new Adapter_List_Comment(ds,this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            binding.listBinhluan.setLayoutManager(linearLayoutManager);
            binding.listBinhluan.setAdapter(adapter_list_comment);
            binding.empty.setVisibility(View.GONE);
            binding.listBinhluan.setVisibility(View.VISIBLE);
        }else {
            binding.empty.setVisibility(View.VISIBLE);
            binding.listBinhluan.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("GETLISTBAIVIET", getList);
        mSocket.off("addComment", onNewComment);
    }
}