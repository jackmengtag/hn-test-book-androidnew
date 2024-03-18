package com.wwb.laobiao.hongbao.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wwb.laobiao.MyObservable.Rinker;
import com.wwb.laobiao.common.Utilswwb;
import com.wwb.laobiao.hongbao.bean.RankingModel;
import com.wwb.laobiao.hongbao.widget.AvatarImageView;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;

import java.io.IOException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.hongbao.bean.RankingModel;
//import com.qzy.laobiao.hongbao.widget.AvatarImageView;

public class LinearLayoutHeadviewnob2 extends LinearLayout {
    private LayoutInflater inflater;
    private ImageView textView;
    private AvatarImageView headimage;
    private RelativeLayout rlay;
    private TextView textViewpraisetotal;
    private Context context;

    public LinearLayoutHeadviewnob2(Context context) {
        super(context);
        init(context,null);

    }
    public LinearLayoutHeadviewnob2(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        this.context=context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_ranking_headview_nob1, this);
        headimage=findViewById(R.id.aiv_left);
        textView=findViewById(R.id.textView0);
        textViewpraisetotal=findViewById(R.id.textView7);//textView7  textViewpraisetotal
//        rlay=findViewById(R.id.iv_rlay);
//    <RelativeLayout
//        android:id="@+id/iv_rlay"
    }
    public LinearLayoutHeadviewnob2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutHeadviewnob2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    public void setImageResource(int ImageResource) {
        headimage.setImageResource(ImageResource);
    }
    public void setImageBitmap(String strURL) throws IOException {
//        headimage.setImageResource(ImageResource);
//        String strURL = "http://192.168.31.184:8011/main.png";
        Utilswwb utilswwb=new Utilswwb();
        utilswwb.setImageURL(strURL);
        Observer Observer=new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                if (arg instanceof Bitmap)
                {
                    Bitmap bitmap = (Bitmap) arg;
                    headimage.setImageBitmap(bitmap);
                }
            }
        };
        Rinker.getInstance().deleteObservers();
        Rinker.getInstance().addObserver(Observer);
    }
    public void setImageBackground(int Resource) {
        textView.setBackgroundResource(Resource);
    }

    public void setranking(RankingModel.record record) {
        String str0;
        if(record.praiseTotal>10000)
        {
            str0=String.format("%4.2fw",record.praiseTotal/10000);
        }
        else
        {
            str0=String.format("%d",record.praiseTotal);
        }
        textViewpraisetotal.setText(str0);
//        try {
//            setImageBitmap(record.logo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Glide.with(Objects.requireNonNull(context)).load(record.logo)
                .error(R.mipmap.user_icon) //异常时候显示的图片
                .placeholder(R.mipmap.user_icon) //加载成功前显示的图片
                .fallback(R.mipmap.user_icon) //url为空的时候,显示的图片
                .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                .into(headimage);//为了加载网络图片的两步方法
    }
}
