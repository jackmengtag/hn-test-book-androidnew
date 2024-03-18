package com.yangna.lbdsp.home.view;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.androidkun.xtablayout.XTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;
import com.yangna.lbdsp.MainActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.home.adapter.TabPager2FragmentStateAdapter;
import com.yangna.lbdsp.mine.adapter.TabPagerAdapter;
import com.yangna.lbdsp.videoCom.LocalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BasePresenterFragment {

    private static final String TAG = "HomeFragment";
    @BindView(R.id.home_tab)
    TabLayout home_tab;
    @BindView(R.id.home_vp)
    public ViewPager2 home_vp;

    //滑动页中所添加碎片的集合
    private List<Fragment> fragments;

    //tab栏标题的集合
    private List<String> list_title;

    //Tab栏和滑动页关联适配器
    private TabPagerAdapter adapter;
    private TabPager2FragmentStateAdapter tabPager2FragmentStateAdapter;
    private LocalFragment localFragment;
    private FriendsVideoFragment friendsVideoFragment;
    private MallFragment mallShopsFragment;
    private int mPosition = 0;

    @Override
    protected int getLayoutRes() {
        Log.i("life", "home+onCreateView");
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(this).init();
        list_title = new ArrayList<>();
        fragments = new ArrayList<>();


        localFragment = new LocalFragment();
        friendsVideoFragment = new FriendsVideoFragment();
        mallShopsFragment = new MallFragment();


        //添加fragment
        fragments.add(localFragment);
        fragments.add(friendsVideoFragment);


        //将tab栏和滑动页关联起来
        FragmentManager fm = getChildFragmentManager();
        tabPager2FragmentStateAdapter = new TabPager2FragmentStateAdapter(fm, getLifecycle());
        home_vp.setAdapter(tabPager2FragmentStateAdapter);
        home_vp.setOffscreenPageLimit(1);
        home_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            /**
             * 选中某个tab
             * @param tab
             */
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                home_vp.setCurrentItem(tab.getPosition());
                Log.d(TAG, "Selected" + tab.getPosition());
            }

            /**
             * 当tab从选择到未选择
             * @param tab
             */
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d(TAG, "Unselected" + tab.getPosition());
            }

            /**
             * 已经选中tab后的重复点击tab
             * @param tab
             */
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(TAG, "Reselected" + tab.getPosition());
            }
        });
        home_vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                home_tab.getTabAt(position).select();
                Log.d(TAG, "onPageSelected" + position);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (friendsVideoFragment != null && localFragment != null) {
            friendsVideoFragment.onResume();
            localFragment.onResume();
            Log.i("life", "home+videoPlayView.onResume()");
        }
        Log.i("life", "home+onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (friendsVideoFragment != null && localFragment != null) {
            friendsVideoFragment.onPause();
            localFragment.onPause();
            Log.i("life", "home+videoPlayView.onPause()");
        }
        Log.i("life", "home+onPause");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("life", "home+onPause");
    }
}
