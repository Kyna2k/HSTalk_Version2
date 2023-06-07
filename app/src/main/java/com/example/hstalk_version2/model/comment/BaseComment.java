package com.example.hstalk_version2.model.comment;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.hstalk_version2.R;
import com.example.hstalk_version2.model.user.User;

import java.io.Serializable;

public class BaseComment implements Serializable {
    private User user;
    private Comment comment;

    public BaseComment() {
    }

    public BaseComment(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
    @BindingAdapter("avatar")
    public static void loadImage(ImageView imageView, String avt) {
        if(avt != null && !avt.equals(""))
        {
//           Cái ngành l này địt mẹ nó , bố mày mà lấy vợ giàu có cái l ngành
            Glide.with(imageView.getContext()).load(avt).centerCrop().circleCrop()
                    .placeholder(R.drawable.avatar_df)
                    .into(imageView);
        }

    }
    @BindingAdapter("hinhanh")
    public static void hinhanh(ImageView imageView, String avt) {
        if(avt != null && !avt.equals(""))
        {
//           Cái ngành l này địt mẹ nó , bố mày mà lấy vợ giàu có cái l ngành
            Glide.with(imageView.getContext()).load(avt)
                    .placeholder(R.drawable.avatar_df)
                    .into(imageView);
        }

    }
}
