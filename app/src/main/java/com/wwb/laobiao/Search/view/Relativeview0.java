package com.wwb.laobiao.Search.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import com.yangna.lbdsp.R;

//import com.qzy.laobiao.R;

public class Relativeview0 extends RelativeLayout {
    private LayoutInflater inflater;

    public Relativeview0(Context context) {
        super(context);
        initview(context);
    }
    private void initview(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.relativeview_0, this);
    }
    public Relativeview0(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    public Relativeview0(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Relativeview0(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initview(context);
    }
}
