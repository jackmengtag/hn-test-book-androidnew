package com.wwb.laobiao.address.model;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.wwb.laobiao.hongbao.widget.AvatarImageView;

////import com.qzy.laobiao.R;
////import com.qzy.laobiao.hongbao.widget.AvatarImageView;

public class LinearLayoutAdress extends LinearLayout {
    private LayoutInflater inflater;
    private ImageView textView;
    private AvatarImageView headimage;
    private RelativeLayout rlay;

    public LinearLayoutAdress(Context context) {
        super(context);
        init(context,null);

    }
    public LinearLayoutAdress(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.layout_adress, this);
//        headimage=findViewById(R.id.aiv_left);
//        textView=findViewById(R.id.textView0);
//        rlay=findViewById(R.id.iv_rlay);
//    <RelativeLayout
//        android:id="@+id/iv_rlay"
    }
    public LinearLayoutAdress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutAdress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
}
