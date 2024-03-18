package com.wwb.laobiao.hongbao.adapter;

import android.graphics.Bitmap;

public class Yaoqing {
    public int sky;
    public String name;
    public Bitmap bmp;

    public Yaoqing()
    {
        name="";
    }
    public String getyaoqing() {
//        return "http://lbdsp.com/";
        return "http://192.168.1.9:8080/lbdsp/release/app-release.apk";
//        return "http://39.108.150.225:8080/swagger-ui.html#!/account-controller/searchAccountUsingPOST";
    }
}
