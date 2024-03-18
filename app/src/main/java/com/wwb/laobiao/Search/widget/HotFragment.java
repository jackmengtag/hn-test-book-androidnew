package com.wwb.laobiao.Search.widget;


import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.wwb.laobiao.Search.adp.HotGuessAdapter;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.mine.adapter.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.common.base.BasePresenterFragment;
//import com.qzy.laobiao.home.view.MallFragment;
//import com.qzy.laobiao.mine.adapter.TabPagerAdapter;
//import com.qzy.laobiao.testview0.adp.HotGuessAdapter;
//import com.qzy.laobiao.testview0.view.SearchActivity1;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends BasePresenterFragment {
    @BindView(R.id.home_tab)
    XTabLayout home_tab;
    @BindView(R.id.home_vp)
    ViewPager home_vp;

    //滑动页中所添加碎片的集合
    private List<Fragment> fragments;
    //tab栏标题的集合 HomeFragment
    private List<String> titleList;
    //Tab栏和滑动页关联适配器
    private TabPagerAdapter adapter;
    private HotFragmentGuess localVideoFragment;
    private HotFragmentRanking friendsVideoFragment;
    private HotGuessAdapter.HotGuessAdapterinterface hotGuessAdapterinterface;
//    private MallFragment mallShopsFragment;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search_gusshot;
    }

    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(this).init();
        titleList = new ArrayList<>();
        fragments = new ArrayList<>();

        titleList.clear();
        titleList.add("猜你想搜");
        titleList.add("热点榜");
        localVideoFragment = new HotFragmentGuess();
        friendsVideoFragment = new HotFragmentRanking();
        {
            localVideoFragment.setinterface(hotGuessAdapterinterface);
            friendsVideoFragment.setinterface(hotGuessAdapterinterface);
        }
        //添加fragment
        fragments.add(localVideoFragment);
        fragments.add(friendsVideoFragment);

        //将tab栏和滑动页关联起来
        adapter = new TabPagerAdapter(getChildFragmentManager(), fragments, titleList);
        home_vp.setAdapter(adapter);
        home_vp.setOffscreenPageLimit(1);

        //设置tablayout和viewpager可联动
        home_tab.setupWithViewPager(home_vp);
    }

    public void setinterface(HotGuessAdapter.HotGuessAdapterinterface hotGuessAdapterinterface) {
        this.hotGuessAdapterinterface=hotGuessAdapterinterface;
    }

}
