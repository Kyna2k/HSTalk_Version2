package com.example.hstalk_version2.ultis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.hstalk_version2.R;


public class Loading {
    private  AlertDialog alertDialog_loading;

    public  void LoadingShow(Context context, String noidung)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.dialog_loading,null);
        builder.setView(view);
        ImageView icon = view.findViewById(R.id.load_toktok);
        TextView noidung_ne = view.findViewById(R.id.noidung);
        noidung_ne.setText(noidung);
        Glide.with(context).load(R.mipmap.ic_loading).into(icon);
        alertDialog_loading= builder.create();
        alertDialog_loading.setCancelable(false);
        alertDialog_loading.setCanceledOnTouchOutside(false);
        alertDialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog_loading.show();
    }
    public  void LoadingDismi()
    {
        alertDialog_loading.dismiss();
    }
}
