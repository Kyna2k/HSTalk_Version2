package com.example.hstalk_version2.ultis;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.hstalk_version2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UI_Feature {
    public enum REGEX{
        number,

    }
    public static void show_hide_icon_edit(Boolean check,TextInputLayout edt_layout, TextInputEditText edt, int icon)
    {
        if(check)
        {
            edt_layout.setStartIconDrawable(null);
        }else {
            if(!edt.getText().toString().equals("")) return;
            edt_layout.setStartIconDrawable(icon);
        }
    }
    public static String getTime(String date)
    {
        final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date _date = inputFormat.parse(date);
            String niceDateStr = DateUtils.getRelativeTimeSpanString(_date.getTime() , Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
            return  niceDateStr;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
