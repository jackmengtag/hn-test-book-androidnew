package com.yangna.lbdsp.msg.model;

import android.util.Log;

import java.io.Serializable;

public class UsMsgModel implements Serializable {
    private static final String TAG = UsMsgModel.class.getName();
    public String instructions;
    public String name;
    public String time;
       public UsMsgModel()
       {
           instructions="这是内容";
           name="我是爸爸";
           time="09:22";
       }
}
