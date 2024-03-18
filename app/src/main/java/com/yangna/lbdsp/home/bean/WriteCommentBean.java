package com.yangna.lbdsp.home.bean;

public class WriteCommentBean {
    private int parentId;
    private int videoId;
    private int viewAuth;
    private String contents;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getViewAuth() {
        return viewAuth;
    }

    public void setViewAuth(int viewAuth) {
        this.viewAuth = viewAuth;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
