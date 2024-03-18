package com.yangna.lbdsp.home.impl;

import com.yangna.lbdsp.home.model.AppSettingModel;
import com.yangna.lbdsp.home.model.VideoIdModel;
import com.yangna.lbdsp.login.bean.CreateFlie;
import com.yangna.lbdsp.login.model.FileModel;

public interface HomeView {

    void onGetDataLoading(boolean isSuccess);

    void onNetLoadingFail();

    void onGetUploadAddresData(FileModel fileModel, CreateFlie createFlie);

    void onGetUploadVideo(VideoIdModel videoIdModel);

    void onGetAppVersion(AppSettingModel appSettingModel);


}
