package com.rs.doorbell.EditTextUtil;

import android.content.Context;
import android.util.AttributeSet;

import com.rs.doorbell.FontUtil.Font;

public class RalewayRegularEditText extends android.support.v7.widget.AppCompatEditText {
    public RalewayRegularEditText(Context context) {
        super(context);
        setFont(context);
    }

    public RalewayRegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public RalewayRegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.raleway_regular_font(context));
    }
}

