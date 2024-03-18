package com.wwb.laobiao.Search.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.yangna.lbdsp.R;

//import com.qzy.laobiao.R;

public class Searchlayout extends LinearLayout{
    private LayoutInflater inflater;

    public Searchlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public Searchlayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_test1, this);
    }
    public Searchlayout(Context context) {
        super(context);
        init(context);
    }
}
