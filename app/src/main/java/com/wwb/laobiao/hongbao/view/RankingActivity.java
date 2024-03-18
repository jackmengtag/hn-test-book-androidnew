package com.wwb.laobiao.hongbao.view;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.wwb.laobiao.hongbao.bean.RankingModel;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragActivity;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.home.presenter.HomePresenter;
import com.yangna.lbdsp.mine.adapter.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.common.base.BasePresenterFragActivity;
//import com.qzy.laobiao.common.manager.ToastManager;
//import com.qzy.laobiao.common.net.MyObserver;
//import com.qzy.laobiao.common.net.NetWorks;
//import com.qzy.laobiao.home.presenter.HomePresenter;
//import com.qzy.laobiao.hongbao.bean.RankingModel;
//import com.qzy.laobiao.mine.adapter.TabPagerAdapter;

public class RankingActivity extends BasePresenterFragActivity<HomePresenter> implements ViewPager.OnPageChangeListener {
//    @BindView(R.id.home_tab)
    XTabLayout home_tab;
    @BindView(R.id.home_vp)
    ViewPager home_vp;
    @BindView(R.id.id_lay0)
    LinearLayout vplay0;
    @BindView(R.id.id_lay1)
    LinearLayout vplay1;
    @BindView(R.id.id_lay2)
    LinearLayout vplay2;

    @BindView(R.id.imageView0)
    ImageView image0;
    @BindView(R.id.imageView1)
    ImageView image1;
    @BindView(R.id.imageView2)
    ImageView image2;
    private List<View> mViews = new ArrayList<View>();// 用来存放Tab01-04
    //滑动页中所添加碎片的集合 RankingActivity2
    private List<Fragment> fragments;
    //tab栏标题的集合 HomeFragment
    private List<String> titleList;
    private TabPagerAdapter adapter;
    private LinearLayoutRankingview linearLayoutRankingviewweek,linearLayoutRankingviewsoaring;
    private LinearLayoutRankingTotalview linearLayoutRankingviewtotal;
    private int Tabflat=0;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_ranking_main;
    }
    @Override
    protected HomePresenter setPresenter() {
        //回调
        return new HomePresenter(this);
    }
    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler logo
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        super.initView();
        initpage();
        image0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onselpage(0);
                home_vp.setCurrentItem(0);
            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                onselpage(1);
                home_vp.setCurrentItem(1);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onselpage(2);
                home_vp.setCurrentItem(2);
            }
        });
       // netreshrank();
    }

    private void netreshrank() {
        MyObserver<RankingModel> observerx= new MyObserver<RankingModel>() {
            @Override
            public void onNext(RankingModel rankingModel) {
//                ToastManager.showToast(context, "test onNext"+rankingModel.getMsg());
                if(rankingModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;
                }
                if(rankingModel.getState()!=200)
                {
                  return;
                }
                if(rankingModel.getbodysize()<1)
                {
                    return;
                }
                rankingModel.show();
//                if(Tabflat==0)
                {
                    if(linearLayoutRankingviewweek!=null)
                    {
                        linearLayoutRankingviewweek.setranking(rankingModel);
                    }
                }
//                else if(Tabflat==1)
                {
                    if(linearLayoutRankingviewtotal!=null)
                    {
                        linearLayoutRankingviewtotal.setranking(rankingModel);
                    }
                }
//                else  if(Tabflat==1)
                {
                    if(linearLayoutRankingviewsoaring!=null)
                    {
                        linearLayoutRankingviewsoaring.setranking(rankingModel);
                    }
                }



            }
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, "onEr");
            }
        };
        NetWorks.getInstance().getranking(context,observerx);
    }
    private void initpage() {
        if(mViews==null)
        {
            mViews=new ArrayList<>();
        }
        mViews.clear();
        titleList=new ArrayList<>();
//        friendsVideoFragment0 = new RankingFragment();
        {
            linearLayoutRankingviewweek=new LinearLayoutRankingview(getApplicationContext());
            mViews.add(linearLayoutRankingviewweek);
            titleList.add("周榜");
        }
        {
            linearLayoutRankingviewtotal=new LinearLayoutRankingTotalview(getApplicationContext());
            mViews.add(linearLayoutRankingviewtotal);
            titleList.add("总榜");
        }
        {
            linearLayoutRankingviewsoaring=new LinearLayoutRankingview(getApplicationContext());
            mViews.add(linearLayoutRankingviewsoaring);
            titleList.add("飚升榜");
        }
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mViews.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                arg0.callOnClick();
                return arg0 == arg1;
            }

            @Override
            public int getCount() {

                return mViews.size();//setOnPageChangeListener
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return titleList.get(position);
            }
        };
        home_vp.setOnPageChangeListener(this);
        home_vp.setAdapter(mPagerAdapter);
//        home_tab.setupWithViewPager(home_vp);
////        home_tab.setSelectedTabIndicatorColor(Color.WHITE);
        home_vp.setCurrentItem(1);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        onselpage(position);
    }

    private void onselpage(int position) {
        Tabflat=position;
        netreshrank();
        switch(position)
        {
            case 0:
            {
                image0.setImageDrawable(getResources().getDrawable(R.mipmap.a_red));
                image1.setImageDrawable(getResources().getDrawable(R.mipmap.b_bk));
                image2.setImageDrawable(getResources().getDrawable(R.mipmap.c_bk));
//                         image0.getWidth();
                vplay0.setVisibility(View.VISIBLE);
                vplay1.setVisibility(View.GONE);
                vplay2.setVisibility(View.GONE);

            }
            break;
            case 1:
                image1.setImageDrawable(getResources().getDrawable(R.mipmap.b_red));
                image0.setImageDrawable(getResources().getDrawable(R.mipmap.a_bk));
                image2.setImageDrawable(getResources().getDrawable(R.mipmap.c_bk));
//                         image0.getWidth();
                vplay1.setVisibility(View.VISIBLE);
                vplay0.setVisibility(View.GONE);
                vplay2.setVisibility(View.GONE);
                break;
            case 2:
            {
                image0.setImageDrawable(getResources().getDrawable(R.mipmap.a_bk));
                image1.setImageDrawable(getResources().getDrawable(R.mipmap.b_bk));
                image2.setImageDrawable(getResources().getDrawable(R.mipmap.c_red));
//                         image0.getWidth();
                vplay0.setVisibility(View.GONE);
                vplay1.setVisibility(View.GONE);
                vplay2.setVisibility(View.VISIBLE);
            }
            break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
