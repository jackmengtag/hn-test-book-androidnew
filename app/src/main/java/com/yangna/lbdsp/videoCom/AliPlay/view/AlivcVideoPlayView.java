package com.yangna.lbdsp.videoCom.AliPlay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aliyun.downloader.AliMediaDownloader;
import com.aliyun.player.IPlayer;
import com.yangna.lbdsp.common.eventbus.BusHomeControllerView;
import com.yangna.lbdsp.common.eventbus.BusMallActual;
import com.yangna.lbdsp.home.bean.CommMsg;
import com.yangna.lbdsp.home.impl.ControllreClickListenter;
import com.yangna.lbdsp.videoCom.AliPlay.DensityUtil;
import com.yangna.lbdsp.videoCom.AliPlay.IVideoSourceModel;
import com.yangna.lbdsp.videoCom.AliPlay.LittleVideoListAdapter;
import com.yangna.lbdsp.videoCom.AliPlay.LoadingView;
import com.yangna.lbdsp.videoCom.AliPlay.OnStsInfoExpiredListener;
import com.yangna.lbdsp.videoCom.AliPlay.OnTimeExpiredErrorListener;
import com.yangna.lbdsp.videoCom.ControllerView;
import com.yangna.lbdsp.videoCom.VideoBean;
import com.yangna.lbdsp.widget.ShareDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 播放界面, 负责initPlayerSDK以及各种view
 *
 * @author xlx
 */
public class AlivcVideoPlayView extends FrameLayout {

    private static final String TAG = "AlivcVideoPlayView";
    private Context context;
    private AlivcVideoListView videoListView;

    /**
     * 刷新数据listener (下拉刷新和上拉加载)
     */
    private AlivcVideoListView.OnRefreshDataListener onRefreshDataListener;
    /**
     * 视频缓冲加载view
     */
    private LoadingView mLoadingView;


    /**
     * 分享选择提示窗
     *
     * @param context
     */

    private ShareDialog mShareDialog;

    /**
     * 视频删除点击事件
     */
    private OnVideoDeleteListener mOutOnVideoDeleteListener;
    private LittleVideoListAdapter mVideoAdapter;

    private ControllerView controllerView;

    public AlivcVideoPlayView(@NonNull Context context) {
        this(context, null);
    }

    public AlivcVideoPlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        initPlayListView();
        initLoadingView();
    }

    /**
     * 下载sdk核心类
     */
    private AliMediaDownloader mDownloadManager;

    private int mClickPosition;

    /**
     * 初始化视频列表
     */
    private void initPlayListView() {
        videoListView = new AlivcVideoListView(context);
        //创建adapter，需要继承BaseVideoListAdapter
        mVideoAdapter = new LittleVideoListAdapter(context);
        mVideoAdapter.setItemBtnClick(new LittleVideoListAdapter.OnItemBtnClick() {
            @Override
            public void onDownloadClick(int position) {
                mClickPosition = position;

            }
        });
        //给AlivcVideoListView实例化对象添加adapter
        videoListView.setAdapter(mVideoAdapter);
        videoListView.setVisibility(VISIBLE);
        //设置sdk播放器实例化对象数量
        videoListView.setPlayerCount(1);
        //设置下拉、上拉监听进行加载数据
        Log.i("Test", " videoListView.setOnRefreshDataListener");
        videoListView.setOnRefreshDataListener(new AlivcVideoListView.OnRefreshDataListener() {
            @Override
            public void onRefresh() {
                if (onRefreshDataListener != null) {
                    onRefreshDataListener.onRefresh();
                    Log.i("Test", "onRefresh");
                }
            }

            @Override
            public void onLoadMore() {
                if (onRefreshDataListener != null) {
                    onRefreshDataListener.onLoadMore();
                    Log.i("Test", "onLoadMore");
                }
            }
        });
        //设置视频缓冲监听
        videoListView.setLoadingListener(new IPlayer.OnLoadingStatusListener() {
            @Override
            public void onLoadingBegin() {
                mLoadingView.start();
            }

            @Override
            public void onLoadingEnd() {
                mLoadingView.cancle();
            }

            @Override
            public void onLoadingProgress(int var1, float var2) {

            }
        });


        //设置鉴权过期监听，刷新鉴权信息
        videoListView.setTimeExpiredErrorListener(new OnTimeExpiredErrorListener() {

            @Override
            public void onTimeExpiredError() {
                if (mStsInfoExpiredListener != null) {
                    mStsInfoExpiredListener.onTimeExpired();
                }
            }
        });
        //添加到布局中
        addSubView(videoListView);


        mVideoAdapter.setmClickListener(new ControllreClickListenter() {
            @Override
            public void onClick(VideoBean videoBean, ControllerView controllerView,int i) {
                EventBus.getDefault().post(new BusHomeControllerView(controllerView,videoBean,i));
            }
        });

    }


    /**
     * 播放、下载、上传过程中鉴权过期时提供的回调消息
     */
    private OnStsInfoExpiredListener mStsInfoExpiredListener;

    public void setOnStsInfoExpiredListener(
            OnStsInfoExpiredListener mTimeExpiredErrorListener) {
        this.mStsInfoExpiredListener = mTimeExpiredErrorListener;
    }

    /**
     * 删除按钮点击listener
     */
    public interface OnVideoDeleteListener {
        /**
         * 删除视频
         *
         * @param videoId 视频id
         */
        void onDeleteClick(VideoBean videoId);
    }

    /**
     * 设置下拉刷新数据listener
     *
     * @param listener OnRefreshDataListener
     */
    public void setOnRefreshDataListener(AlivcVideoListView.OnRefreshDataListener listener) {
        this.onRefreshDataListener = listener;
    }

    public void onStart() {
        Log.i(TAG, "onStart");
    }

    public void onResume() {
        Log.i(TAG, "onResume");
        videoListView.setOnBackground(false);

    }

    public void onStop() {
        Log.i(TAG, "onStop");
        mLoadingView.cancle();
    }

    public void onPause() {
        Log.i(TAG, "onPause");
        videoListView.setOnBackground(true);

    }

    public void onDestroy() {
        Log.i(TAG, "onDestroy");
    }

    /**
     * 刷新视频列表数据
     *
     * @param datas
     */
    public void refreshVideoList(List<? extends VideoBean> datas) {
        List<IVideoSourceModel> videoList = new ArrayList<>();
        videoList.addAll(datas);
        videoListView.refreshData(videoList);
        //取消加载loading
        mLoadingView.cancle();

    }

    /**
     * 添加更多视频
     *
     * @param datas
     */
    public void addMoreData(List<? extends VideoBean> datas) {
        List<IVideoSourceModel> videoList = new ArrayList<>();
        videoList.addAll(datas);
        videoListView.addMoreData(videoList);
        //取消加载loading
        mLoadingView.cancle();
    }


    /**
     * 视频列表获取失败
     */
    public void loadFailure() {
        mLoadingView.cancle();
        videoListView.loadFailure();
    }


    private void initLoadingView() {
        mLoadingView = new LoadingView(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                5);
        params.setMargins(0, 0, 0, DensityUtil.dip2px(40));
        params.gravity = Gravity.BOTTOM;
        addView(mLoadingView, params);
    }

    /**
     * addSubView 添加子view到布局中
     *
     * @param view 子view
     */
    private void addSubView(View view) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        addView(view, params);
    }

}
