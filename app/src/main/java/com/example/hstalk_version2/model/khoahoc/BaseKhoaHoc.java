package com.example.hstalk_version2.model.khoahoc;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class BaseKhoaHoc implements Serializable {
    private CapHoc CapHoc;
    private Integer tiendo;

    public BaseKhoaHoc() {
    }

    public BaseKhoaHoc(com.example.hstalk_version2.model.khoahoc.CapHoc capHoc, Integer tiendo) {
        CapHoc = capHoc;
        this.tiendo = tiendo;
    }

    public com.example.hstalk_version2.model.khoahoc.CapHoc getCapHoc() {
        return CapHoc;
    }

    public void setCapHoc(com.example.hstalk_version2.model.khoahoc.CapHoc capHoc) {
        CapHoc = capHoc;
    }

    public int getTiendo() {
        return tiendo;
    }

    public void setTiendo(Integer tiendo) {
        this.tiendo = tiendo;
    }
    @BindingAdapter("urlbackground")
    public static void loadImage(ImageView imageView, String avt) {
        if(avt != null && !avt.equals(""))
        {
//           Cái ngành l này địt mẹ nó , bố mày mà lấy vợ giàu có cái l ngành
            Glide.with(imageView.getContext()).load(avt).centerCrop()
                    .into(imageView);
        }

    }
}
