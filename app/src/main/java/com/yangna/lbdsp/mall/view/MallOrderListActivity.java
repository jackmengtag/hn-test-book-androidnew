package com.yangna.lbdsp.mall.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseFragActivity;
import com.yangna.lbdsp.mine.adapter.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商品订单
 */
public class MallOrderListActivity extends BaseFragActivity {
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
        return R.layout.activity_mall_order_list;
    }

    @Override
    protected void initView() {
        super.initView();

        titleManager.setTitleTxt("商城订单");
        titleManager.setLeftLayout(0, R.mipmap.back_left_img);

        fragments = new ArrayList<>();
        titleList = new ArrayList<>();

        titleList.clear();
        titleList.add("全部");
        titleList.add("待付款");
        titleList.add("待使用");
        titleList.add("待收货");
        titleList.add("待评价");
        titleList.add("退换");


        //添加fragment
        fragments.add(MallOrderListFragment.newInstance(10));
        fragments.add(MallOrderListFragment.newInstance(0));
        fragments.add(MallOrderListFragment.newInstance(3));
        fragments.add(MallOrderListFragment.newInstance(1));
        fragments.add(MallOrderListFragment.newInstance(5));
        fragments.add(MallOrderListFragment.newInstance(99));

        //将tab栏和滑动页关联起来
        adapter = new TabPagerAdapter(getSupportFragmentManager(), fragments, titleList);
        vp.setAdapter(adapter);



        //设置tablayout和viewpager可联动
        tabLayout.setupWithViewPager(vp);
        //设置tablayout可以滑动 宽度自适应屏幕大小
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
