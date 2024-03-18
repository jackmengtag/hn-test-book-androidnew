package com.wwb.laobiao.common;

import com.umeng.message.entity.UMessage;

import java.io.Serializable;

public class ChatUMessage  implements Serializable {

    public String customstr;
    public String titlestr;
    public String textstr;


    public void setmsg(UMessage msg) {
        customstr=msg.custom;
        titlestr=msg.title;
        textstr=msg.text;
    }
}
