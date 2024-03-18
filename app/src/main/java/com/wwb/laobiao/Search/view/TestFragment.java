package com.wwb.laobiao.Search.view;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.mine.adapter.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.common.base.BasePresenterFragment;
//import com.qzy.laobiao.mine.adapter.TabPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends BasePresenterFragment {
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
    private ZongheFragment zongheFragment;

    private SearhMineFragment searhMineFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<Fragment> mFragments;//
    private SearchFragment searchFragment;
    private SearchLocalVideoFragment searchLocalVideoFragment;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_test1;
    }
    @Override
    protected void initView() {
        super.initView();
//      fragmentManager = getChildFragmentManager();
//      fragmentTransaction = fragmentManager.beginTransaction();
//      mFragments = new ArrayList<>();
        ImmersionBar.with(this).init();
        titleList = new ArrayList<>();
        fragments = new ArrayList<>();
        titleList.clear();
        titleList.add("综合");
        titleList.add("视频");
        titleList.add("用户");
        zongheFragment = new ZongheFragment();
        searchLocalVideoFragment = new SearchLocalVideoFragment();
        searhMineFragment = new SearhMineFragment();
        //添加fragment
        fragments.add(zongheFragment);
        fragments.add(searchLocalVideoFragment);
        fragments.add(searhMineFragment);
        //将tab栏和滑动页关联起来
        adapter = new TabPagerAdapter(getChildFragmentManager(), fragments, titleList);
        home_vp.setAdapter(adapter);
        home_vp.setOffscreenPageLimit(2);
        //设置tablayout和viewpager可联动
        home_tab.setupWithViewPager(home_vp);
    }

}
