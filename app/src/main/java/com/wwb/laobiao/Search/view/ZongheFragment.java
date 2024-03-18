package com.wwb.laobiao.Search.view;


import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wwb.laobiao.Search.adp.SearchIndexAdapter;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.widget.FullScreenVideoView;
import com.yangna.lbdsp.widget.viewpagerlayoutmanager.ViewPagerLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.common.base.BasePresenterFragment;
//import com.qzy.laobiao.home.view.PlayListActivity;
//import com.qzy.laobiao.testview0.adp.FruitAdapter;
//import com.qzy.laobiao.testview0.adp.SearchIndexAdapter;
//import com.qzy.laobiao.testview0.adp.StaggeredGridAdapter;
//import com.qzy.laobiao.testview0.impl.Fruit;
//import com.qzy.laobiao.widget.FullScreenVideoView;
//import com.qzy.laobiao.widget.viewpagerlayoutmanager.OnViewPagerListener;
//import com.qzy.laobiao.widget.viewpagerlayoutmanager.ViewPagerLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZongheFragment extends BasePresenterFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.local_ry)
    RecyclerView  recyclerView;
//    @BindView(R.id.local_ref)
//    SwipeRefreshLayout  swipeRefreshLayout ;
    private ViewPagerLayoutManager viewPagerLayoutManager;
    /** 当前播放视频位置 */
    private int curPlayPos = -1;
    private FullScreenVideoView videoView;
    private ImageView ivCurCover;
    private int oldPosition;
    private List<String> list;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_zonghe;
    }
    @Override
    protected void initView() {
        super.initView();
        if(0==0)
        {
            ArrayList<SearchIndexView> searchIndexViews = new ArrayList<SearchIndexView>();
            for(int i=0;i<11;i++)
            {
                {
                    SearchIndexView mSearchIndexView=new SearchIndexView();
                    mSearchIndexView.sky=0;
                    searchIndexViews.add(mSearchIndexView);
                }
                {
                    SearchIndexView mSearchIndexView=new SearchIndexView();
                    mSearchIndexView.sky=1;
                    searchIndexViews.add(mSearchIndexView);
                }
                {
                    SearchIndexView mSearchIndexView=new SearchIndexView();
                    mSearchIndexView.sky=2;
                    searchIndexViews.add(mSearchIndexView);
                }
            }
            LinearLayoutManager layout1 = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layout1);
            SearchIndexAdapter adapter = new SearchIndexAdapter(searchIndexViews,context);
            recyclerView.setAdapter(adapter);
        }
        else
        {

        }
        
        }
    @Override
    public void onRefresh() {
//            handler.sendEmptyMessageDelayed(2,2000l);
    }

}
