package com.wwb.laobiao.Search.impl;

public class Hotguss {

    private String name0 ;
    private String name1 ;
    public Hotguss() {
        name0="test0";
        name1="test1";
    }
    public String getName(int sky) {
        if(sky==0)
        {
            return name0;
        }
        else
        {
            return name1;
        }

    }

    public void SetName(String string,int sky) {
        if(sky==0)
        {
            name0 =string;
        }
        else
        {
            name1=string;
        }

    }
}
