package com.yangna.lbdsp.mine.impl;

import com.yangna.lbdsp.home.model.VideoListModel;

public interface PersonalView {
    void onInitData(VideoListModel VideoListModel);

    void onMineVideo(VideoListModel VideoListModel);

    void onUserLoveVideo(VideoListModel VideoListModel);
}
