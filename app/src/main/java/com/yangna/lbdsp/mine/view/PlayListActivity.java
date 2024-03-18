package com.yangna.lbdsp.mine.view;


import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aliyun.player.AliListPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.base.BasePresenterFragActivity;
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
        initVideoListener(videoInfoList);
        //初始化列表播放器
        initAliPlayer();
        //初始化播放界面和暂停/播放按钮
        mAliListAdapter.setNewData(videoInfoList);
        initPlayerView();
        for (int i = 0; i < mAliListAdapter.getData().size(); i++) {
//            mVideoListPlayer.addUrl(mAliListAdapter.getData().get(i).getUploadAddress(), i + "");
            mVideoListPlayer.addUrl(mAliListAdapter.getData().get(i).getUploadAddress(), mAliListAdapter.getData().get(i).getId() + "");
            Log.i(TAG, "添加视频URL：=====" + mAliListAdapter.getData().get(i).getUploadAddress() + "=====uid=====" + i + 1);
        }
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    private void initAliPlayer() {
        mVideoListPlayer = AliPlayerFactory.createAliListPlayer(context);
        //开启硬解
        mVideoListPlayer.enableHardwareDecoder(true);
        //设置播放器参数
        PlayerConfig config = mVideoListPlayer.getConfig();
        //停止之后清空画面。防止画面残留（建议设置）
        config.mClearFrameWhenStop = true;
        mVideoListPlayer.setConfig(config);
        //开启循环播放。短视频一般都是循环播放的
        mVideoListPlayer.setLoop(true);
        //开启自动播放。
        mVideoListPlayer.setAutoPlay(true);//注意这个一定要开启
        //设置预加载个数。此时会加载3个视频。当前播放的，和上下1个。
        mVideoListPlayer.setPreloadCount(1);

        //准备成功事件
        mVideoListPlayer.setOnPreparedListener(new IPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                System.out.println("AliListPlayerActivity.onPrepared mIsPause=" + mIsPause + " mIsOnBackground=" + mIsOnBackground);
                if (!mIsPause && !mIsOnBackground) {
                    mVideoListPlayer.start();
                }
            }
        });

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (!recyclerView.canScrollVertically(1)) {//滑动到底部加载数据
//                    mPresenter.getVideoInfo(context, currentPage);
//                }
//            }
//        });

        //列表播放时一般都会有个封面，视频播起来之后，把封面隐藏，显示视频。
        mVideoListPlayer.setOnRenderingStartListener(new IPlayer.OnRenderingStartListener() {
            @Override
            public void onRenderingStart() {
                //视频第一帧开始渲染。此时可以隐藏封面
                /*if (mRecyclerView != null) {
                    AliListAdapter.ViewHolder mViewHolder = (AliListAdapter.ViewHolder) mRecyclerView.findViewHolderForLayoutPosition(mCurrentPosition);
                    if (mViewHolder != null) {
                        mViewHolder.getCoverView().setVisibility(View.GONE);
                    }
                }*/
            }
        });

        //其他信息的事件，type包括了：循环播放开始，缓冲位置，当前播放位置，自动播放开始等
        mVideoListPlayer.setOnInfoListener(new IPlayer.OnInfoListener() {
            @Override
            public void onInfo(InfoBean infoBean) {
            }
        });
        //出错事件
        mVideoListPlayer.setOnErrorListener(new IPlayer.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) {
                Toast.makeText(context, errorInfo.getCode() + " --- " + errorInfo.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 初始化播放View 关键代码，好好理解！！
     */
    private void initPlayerView() {
        mListPlayerContainer = View.inflate(context, R.layout.layout_list_player_view, null);
        mPlayIconImageView = mListPlayerContainer.findViewById(R.id.iv_play_icon);
        mListPlayerTextureView = mListPlayerContainer.findViewById(R.id.list_player_textureview);

        //TextureView
        mListPlayerTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                System.out.println("initPlayerView AliListPlayerActivity.onSurfaceTextureAvailable");
                Surface mSurface = new Surface(surface);
                if (mVideoListPlayer != null) {
                    mVideoListPlayer.setSurface(mSurface);
                    mVideoListPlayer.redraw();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                System.out.println("initPlayerView AliListPlayerActivity.onSurfaceTextureSizeChanged");
                if (mVideoListPlayer != null) {
                    mVideoListPlayer.redraw();
                }
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                System.out.println("initPlayerView AliListPlayerActivity.onSurfaceTextureDestroyed");
                //这里一定要return false。这样SurfaceTexture不会被系统销毁
                //测试发现 返回 true 也没有什么影响,官方demo 中是返回的true
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                //System.out.println("initPlayerView AliListPlayerActivity.onSurfaceTextureUpdated");
            }
        });


        //手势监听器
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                onPauseClick();
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });


        //播放列表界面的touch事件由手势监听器处理
        mListPlayerContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                return mGestureDetector.onTouchEvent(motionEvent);
            }
        });
    }


    private View mListPlayerContainer;
    private ImageView mPlayIconImageView;
    TextureView mListPlayerTextureView = null;

    private void initVideoListener(List<VideoBean> videoInfoList) {
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
                startPlay(mCurrentPosition, videoInfoList);
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
                    startPlay(position, videoInfoList);
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
    private void startPlay(int position, List<VideoBean> videoInfoList) {
        if (position < 0 || position > mAliListAdapter.getData().size()) {
            return;
        }
        //恢复界面状态
        mIsPause = false;
        mPlayIconImageView.setVisibility(View.GONE);
        DouYinAliPlayAdapter.ViewHolder mViewHolder = (DouYinAliPlayAdapter.ViewHolder) play_recyclerview.findViewHolderForLayoutPosition(position);

        videoId = videoInfoList.get(position).getId();
//        mPresenter.getStatisticalVideo(context, videoId);

        ViewParent parent = mListPlayerContainer.getParent();
        VideoBean videoBean = mAliListAdapter.getData().get(position);

        if (parent instanceof FrameLayout) {
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
     * 视频暂停/恢复的时候使用，
     */
    public void onPauseClick() {
        if (mIsPause) {
            resumePlay();
        } else {
            pausePlay();
        }
    }

    /**
     * 恢复播放
     */
    private void resumePlay() {
        if (mPlayIconImageView != null) {
            mIsPause = false;
            mPlayIconImageView.setVisibility(View.GONE);
            mVideoListPlayer.start();
        }
    }

    /**
     * 暂停播放
     */
    private void pausePlay() {
        if (mPlayIconImageView != null) {
            mIsPause = true;
            mPlayIconImageView.setVisibility(View.VISIBLE);
            mVideoListPlayer.pause();
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
