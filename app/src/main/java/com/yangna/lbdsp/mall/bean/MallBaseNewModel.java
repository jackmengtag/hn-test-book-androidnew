package com.yangna.lbdsp.mall.bean;

public class MallBaseNewModel {
    private MallBodyModel body;
    private int state;
    private String msg;

    public MallBodyModel getBody() {
        return body;
    }

    public void setBody(MallBodyModel body) {
        this.body = body;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
