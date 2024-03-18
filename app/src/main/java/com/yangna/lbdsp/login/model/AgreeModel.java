package com.yangna.lbdsp.login.model;


import com.yangna.lbdsp.common.base.BaseModel;

/**
 * artifact
 */

public class AgreeModel extends BaseModel {
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AgreeModel() {
    }

    public AgreeModel(String body) {
        this.body = body;
    }
}
