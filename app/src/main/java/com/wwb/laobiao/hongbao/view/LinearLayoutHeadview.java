package com.wwb.laobiao.hongbao.view;

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
import com.yangna.lbdsp.R;

////import com.qzy.laobiao.R;
////import com.qzy.laobiao.hongbao.widget.AvatarImageView;

public class LinearLayoutHeadview extends LinearLayout {
    private LayoutInflater inflater;
    private ImageView textView;
    private AvatarImageView headimage;
    private RelativeLayout rlay;

    public LinearLayoutHeadview(Context context) {
        super(context);
        init(context,null);

    }
    public LinearLayoutHeadview(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_ranking_headview, this);
        headimage=findViewById(R.id.aiv_left);
        textView=findViewById(R.id.textView0);
//        rlay=findViewById(R.id.iv_rlay);
//    <RelativeLayout
//        android:id="@+id/iv_rlay"
    }
    public LinearLayoutHeadview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutHeadview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    public void setImageResource(int ImageResource) {
        headimage.setImageResource(ImageResource);
    }

    public void setImageBackground(int Resource) {
        textView.setBackgroundResource(Resource);
    }
}
