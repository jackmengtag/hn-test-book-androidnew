package com.yangna.lbdsp.login.model;


import com.yangna.lbdsp.common.base.BaseModel;

/**
 * artifact
 */

public class DisAgreeModel extends BaseModel {
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public DisAgreeModel() {
    }

    public DisAgreeModel(String body) {
        this.body = body;
    }
}
