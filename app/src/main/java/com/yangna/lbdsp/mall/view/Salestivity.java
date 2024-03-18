package com.yangna.lbdsp.mall.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseFragActivity;
import com.yangna.lbdsp.common.base.BasePresenterActivity;
import com.yangna.lbdsp.mine.adapter.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Salestivity extends BaseFragActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;

    //滑动页中所添加碎片的集合
    private List<Fragment> fragments;
    //tab栏标题的集合
    private List<String> titleList;
    //Tab栏和滑动页关联适配器
    private TabPagerAdapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_salestivity;
    }

    @Override
    protected void initView() {
        super.initView();

        titleManager.setTitleTxt("加油订单");
        titleManager.setLeftLayout(0, R.mipmap.back_left_img);

        fragments = new ArrayList<>();
        titleList = new ArrayList<>();

        titleList.clear();
        titleList.add("售后申请");
        titleList.add("处理中");
        titleList.add("售后评价");
        titleList.add("申请记录");

        //添加fragment
        fragments.add(AfterFgFragment.newInstance(-1));
        fragments.add(AfterFgFragment.newInstance(0));
        fragments.add(AfterFgFragment.newInstance(1));

        //将tab栏和滑动页关联起来
        adapter = new TabPagerAdapter(getSupportFragmentManager(), fragments, titleList);
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(2);

        //设置tablayout和viewpager可联动
        tabLayout.setupWithViewPager(vp);
        //设置tablayout可以滑动 宽度自适应屏幕大小
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
