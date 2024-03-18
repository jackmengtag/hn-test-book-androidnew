package com.wwb.laobiao.Search.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.yangna.lbdsp.R;

//import com.qzy.laobiao.R;

public class LinearLayoutSearch extends LinearLayout {
    private LayoutInflater inflater;
    private TextView textView;
    private String str7="<font color=\"#FAF7F7\">这些小而美的景点虽然不大，但是花个两天，看看山，听听水，休闲度过两天时光，好好收拾心情面对下一周的工作还是非常合适惬意的！\\n\" +\n" +
            "            \"上一期节目，我们介绍了崇左大新骆越田园！一说到大新，可能一下能想到的是闻名天下的德天瀑布，是意境悠远的名仕田园！</font>" +
            "<font color=\"#F3CC21\">#广西旅游#崇左崇左崇左崇左崇左崇左</font>";

    public LinearLayoutSearch(Context context) {
        super(context);
        init(context,null);
    }
    public LinearLayoutSearch(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_search, this);
    }
    public LinearLayoutSearch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutSearch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
}
