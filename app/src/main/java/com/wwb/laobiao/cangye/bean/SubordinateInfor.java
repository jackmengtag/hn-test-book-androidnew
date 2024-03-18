package com.wwb.laobiao.cangye.bean;

public class SubordinateInfor {
    private long accountId;//tier
    private String tier;//
//      "accountId": 0,
//              "tier": "string"
    public void setaccountId( long accountId) {
        this.accountId=accountId;
    }

    public void settier(int tierval) {
        tier=String.format("%d",tierval);
//        tier=""+tierval;
    }
}
