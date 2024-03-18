package com.yangna.lbdsp.common.eventbus;

import com.yangna.lbdsp.videoCom.ControllerView;
import com.yangna.lbdsp.videoCom.VideoBean;

public class BusHomeControllerView {
    private ControllerView controllerView;
    private VideoBean videoBean;
    private int onClickTag;

    public BusHomeControllerView(ControllerView controllerView, VideoBean videoBean) {
        this.controllerView = controllerView;
        this.videoBean = videoBean;
    }

    public BusHomeControllerView(ControllerView controllerView, VideoBean videoBean, int onClickTag) {
        this.controllerView = controllerView;
        this.videoBean = videoBean;
        this.onClickTag = onClickTag;
    }

    public int getOnClickTag() {
        return onClickTag;
    }

    public void setOnClickTag(int onClickTag) {
        this.onClickTag = onClickTag;
    }

    public ControllerView getControllerView() {
        return controllerView;
    }

    public void setControllerView(ControllerView controllerView) {
        this.controllerView = controllerView;
    }

    public VideoBean getVideoBean() {
        return videoBean;
    }

    public void setVideoBean(VideoBean videoBean) {
        this.videoBean = videoBean;
    }
}
