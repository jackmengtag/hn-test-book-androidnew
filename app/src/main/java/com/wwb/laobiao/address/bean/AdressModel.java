package com.wwb.laobiao.address.bean;

import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;

public class AdressModel  extends BaseModel implements Serializable {
    public static final String SER_KEY ="SER_KEY" ;
    public String ProvinceId;
    public String CityId;
    public String Areid;
    public String provinceName;
    public String cityName;
    public String areaName;
    String address;
    String mobile;
    String consignee;
    long id;
    long accountId;
    private int isDefault;

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

    public void setDefault(int isDefault) {
        this.isDefault=isDefault;
    }

    public String getaddress0() {
        if(provinceName==null)
        {
            return null;
        }
        if(cityName==null)
        {
            return provinceName;
        }
        if(areaName==null)
        {
            return provinceName+cityName;
        }
        String str0=provinceName;
        str0+=cityName;
        str0+=areaName;
       return  str0;
    }
    public String getaddressall() {
        if(provinceName==null)
        {
            return null;
        }
        if(cityName==null)
        {
            return null;
        }
        if(areaName==null)
        {
            return null;
        }
        if(address==null)
        {
            return null;
        }

        String str0=provinceName;
        str0+=cityName;
        str0+=areaName;
        str0+=address;
        return  str0;
    }
//    "accountId": 0,
//            "address": "string",
//            "id": 0,
//            "mobile": "string"

}
