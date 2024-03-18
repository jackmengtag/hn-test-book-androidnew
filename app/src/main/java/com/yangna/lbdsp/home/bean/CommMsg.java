package com.yangna.lbdsp.home.bean;

public class CommMsg {
    private String msg;
    private Boolean isIask;

    public Boolean getIask() {
        return isIask;
    }

    public void setIask(Boolean iask) {
        isIask = iask;
    }

    public CommMsg(String msg) {
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
