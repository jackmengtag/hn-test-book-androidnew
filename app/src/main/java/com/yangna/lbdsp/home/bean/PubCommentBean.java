package com.yangna.lbdsp.home.bean;

public class PubCommentBean {
    private String contents;
    private int parentId;
    private long videoId;
    private String viewAuth;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public String getViewAuth() {
        return viewAuth;
    }

    public void setViewAuth(String viewAuth) {
        this.viewAuth = viewAuth;
    }
}
