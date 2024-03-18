package com.yangna.lbdsp.home.impl;

import com.yangna.lbdsp.home.model.GiveCommentsModel;
import com.yangna.lbdsp.home.model.GiveLikeModel;
import com.yangna.lbdsp.home.model.VideoDetailModel;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.home.model.VideoWelfareModel;
import com.yangna.lbdsp.videoCom.ControllerView;
import com.yangna.lbdsp.videoCom.VideoBean;

public interface VideoListView {

    void onGetDataLoading(boolean isSuccess);

    void onNetLoadingFail();

    void onGetGainVideo(VideoListModel videoListModel);

    void onGetLike(GiveLikeModel giveLikeModel, ControllerView controllerView, VideoBean videoBean);

    void onGetWelfareModel(VideoWelfareModel videoWelfareModel);

    void onStatisticalVideo(VideoDetailModel videoDetailModel);


    void onGetComments(GiveCommentsModel giveCommentsModel);

    void onWriteComment(GiveLikeModel giveLikeModel);

    //评论回调
    void onGetCommentMsg(String msg);


}
