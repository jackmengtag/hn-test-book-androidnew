package com.yangna.lbdsp.home.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.yangna.lbdsp.home.view.FriendsVideoFragment;
import com.yangna.lbdsp.home.view.HomeFragment;
import com.yangna.lbdsp.videoCom.LocalFragment;

public class TabPager2FragmentStateAdapter extends FragmentStateAdapter {

    private String[] mList;

    public TabPager2FragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager,lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //Fragment懒加载
        switch (position) {
            case 0:
                return new LocalFragment();
            case 1:
                return new FriendsVideoFragment();
            default:
                return new LocalFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
