package com.yangna.lbdsp.home.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


import com.cicada.player.utils.Logger;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.common.utils.DensityUtils;
import com.yangna.lbdsp.home.bean.CommMsg;
import com.yangna.lbdsp.home.bean.SecondLevelBean;
import com.yangna.lbdsp.home.impl.VideoListView;
import com.yangna.lbdsp.home.model.GiveCommentsModel;
import com.yangna.lbdsp.home.model.GiveLikeModel;
import com.yangna.lbdsp.home.model.VideoDetailModel;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.home.model.VideoWelfareModel;
import com.yangna.lbdsp.home.presenter.VideolistPresenter;
import com.yangna.lbdsp.videoCom.AliPlay.OnStsInfoExpiredListener;
import com.yangna.lbdsp.videoCom.AliPlay.view.AlivcVideoListView;
import com.yangna.lbdsp.videoCom.AliPlay.view.AlivcVideoPlayView;
import com.yangna.lbdsp.videoCom.ControllerView;
import com.yangna.lbdsp.videoCom.VideoBean;
import com.yangna.lbdsp.widget.VerticalCommentLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;

/**
 * 推介视频
 */
public class FriendsVideoFragment extends BasePresenterFragment<VideolistPresenter> implements VideoListView, VerticalCommentLayout.CommentItemClickListener {

    private static final String TAG = "FriednsVideoFragment";
    @BindView(R.id.video_play)
    AlivcVideoPlayView videoPlayView;
    private AlivcVideoListView videoListView;

    /**
     * 判断当前fragment是否可见
     */
    private boolean isVisibleToUserInFrag;

    /**
     * 当前请求的视频列表最后数据的主键id，用于查询下一页数据
     */
    private long mLastVideoId = 0;

    /**
     * 当前请求的视频列表页数，用于查询下一页数据
     */
    private int pagSize = 0;

    /**
     * 数据请求是否为加载更多数据
     */
    private boolean isLoadMoreData = false;


    @Override
    protected int getLayoutRes() {
        Log.i("life", "fd+onCreateView");
        return R.layout.fragment_friends_video;
    }

    @Override
    protected VideolistPresenter setPresenter() {
        return new VideolistPresenter(context);
    }


    @Override
    protected void initView() {
        super.initView();
        videoListView = new AlivcVideoListView(context);
        Logger.getInstance(getContext()).enableConsoleLog(true);
        Logger.getInstance(getContext()).setLogLevel(Logger.LogLevel.AF_LOG_LEVEL_TRACE);
        videoPlayView.setOnRefreshDataListener(new MyRefreshDataListener(this));
        videoPlayView.setOnStsInfoExpiredListener(new OnStsInfoExpiredListener() {
            @Override
            public void onTimeExpired() {

            }
        });
        initLiveView();
        mPresenter.setVideoListView(this);
        mPresenter.initLoadingView();
        mPresenter.getPromoteVideo(context, pagSize, videoPlayView);
        videoListView.setOnBackground(isVisibleToUserInFrag);
        Log.i("addUrl", "local+isVisibleToUserInFrag:" + isVisibleToUserInFrag);
        //初始化列表播放器
//        initAliPlayer();
        //初始化播放界面和暂停/播放按钮
//        initPlayerView();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Override
    protected boolean enableLoading() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(CommMsg commMsg) {
//        mPresenter.writeComment(context, videoId, commMsg.getMsg());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Test", "onAttach");
        setOnBackground(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Test", "onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        videoPlayView.onPause();
        Log.i("Test", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoPlayView != null) {
            videoPlayView.onResume();
            Log.i("life", "fd+videoPlayView.onResume();");
        }
        Log.i("life", "fd+onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoPlayView != null) {
            videoPlayView.onPause();
            Log.i("life", "fd+videoPlayView.onPause();");
        }
        Log.i("life", "fd+onResume");
    }

    private void initLiveView() {
        ImageView mImageView = new ImageView(getActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.width = DensityUtils.dip2px(getActivity(), 30);
        params.height = DensityUtils.dip2px(getActivity(), 30);
        int marginRight = DensityUtils.dip2px(getActivity(), 25);
        int marginTop = DensityUtils.dip2px(getActivity(), 40);
        params.setMargins(0, marginTop, marginRight, 0);
        params.gravity = Gravity.RIGHT;
//        mImageView.setImageResource(R.mipmap.log);
        videoPlayView.addView(mImageView, params);

    }


    /**
     * 视频播放列表刷新、加载更多事件监听
     */
    private static class MyRefreshDataListener implements AlivcVideoListView.OnRefreshDataListener {
        WeakReference<FriendsVideoFragment> weakReference;

        MyRefreshDataListener(FriendsVideoFragment activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onRefresh() {
            FriendsVideoFragment friendsVideoFragment = weakReference.get();
            if (friendsVideoFragment != null) {
                friendsVideoFragment.isLoadMoreData = false;
                friendsVideoFragment.mLastVideoId = 0;
                friendsVideoFragment.refreshPlayList(friendsVideoFragment.mLastVideoId);
                Log.i(TAG, "视频播放列表刷新、加载更多事件监听onRefresh");
            }
        }

        @Override
        public void onLoadMore() {
            FriendsVideoFragment friendsVideoFragment = weakReference.get();
            if (friendsVideoFragment != null) {
                friendsVideoFragment.isLoadMoreData = true;
                friendsVideoFragment.loadPlayList(friendsVideoFragment.mLastVideoId);
                Log.i(TAG, "视频播放列表刷新、加载更多事件监听onLoadMore");
            }
        }
    }


    /**
     * 下拉获取播放列表数据
     *
     * @param id 请求第pageNo页数据
     */
    private void loadPlayList(final long id) {
        pagSize++;
        mPresenter.getPromoteVideo(context, pagSize, videoPlayView);
        Log.d(TAG, "pagSize:" + pagSize);
    }


    /**
     * 上拉获取播放列表数据
     *
     * @param id 请求第pageNo页数据
     */
    private void refreshPlayList(final long id) {
        mPresenter.getPromoteVideo(context, pagSize, videoPlayView);
        Log.d(TAG, "pagSize:" + pagSize);
    }


    /**
     * activity不可见或者播放页面不可见时调用该方法
     */
    public void setOnBackground(boolean isOnBackground) {
//        this.mIsOnBackground = isOnBackground;
        if (isOnBackground) {
            Log.i(TAG, "暂停播放");
            pausePlay();
        } else {
            Log.i(TAG, "恢复播放");
            resumePlay();
        }
    }

    /**
     * 视频暂停/恢复的时候使用，
     */
    public void onPauseClick() {
        //        if (mIsPause) {
        //            resumePlay();
        //        } else {
        //            pausePlay();
        //        }
    }

    /**
     * 暂停播放
     */
    private void pausePlay() {
//        if (mPlayIconImageView != null) {
//            mIsPause = true;
//            mPlayIconImageView.setVisibility(View.VISIBLE);
//            mVideoListPlayer.pause();
//        }
    }


    /**
     * 恢复播放
     */
    private void resumePlay() {
//        if (mPlayIconImageView != null) {
//            mIsPause = false;
//            mPlayIconImageView.setVisibility(View.GONE);
//            mVideoListPlayer.start();
//        }

    }


    /**
     * 销毁
     */
    public void destroy() {
//        if (mVideoListPlayer != null) {
//            mVideoListPlayer.stop();
//            mVideoListPlayer.release();
//        }
    }


    @Override
    public void onGetDataLoading(boolean isSuccess) {
        if (loadingManager != null) {
            if (isSuccess) {
                Log.i(TAG, "加载状态" + isSuccess);
                loadingManager.networkSuccess();
            } else {
//                refreshLayout.setVisibility(View.GONE);
            }
        }

    }

    @Override //加载数据出错
    public void onNetLoadingFail() {
        loadingManager.networkFail(() -> {
            mPresenter.getPromoteVideo(context, pagSize, videoPlayView);
        });
    }


    //获取推介视频成功回调
    @Override
    public void onGetGainVideo(VideoListModel model) {
        Log.i(TAG, "获取" + model.getBody().getRecords().size() + "视频");
        if (model != null && model.getBody().getRecords().size() > 0) {
            int listSize = model.getBody().getRecords().size();
            for (int i = 0; i < listSize; i++) {
                mLastVideoId = model.getBody().getRecords().get(i).getId();
            }
        }
        if (!isLoadMoreData) {
            videoPlayView.refreshVideoList(model.getBody().getRecords());
        } else {
            videoPlayView.addMoreData(model.getBody().getRecords());
        }


    }

    //点赞回调
    @Override
    public void onGetLike(GiveLikeModel giveLikeModel, ControllerView controllerView, VideoBean videoBean){
//        controllerView.like(giveLikeModel.isBody());
    }

    //观看视频福利回调
    @Override
    public void onGetWelfareModel(VideoWelfareModel videoWelfareModel) {

    }

    //获取视频统计数回调
    @Override
    public void onStatisticalVideo(VideoDetailModel model) {
//        controllerView.liked(model.body.hasPraise);
//        controllerView.setCommentCount(model.body.commentTotal);
//        controllerView.setLikeCount(model.body.praiseTotal);
    }

    //获取评论回调
    @Override
    public void onGetComments(GiveCommentsModel giveCommentsModel) {

//        showSheetDialog(giveCommentsModel);
//        bottomSheetAdapter.setNewData(giveCommentsModel.getBody().getRecords());
    }

    //写评论成功回调
    @Override
    public void onWriteComment(GiveLikeModel giveLikeModel) {
//        mVideoListPlayer.clear();
//        mAliListAdapter.setNewData(model.getBody().getRecords());
//        mPresenter.getComments(context, videoId);
//        controllerView.commMsg(giveLikeModel.isBody());
    }

    @Override
    public void onGetCommentMsg(String msg) {

    }

    private int getWindowHeight() {
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }


    @Override
    public void onMoreClick(View layout, int position) {

    }

    @Override
    public void onItemClick(View view, SecondLevelBean bean, int position) {

    }

    @Override
    public void onLikeClick(View layout, SecondLevelBean bean, int position) {

    }


}