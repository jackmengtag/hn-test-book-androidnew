package com.wwb.laobiao.Search.adp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    public ViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> mFragments) {
        super(fm);
        this.mFragments=mFragments;
//        mFragments.add(new ClickFragment());
//        mFragments.add(new DateFragment());
//        mFragments.add(new AnimFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


}
