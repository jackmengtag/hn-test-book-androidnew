package com.wwb.laobiao.address.bean;

import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;

public class UserAdressModel extends BaseModel implements Serializable {
    public static final String SER_KEY ="SER_KEY" ;
    String address;
    String mobile;
    String consignee;
    long id;
    long accountId;

    public void setmobile(String s) {
        mobile=s;
    }
    public void setaddress(String s) {
        address=s;
    }

    public String getmobile() {
        return mobile;
    }


    public String getaddress() {
        return address;
    }

    public void setid(long getid) {
        id=getid;
    }

    public long getid() {
        return id;
    }

    public String getname() {
        return consignee;
    }
    public void setname(String name) {
        this.consignee=name;
    }
//    "accountId": 0,
//            "address": "string",
//            "id": 0,
//            "mobile": "string"

}
