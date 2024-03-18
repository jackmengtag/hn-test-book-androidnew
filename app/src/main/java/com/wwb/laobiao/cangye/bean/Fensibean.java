package com.wwb.laobiao.cangye.bean;

public class Fensibean {
    public boolean headbf;
    private String name ;
    private int imageId;
    private long num;
    public Fensibean() {
        headbf=false;
        num=0;
    }
    public int getImageId() {
        return imageId;
    }
    public String getName() {
        return name;
    }

    public String getnumstr() {
        return num+"个";
    }

    public void setnum(long inum) {
        num=inum;
    }

    public void setname(String Strings0) {
        name=Strings0;
    }
}
