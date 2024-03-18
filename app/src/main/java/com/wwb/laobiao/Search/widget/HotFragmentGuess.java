package com.wwb.laobiao.Search.widget;


import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.Search.adp.HotGuessAdapter;
import com.wwb.laobiao.Search.impl.Hotguss;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.widget.FullScreenVideoView;
import com.yangna.lbdsp.widget.viewpagerlayoutmanager.ViewPagerLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.common.base.BasePresenterFragment;
//import com.qzy.laobiao.testview0.adp.HotGuessAdapter;
//import com.qzy.laobiao.testview0.impl.Hotguss;
//import com.qzy.laobiao.widget.FullScreenVideoView;
//import com.qzy.laobiao.widget.viewpagerlayoutmanager.ViewPagerLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragmentGuess extends BasePresenterFragment implements HotGuessAdapter.HotGuessAdapterinterface {
    @BindView(R.id.local_ry)
    RecyclerView local_ry;
//    private Pressinterface pressinterface;
    private ViewPagerLayoutManager viewPagerLayoutManager;
    /** 当前播放视频位置 */
    private int curPlayPos = -1;
    private FullScreenVideoView videoView;
    private ImageView ivCurCover;
    private int oldPosition;
    private HotGuessAdapter adapter;
    private HotGuessAdapter.HotGuessAdapterinterface hotGuessAdapterinterface;

    public HotFragmentGuess() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_hot_guess;
    }

    @Override
    protected void initView() {
        super.initView();
//        adapter = new VideoAdapter(getContext(), DataCreate.datas);
//        local_ry.setAdapter(adapter);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        local_ry.setLayoutManager(layout);
        List<Hotguss> fruitList=new ArrayList<Hotguss>();
        {
            Hotguss hotguss=new Hotguss();
            //hotguss.text="广西柳州"；
            hotguss.SetName("看电影",0);
            hotguss.SetName("游泳",1);
            fruitList.add(hotguss);
        }
        {
            Hotguss hotguss=new Hotguss();
            // hotguss.text="螺蛳粉"；
            hotguss.SetName("奶茶",0);
            hotguss.SetName("爬山",1);
            fruitList.add(hotguss);
        }
        {
            Hotguss hotguss=new Hotguss();
            // hotguss.text="螺蛳粉"；
            hotguss.SetName("烧饼",0);
            hotguss.SetName("柳州方言配音",1);
            fruitList.add(hotguss);
        }
        {
            Hotguss hotguss=new Hotguss();
            // hotguss.text="螺蛳粉"；
            hotguss.SetName("烧鸭粉",0);
            hotguss.SetName("柳州方言配音",1);
            fruitList.add(hotguss);
        }
        adapter = new HotGuessAdapter(fruitList);
        adapter.setHotGuessAdapterinterface(hotGuessAdapterinterface);
        local_ry.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showname(String toString) {

    }
    public void setinterface(HotGuessAdapter.HotGuessAdapterinterface hotGuessAdapterinterface) {
//      adapter.setHotGuessAdapterinterface(hotGuessAdapterinterface);
        this.hotGuessAdapterinterface=hotGuessAdapterinterface;
    }

}
