package com.wwb.laobiao.address.model;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.yangna.lbdsp.R;

////import com.qzy.laobiao.R;
////import com.qzy.laobiao.hongbao.widget.AvatarImageView;

public class LinearLayoutZhifu extends LinearLayout {
    private LayoutInflater inflater;
    private TextView textView;
    private Button btok;
    private RelativeLayout rlay;
    private LinearLayoutZhifuinterface linearLayoutZhifuinterface;

    public LinearLayoutZhifu(Context context) {
        super(context);
        init(context,null);

    }
    public LinearLayoutZhifu(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_zhifu, this);
        textView=findViewById(R.id.textView_mmy);
        btok=findViewById(R.id.buttonok);
        btok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayoutZhifuinterface!=null)
                {
                    linearLayoutZhifuinterface.onok();
                }
            }
        });
    }
    public LinearLayoutZhifu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutZhifu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    public void setinterface(LinearLayoutZhifuinterface linearLayoutZhifuinterface) {
        this.linearLayoutZhifuinterface=linearLayoutZhifuinterface;
    }
    public void setzongji(int userLevel, String goodsPrice) {
        float val=0;
        try {
            val=Float.valueOf(goodsPrice);
//            val = float.valueOf(goodsPrice);
        } catch (Exception e) {
            // TODO: handle exception
        }
        if(userLevel<0)
        {
            textView.setText("--");
        }
        else
        {
            val=userLevel*val;
            textView.setText(String.format("%4.2f",val));
        }

    }

    public String getzongji() {

        return  textView.getText().toString();
    }
    public interface LinearLayoutZhifuinterface {
        void onok();
    }
}
