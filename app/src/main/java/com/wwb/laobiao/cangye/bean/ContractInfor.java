package com.wwb.laobiao.cangye.bean;

public class ContractInfor {
    private long accountId;
    public void setaccountId( long accountId) {
        this.accountId=accountId;
    }
//     "accountId": 0,
//             "contractContents": "string",
//             "id": 0,
//             "title": "string",
//             "version": "string"
    private String contractContents;
    private  int id;
    private String title;
    private String version;
    public void settitle( String title) {
        this.title=title;
    }
}
