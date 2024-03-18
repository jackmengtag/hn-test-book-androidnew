package com.wwb.laobiao.Search.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.wwb.laobiao.Search.adp.FruitAdapter;
import com.wwb.laobiao.Search.impl.Fruit;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.mine.adapter.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.mine.adapter.TabPagerAdapter;
//import com.qzy.laobiao.testview0.adp.FruitAdapter;
//import com.qzy.laobiao.testview0.impl.Fruit;

public class LinearLayoutGusshot extends LinearLayout {
    @BindView(R.id.home_tab)
    XTabLayout home_tab;
    @BindView(R.id.home_vp)
    ViewPager home_vp;
    private LayoutInflater inflater;
    private TextView textView;
    private RecyclerView recyclerView;
    List<Fruit> fruitList;

    public LinearLayoutGusshot(Context context) {
        super(context);
        init(context,null);
    }
    public LinearLayoutGusshot(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);

    }
    //Tab栏和滑动页关联适配器
    private TabPagerAdapter adapter;
    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_search_gusshot, this);

    }

    private void initx(Context context, AttributeSet attrs) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_staggere_grid_item_bottom, this);
//        textView=findViewById(R.id.textView);
        recyclerView=findViewById(R.id.RecyclerView_iv_pinglun);
        recyclerView.setBackgroundColor(Color.BLACK);//fruit_item
        {
            fruitList=new ArrayList<Fruit>();
            for (int i = 0; i < 50; i++) {
                Fruit fruit = new Fruit(R.drawable.alivc_bottom_tab_item_selector,"Name"+i);
                fruitList.add(fruit);
            }
        }
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layout);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setAdapter(new RecyclerViewAdapter(getContext()));//AppCompatActivity
    }
    public LinearLayoutGusshot(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutGusshot(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }
    public void setText(String s) {
        if(textView!=null)
        {
            textView.setText(s);
        }
    }
}
