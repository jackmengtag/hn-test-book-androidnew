package com.wwb.laobiao.Search.adp;

import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;


public    class TabPagerAdapter0 extends PagerAdapter {
//    private List<Fragment> mViews = new ArrayList<View>(); //List<Fragment>
    public TabPagerAdapter0(android.app.FragmentManager fragmentManager, List<Fragment> fragments, List<String> titleList) {
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
//        container.removeView(mViews.get(position));
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        View view = mViews.get(position);
//        container.addView(view);
        return null;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return 0;
//        return mViews.size();
    }
}

