package com.yangna.lbdsp.home.presenter;

import android.content.Context;
import android.util.Log;

import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.home.bean.GainVideoBean;
import com.yangna.lbdsp.home.bean.GiveComments;
import com.yangna.lbdsp.home.bean.GiveLikeBean;
import com.yangna.lbdsp.home.bean.PubCommentBean;
import com.yangna.lbdsp.home.bean.WriteCommentBean;
import com.yangna.lbdsp.home.impl.VideoListView;
import com.yangna.lbdsp.home.model.GiveCommentsModel;
import com.yangna.lbdsp.home.model.GiveLikeModel;
import com.yangna.lbdsp.home.model.VideoDetailModel;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.home.model.VideoWelfareModel;
import com.yangna.lbdsp.videoCom.AliPlay.view.AlivcVideoPlayView;
import com.yangna.lbdsp.videoCom.ControllerView;
import com.yangna.lbdsp.videoCom.VideoBean;

public class VideolistPresenter extends BasePresenter {

    private VideoListView videoListView;
    private static final String TAG = "LocalFragment";

    //主页回调
    private VideolistPresenter videolistPresenter;


    public VideolistPresenter(Context context) {
        super(context);
    }

    @Override
    protected void detachView() {
        videoListView = null;
    }

    public void setVideoListView(VideoListView videoListView) {
        this.videoListView = videoListView;
    }

    /**
     * 获取首页视频
     */
    public void getVideoInfo(final Context context, int currentPage, AlivcVideoPlayView videoPlayView) {
        GainVideoBean gainVideoBean = new GainVideoBean();
        gainVideoBean.setCurrentPage(currentPage);
        gainVideoBean.setCurrentSize(10);
        Log.e("Test", "" + "currentPage" + currentPage);
        NetWorks.getInstance().getGainVideo(context, gainVideoBean, new MyObserver<VideoListModel>() {


            @Override
            public void onNext(VideoListModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        Log.e("播放视频过程", "" + "成功获取到首页视频");
                        videoListView.onGetGainVideo(model);
                        videoListView.onGetDataLoading(true);
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
                videoListView.onNetLoadingFail();
            }


        });
    }

    /**
     * 获取推介视频
     */
    public void getPromoteVideo(final Context context, int pagSize, AlivcVideoPlayView videoPlayView) {
        GainVideoBean gainVideoBean = new GainVideoBean();
        gainVideoBean.setCurrentPage(pagSize);
        gainVideoBean.setCurrentSize(10);
        Log.i("addUrl", "pagSize=" + pagSize );
        NetWorks.getInstance().getPromoteVideo(context, gainVideoBean, new MyObserver<VideoListModel>() {

            @Override
            public void onNext(VideoListModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        Log.e("播放视频过程", "" + "成功获取到首页视频");
                        videoListView.onGetGainVideo(model);
                        videoListView.onGetDataLoading(true);
                    } else {
                        videoPlayView.loadFailure();
                        ToastManager.showToast(context, model.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
                videoListView.onNetLoadingFail();
            }


        });
    }


    /**
     * 观看视频福利
     */
    public void getLookWelfare(final Context context) {
        GiveLikeBean giveLikeBean = new GiveLikeBean();

        NetWorks.getInstance().getVideoWelfare(context, new MyObserver<VideoWelfareModel>() {


            @Override
            public void onNext(VideoWelfareModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        videoListView.onGetWelfareModel(model);
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }


        });
    }

    /**
     * 获取视频详情
     */
    public void getStatisticalVideo(final Context context, long videoId) {
        GiveLikeBean gainVideoBean = new GiveLikeBean();
        gainVideoBean.setVideoId(videoId);

        NetWorks.getInstance().getStatisticalVideo(context, gainVideoBean, new MyObserver<VideoDetailModel>() {
            @Override
            public void onNext(VideoDetailModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        videoListView.onStatisticalVideo(model);
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }


        });
    }


    /**
     * 点赞视频
     */
    public void getLike(final Context context, ControllerView controllerView, VideoBean videoBean) {
        GiveLikeBean giveLikeBean = new GiveLikeBean();
        giveLikeBean.setVideoId(videoBean.getId());

        NetWorks.getInstance().getGiveLike(context, giveLikeBean, new MyObserver<GiveLikeModel>() {


            @Override
            public void onNext(GiveLikeModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        videoListView.onGetLike(model, controllerView,videoBean);
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }


        });
    }

    /**
     * 获取评论
     */
    public void getComments(final Context context, long videoId) {
        GiveComments giveComments = new GiveComments();
        giveComments.setCurrentPage(1);
        giveComments.setParentId(0);
        giveComments.setShowCount(1000);
        giveComments.setVideoId(videoId);

        NetWorks.getInstance().getComments(context, giveComments, new MyObserver<GiveCommentsModel>() {


            @Override
            public void onNext(GiveCommentsModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        Log.i(TAG, "获取评论" + videoId + "视频id");
                        videoListView.onGetComments(model);
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }


        });
    }


    /**
     * 写评论
     */
    public void writeComment(final Context context, long videoId, String contents) {
        PubCommentBean writeCommentBean = new PubCommentBean();
        writeCommentBean.setContents(contents);
        writeCommentBean.setParentId(0);
        writeCommentBean.setViewAuth("0");
        writeCommentBean.setVideoId(videoId);

        NetWorks.getInstance().WriteComment(context, writeCommentBean, new MyObserver<GiveLikeModel>() {


            @Override
            public void onNext(GiveLikeModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        Log.i(TAG, "评论成功 " + videoId + "视频id");
                        videoListView.onWriteComment(model);
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }


        });
    }

    /**
     * loading页面
     */
    public void initLoadingView() {
        videoListView.onGetDataLoading(false);
    }


}
