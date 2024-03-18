package com.yangna.lbdsp.home.view;


import android.util.Log;
import android.view.GestureDetector;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aliyun.player.AliListPlayer;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.base.BasePresenterFragActivity;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.videoCom.DouYinAliPlayAdapter;
import com.yangna.lbdsp.videoCom.VideoBean;
import com.yangna.lbdsp.widget.viewpagerlayoutmanager.AliViewPagerLayoutManager;
import com.yangna.lbdsp.widget.viewpagerlayoutmanager.OnViewPagerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PlayListActivity extends BasePresenterFragActivity {
    @BindView(R.id.play_recyclerview)
    RecyclerView play_recyclerview;

    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;

    /**
     * 是否是暂停
     */
    private boolean mIsPause;
    /**
     * 手势监听器
     */
    private GestureDetector mGestureDetector;

    /**
     * 是否在后台
     */
    private boolean mIsOnBackground;

    /**
     * 当前选中位置
     */
    private int mCurrentPosition;
    /**
     * 数据是否到达最后一页
     */
    private boolean isEnd;

    private AliViewPagerLayoutManager mLayoutManager;

    public static int initPos;

    private DouYinAliPlayAdapter mAliListAdapter;

    private static final String TAG = "PlayListActivity";

    AliListPlayer mVideoListPlayer;

    private long videoId;

    List<VideoBean> videoInfoList;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_play_list;
    }

    @Override
    protected void initView() {
        super.initView();
        videoInfoList = (List<VideoBean>) getIntent().getSerializableExtra("videoInfoList");
        Log.i(TAG, "视频个数" + videoInfoList.size() + "点击了第" + initPos + "个视频");
        //阿里云播放器
        mAliListAdapter = new DouYinAliPlayAdapter(context, new ArrayList<>());

        play_recyclerview.setAdapter(mAliListAdapter);
        mLayoutManager = new AliViewPagerLayoutManager(context, OrientationHelper.VERTICAL);
        play_recyclerview.setLayoutManager(mLayoutManager);
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }


    private View mListPlayerContainer;
    private ImageView mPlayIconImageView;
    TextureView mListPlayerTextureView = null;

    private void initListener(VideoListModel model) {
        mLayoutManager = new AliViewPagerLayoutManager(context, OrientationHelper.VERTICAL);
        play_recyclerview.setLayoutManager(mLayoutManager);
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                Log.i(TAG, "OnViewPager onInitComplete");
                int position = mLayoutManager.findFirstVisibleItemPosition();
                Log.i(TAG, "position:" + position);
                if (position != -1) {
                    mCurrentPosition = position;
                }
                Log.i(TAG, "mCurrentPosition:" + mCurrentPosition);
                startPlay(mCurrentPosition, model);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.i(TAG, "OnViewPager 释放位置:" + position + " mCurrentPosition=" + mCurrentPosition + " 下一页:" + isNext);
                if (mCurrentPosition == position) {
                    stopPlay();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                isEnd = isBottom;
                Log.i(TAG, "OnViewPager 选中位置:" + position + " mCurrentPosition=" + mCurrentPosition + "  是否是滑动到底部:" + isBottom);
                if (mCurrentPosition != position) {
                    startPlay(position, model);
                } else {
                    Log.i(TAG, "OnViewPager onPageSelected: 不做处理");
                }
                mCurrentPosition = position;
                if (isBottom) {
//                    Log.i(TAG, "滑动到底部重新请求加载视频" + currentPage + "" + isBottom);
//                    mPresenter.getVideoInfo(context, currentPage);
                }
            }
        });
    }

    /**
     * 播放视频
     */
    private void startPlay(int position, VideoListModel model) {
        if (position < 0 || position > mAliListAdapter.getData().size()) {
            return;
        }
        //恢复界面状态
        mIsPause = false;
        mPlayIconImageView.setVisibility(View.GONE);
        DouYinAliPlayAdapter.ViewHolder mViewHolder = (DouYinAliPlayAdapter.ViewHolder) play_recyclerview.findViewHolderForLayoutPosition(position);

        videoId = model.getBody().getRecords().get(position).getId();
//        mPresenter.getStatisticalVideo(context, videoId);

        ViewParent parent = mListPlayerContainer.getParent();
        VideoBean videoBean = mAliListAdapter.getData().get(position);

        if(parent instanceof FrameLayout) {
            ((ViewGroup) parent).removeView(mListPlayerContainer);
            mVideoListPlayer.stop();
            mVideoListPlayer.setSurface(null);
        } else {

        }
        if (mViewHolder != null) {
            mViewHolder.getContainerView().addView(mListPlayerContainer);
        }

        //防止退出后台之后，再次调用start方法，导致视频播放
        if (!mIsOnBackground) {
            //开始播放视频
            //mVideoListPlayer.moveTo("视频唯一标识") //URL源
            mVideoListPlayer.moveTo(position + "");
        }


    }


    /**
     * 停止视频播放
     */
    private void stopPlay() {
        ViewParent parent = mListPlayerContainer.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(mListPlayerContainer);
        }
        mVideoListPlayer.stop();
        mVideoListPlayer.setSurface(null);
    }

}
