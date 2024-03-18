package com.yangna.lbdsp.mine.impl;

import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.login.model.UserInfoModel;

public interface MineView {

    void onGetMineInfo(UserInfoModel model);

    void onUserLoveVideo(VideoListModel VideoListModel);
}
