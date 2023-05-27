package com.example.hstalk_version2.ultis;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.hstalk_version2.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

}
