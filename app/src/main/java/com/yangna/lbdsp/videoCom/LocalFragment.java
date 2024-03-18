package com.yangna.lbdsp.videoCom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aliyun.player.AliListPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.cicada.player.utils.Logger;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragment;
import com.yangna.lbdsp.common.eventbus.BusHomeControllerView;
import com.yangna.lbdsp.common.eventbus.BusMallActual;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.common.utils.DensityUtils;
import com.yangna.lbdsp.common.utils.OnVideoControllerListener;
import com.yangna.lbdsp.common.utils.RecyclerViewUtil;
import com.yangna.lbdsp.home.adapter.CommentDialogSingleAdapter;
import com.yangna.lbdsp.home.bean.CommMsg;
import com.yangna.lbdsp.home.bean.FirstLevelBean;
import com.yangna.lbdsp.home.bean.SecondLevelBean;
import com.yangna.lbdsp.home.impl.ControllreClickListenter;
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
import com.yangna.lbdsp.widget.VerticalCommentLayout;
import com.yangna.lbdsp.widget.dialog.InputTextMsgDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 登陆界面
 */
public class LocalFragment extends BasePresenterFragment<VideolistPresenter> implements VideoListView, VerticalCommentLayout.CommentItemClickListener {

    private static final String TAG = "LocalFragment";

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

    private ControllerView controllerView;

    /**
     * 评论dialog
     */
    private BottomSheetDialog bottomSheetDialog;

    private RecyclerView rv_dialog_lists;

    private CommentDialogSingleAdapter bottomSheetAdapter;

    private float slideOffsets = 0;

    private RecyclerViewUtil mRecyclerViewUtil;

    private ArrayList<FirstLevelBean> data = new ArrayList<>();

    private InputTextMsgDialog inputTextMsgDialog;

    private long totalCount = 30;//总条数不得超过它
    private int offsetY;
    private long videoId;


    @Override
    protected int getLayoutRes() {
        Log.i("life", "local+onCreateView");
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
        videoPlayView.setOnRefreshDataListener(new LocalFragment.MyRefreshDataListener(this));
        videoPlayView.setOnStsInfoExpiredListener(new OnStsInfoExpiredListener() {
            @Override
            public void onTimeExpired() {

            }
        });
        initLiveView();
        mPresenter.setVideoListView(this);
        mPresenter.initLoadingView();
        mPresenter.getVideoInfo(context, pagSize, videoPlayView);
        Log.i("addUrl", "fd+isVisibleToUserInFrag:" + isVisibleToUserInFrag);

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
    public void getEventBus(BusHomeControllerView busHomeControllerView) {
        if (busHomeControllerView.getOnClickTag() == 1) {
            mPresenter.getLike(getActivity(), busHomeControllerView.getControllerView(), busHomeControllerView.getVideoBean());
        }
        if (busHomeControllerView.getOnClickTag() == 2) {
            videoId = busHomeControllerView.getVideoBean().getId();
            controllerView = busHomeControllerView.getControllerView();
            showSheetDialog(busHomeControllerView.getVideoBean().getId());
        }
        if (busHomeControllerView.getOnClickTag() == 3) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(CommMsg commMsg) {
        Log.i(TAG, "getEventBus" + videoId + commMsg.getMsg());
        mPresenter.writeComment(context, videoId, commMsg.getMsg());
    }

    public void show() {
        Log.i(TAG, "show");
        if (videoListView != null) {
            videoPlayView.onResume();
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        Log.i("Test", "onStop");
        videoListView.setOnBackground(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Test", "onStart");
//        setOnBackground(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoPlayView != null) {
            videoPlayView.onResume();
            Log.i("life", "local+videoPlayView.onResume();");
        }
        Log.i("life", "local+onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoPlayView != null) {
            videoPlayView.onPause();
            Log.i("life", "local+videoPlayView.onPause()");
        }
        Log.i("life", "local+onPause");
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
        WeakReference<LocalFragment> weakReference;

        MyRefreshDataListener(LocalFragment activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onRefresh() {
            LocalFragment friendsVideoFragment = weakReference.get();
            if (friendsVideoFragment != null) {
                friendsVideoFragment.isLoadMoreData = false;
                friendsVideoFragment.mLastVideoId = 0;
                friendsVideoFragment.refreshPlayList(friendsVideoFragment.mLastVideoId);
                Log.i(TAG, "视频播放列表刷新、加载更多事件监听onRefresh");
            }
        }

        @Override
        public void onLoadMore() {
            LocalFragment friendsVideoFragment = weakReference.get();
            if (friendsVideoFragment != null) {
                friendsVideoFragment.isLoadMoreData = true;
                friendsVideoFragment.loadPlayList(friendsVideoFragment.mLastVideoId);
                Log.i(TAG, "视频播放列表刷新、加载更多事件监听onLoadMore");
            }
        }
    }


    /**
     *上拉获取播放列表数据
     * @param id 请求第pageNo页数据
     */
    private void loadPlayList(final long id) {
        pagSize++;
        mPresenter.getVideoInfo(context, pagSize, videoPlayView);
        Log.d(TAG, "loadPlayList+pagSize:" + pagSize);
    }


    /**
     *下拉获取播放列表数据
     * @param id 请求第pageNo页数据
     */
    private void refreshPlayList(final long id) {
        pagSize = 0;
        mPresenter.getVideoInfo(context, pagSize, videoPlayView);
        Log.d(TAG, "refreshPlayList+pagSize:" + pagSize);
    }

    private void showSheetDialog(long videoId) {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.show();
            mPresenter.getComments(getActivity(), videoId);
            return;
        }
        View view = View.inflate(context, R.layout.dialog_bottomsheet, null);
        ImageView iv_dialog_close = (ImageView) view.findViewById(R.id.dialog_bottomsheet_iv_close);
        rv_dialog_lists = (RecyclerView) view.findViewById(R.id.dialog_bottomsheet_rv_lists);
        RelativeLayout rl_comment = view.findViewById(R.id.rl_comment);

        iv_dialog_close.setOnClickListener(v -> bottomSheetDialog.dismiss());

        rl_comment.setOnClickListener(v -> {
            initInputTextMsgDialog(null, false, null, -1);
        });
        mPresenter.getComments(getActivity(), videoId);
        bottomSheetAdapter = new CommentDialogSingleAdapter(context, this);
//        bottomSheetAdapter.setNewData(data);
        rv_dialog_lists.setHasFixedSize(true);
        rv_dialog_lists.setLayoutManager(new LinearLayoutManager(context));
        rv_dialog_lists.setItemAnimator(new DefaultItemAnimator());
        bottomSheetAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                int id = view.getId();
                switch (id) {
                    case R.id.rl_group:
                        ToastUtil.showToast("点击了rl_group");
                        break;
                    case R.id.ll_like:
                        ToastUtil.showToast("点击了ll_like");
                        break;

                }
            }
        });
//        bottomSheetAdapter.setLoadMoreView(new SimpleLoadMoreView());
//        bottomSheetAdapter.setOnLoadMoreListener(this, rv_dialog_lists);
        rv_dialog_lists.setAdapter(bottomSheetAdapter);

        initListener();

        bottomSheetDialog = new BottomSheetDialog(context, R.style.dialog);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        final BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        mDialogBehavior.setPeekHeight(getWindowHeight());
        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                    bottomSheetDialog.dismiss();
                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    if (slideOffsets <= -0.28) {
                        bottomSheetDialog.dismiss();
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                slideOffsets = slideOffset;

            }
        });
    }

    private void initListener() {
        // 点击事件
        bottomSheetAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            FirstLevelBean firstLevelBean = bottomSheetAdapter.getData().get(position);
            if (firstLevelBean == null) return;
            if (view1.getId() == R.id.ll_like) {
                //一级评论点赞 项目中还得通知服务器 成功才可以修改
                firstLevelBean.setLikeCount(firstLevelBean.getLikeCount() + (firstLevelBean.getIsLike() == 0 ? 1 : -1));
                firstLevelBean.setIsLike(firstLevelBean.getIsLike() == 0 ? 1 : 0);
                data.set(position, firstLevelBean);
                bottomSheetAdapter.notifyItemChanged(firstLevelBean.getPosition());
            } else if (view1.getId() == R.id.rl_group) {
                //添加二级评论
                initInputTextMsgDialog((View) view1.getParent(), false, firstLevelBean.getHeadImg(), position);
            }
        });
        //滚动事件
        if (mRecyclerViewUtil != null) mRecyclerViewUtil.initScrollListener(rv_dialog_lists);
    }

    private void initInputTextMsgDialog(View view, final boolean isReply, final String headImg, final int position) {
        dismissInputDialog();
        if (view != null) {
            offsetY = view.getTop();
            scrollLocation(offsetY);
        }
        if (inputTextMsgDialog == null) {
            inputTextMsgDialog = new InputTextMsgDialog(context, R.style.dialog_center);
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
                    Log.d(TAG, "onTextSend: " + msg);
                    addComment(isReply, headImg, position, msg);
                }

                @Override
                public void dismiss() {
                    scrollLocation(-offsetY);
                }
            });
        }
        showInputTextMsgDialog();

    }

    // item滑动到原位
    public void scrollLocation(int offsetY) {
        try {
            rv_dialog_lists.smoothScrollBy(0, offsetY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 重新排列数据
     * 未解决滑动卡顿问题
     */
    private void sort() {
        int size = data.size();
        for (int i = 0; i < size; i++) {
            FirstLevelBean firstLevelBean = data.get(i);
            firstLevelBean.setPosition(i);

            List<SecondLevelBean> secondLevelBeans = firstLevelBean.getSecondLevelBeans();
            if (secondLevelBeans == null || secondLevelBeans.isEmpty()) continue;
            int count = secondLevelBeans.size();
            for (int j = 0; j < count; j++) {
                SecondLevelBean secondLevelBean = secondLevelBeans.get(j);
                secondLevelBean.setPosition(i);
                secondLevelBean.setChildPosition(j);
            }
        }

        bottomSheetAdapter.notifyDataSetChanged();

    }

    private void dismissInputDialog() {
        if (inputTextMsgDialog != null) {
            if (inputTextMsgDialog.isShowing()) inputTextMsgDialog.dismiss();
            inputTextMsgDialog.cancel();
            inputTextMsgDialog = null;
        }
    }

    private void showInputTextMsgDialog() {
        inputTextMsgDialog.show();
    }

    private void addComment(boolean isReply, String headImg, final int position, String msg) {
        if (position >= 0) {
            //添加二级评论
            SecondLevelBean secondLevelBean = new SecondLevelBean();
            FirstLevelBean firstLevelBean = bottomSheetAdapter.getData().get(position);
            secondLevelBean.setReplyUserName("replyUserName");
            secondLevelBean.setIsReply(isReply ? 1 : 0);
            secondLevelBean.setContent(msg);
            secondLevelBean.setHeadImg(headImg);
            secondLevelBean.setCreateTime(System.currentTimeMillis());
            secondLevelBean.setIsLike(0);
            secondLevelBean.setUserName("userName");
            secondLevelBean.setId(firstLevelBean.getSecondLevelBeans() + "");
            firstLevelBean.getSecondLevelBeans().add(secondLevelBean);

            data.set(firstLevelBean.getPosition(), firstLevelBean);
            bottomSheetAdapter.notifyDataSetChanged();
            rv_dialog_lists.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((LinearLayoutManager) rv_dialog_lists.getLayoutManager())
                            .scrollToPositionWithOffset(position == data.size() - 1 ? position
                                    : position + 1, position == data.size() - 1 ? Integer.MIN_VALUE : rv_dialog_lists.getHeight() / 2);
                }
            }, 100);

        } else {
            //添加一级评论
            FirstLevelBean firstLevelBean = new FirstLevelBean();
            firstLevelBean.setUserName("赵丽颖");
            firstLevelBean.setId(bottomSheetAdapter.getItemCount() + 1 + "");
            firstLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
            firstLevelBean.setCreateTime(System.currentTimeMillis());
            firstLevelBean.setContent(msg);
            firstLevelBean.setLikeCount(0);
            firstLevelBean.setSecondLevelBeans(new ArrayList<SecondLevelBean>());
            data.add(0, firstLevelBean);
            sort();
            rv_dialog_lists.scrollToPosition(0);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("addUrl", "isVisibleToUser:" + isVisibleToUser);
        if (!isVisibleToUser) {
            this.isVisibleToUserInFrag = true;
            Log.i("addUrl", "isVisibleToUserInFrag:" + isVisibleToUserInFrag);
        } else {
            this.isVisibleToUserInFrag = isVisibleToUser;
            Log.i("addUrl", "isVisibleToUserInFrag:" + isVisibleToUserInFrag);
        }
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
            mPresenter.getVideoInfo(context, pagSize, videoPlayView);
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
    public void onGetLike(GiveLikeModel giveLikeModel, ControllerView controllerView, VideoBean videoBean) {
        controllerView.like(giveLikeModel.isBody(), videoBean);
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
        bottomSheetAdapter.setNewData(giveCommentsModel.getBody().getRecords());
    }

    //写评论成功回调
    @Override
    public void onWriteComment(GiveLikeModel giveLikeModel) {
        mPresenter.getComments(context, videoId);
        controllerView.commMsg(giveLikeModel.isBody());
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
