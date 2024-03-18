package com.yangna.lbdsp.mine.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterActivity;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.mine.adapter.CommPagerAdapter;
import com.yangna.lbdsp.mine.impl.PersonalView;
import com.yangna.lbdsp.mine.presenter.HomepagePresenter;
import com.yangna.lbdsp.widget.CircleImageView;
import com.yangna.lbdsp.widget.IconFontTextView;

import java.util.ArrayList;

import butterknife.BindView;


public class PersonalHomeActivity extends BasePresenterActivity<HomepagePresenter> implements PersonalView, View.OnClickListener {

    public static String token = "token";

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.ll_focus)
    LinearLayout llFocus;
    @BindView(R.id.ll_fans)
    LinearLayout llFans;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_focus)
    TextView tvFocus;
    @BindView(R.id.iv_more)
    IconFontTextView ivMore;

    @BindView(R.id.tablayout)
    XTabLayout tabLayout;
    @BindView(R.id.appbarlayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_getlike_count)
    TextView tvGetLikeCount;
    @BindView(R.id.tv_focus_count)
    TextView tvFocusCount;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.tv_addfocus)
    TextView tvAddfocus;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CommPagerAdapter pagerAdapter;


    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(this).statusBarColor("#161924").fitsSystemWindows(true).init();
        setAppbarLayoutPercent();
        ivReturn.setOnClickListener(this);
        ivHead.setOnClickListener(this);
        ivBg.setOnClickListener(this);
        llFocus.setOnClickListener(this);
        llFans.setOnClickListener(this);
        setTabLayout();
    }

    private void setTabLayout() {
        String[] titles = new String[]{"作品 100w", "喜欢 1000w"};

        fragments.clear();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(new WorkFragment());
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }
//  \\
        pagerAdapter = new CommPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 根据滚动比例渐变view
     */
    private void setAppbarLayoutPercent() {
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());  //滑动比例

            if (percent > 0.8) {
                tvTitle.setVisibility(View.VISIBLE);
                tvFocus.setVisibility(View.VISIBLE);

                float alpha = 1 - (1 - percent) * 5;  //渐变变换
                tvTitle.setAlpha(alpha);
                tvFocus.setAlpha(alpha);
            } else {
                tvTitle.setVisibility(View.GONE);
                tvFocus.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 自动回顶部
     */
    private void coordinatorLayoutBackTop() {
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != 0) {
                appBarLayoutBehavior.setTopAndBottomOffset(0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_return:
                break;
            case R.id.iv_head:
                break;
            case R.id.ll_focus:
            case R.id.ll_fans:
                break;
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_personal_home;
    }

    @Override
    protected HomepagePresenter setPresenter() {
        return new HomepagePresenter(this);
    }



    @Override
    public void onInitData(VideoListModel VideoListModel) {

    }

    @Override
    public void onMineVideo(VideoListModel VideoListModel) {

    }

    @Override
    public void onUserLoveVideo(VideoListModel VideoListModel) {

    }
}
