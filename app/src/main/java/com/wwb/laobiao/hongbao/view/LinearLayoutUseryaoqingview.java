package com.wwb.laobiao.hongbao.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.hongbao.adapter.Yaoqing;
import com.wwb.laobiao.hongbao.bean.Fruit;
import com.yangna.lbdsp.R;

import java.util.List;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.hongbao.adapter.Yaoqing;
//import com.qzy.laobiao.testview0.impl.Fruit;

public class LinearLayoutUseryaoqingview extends LinearLayout {
    private LayoutInflater inflater;
    private TextView textView;
    private RecyclerView recyclerView;
    List<Fruit> fruitList;

    public LinearLayoutUseryaoqingview(Context context) {
        super(context);
        init(context,null);
    }
    public LinearLayoutUseryaoqingview(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }
    private void init(Context context, AttributeSet attrs) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_useryaoqing_item, this);
        textView=findViewById(R.id.textView_iv);

    }
    public LinearLayoutUseryaoqingview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutUseryaoqingview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    public void setText(String s) {
        if(textView!=null)
        {
            textView.setText(s);
        }
    }
    public void setyaoqing(Yaoqing yaoqing) {
        textView.setText(yaoqing.name);
    }
}
