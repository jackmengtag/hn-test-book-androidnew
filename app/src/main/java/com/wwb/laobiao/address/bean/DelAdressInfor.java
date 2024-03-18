package com.wwb.laobiao.address.bean;

public class DelAdressInfor {
    public static final String KEY = "DelAdressInfor";
    //    private long accountId;
//    private String  address;
//    private String  mobile;
    public DelAdressInfor()
    {

    }
    public String getname() {
        return consignee;
    }
    public void setname(String name) {
        this.consignee=name;
    }
    public void setaccountId( long accountId) {
        this.accountId=accountId;
    }
    public void setaddress(String address) {
        this.address=address;
    }
    public void setmobile(String mobile) {
        this.mobile=mobile;
    }
    public long    accountId		;//	false
    // public long    integer(int64)
    public String    address		;//	false
    // public long    string
    public String    areaId		;//	false
    // public long    string
    public String    cityId		;//	false
    // public long    string
    public String    consignee		;//	false
    // public long    string
    public int    deleted		;//	false
    // public long    integer(int32)
    public long    id		;//	false
    // public long    integer(int64)
    public int    isDefault		;//	false
    // public long    integer(int32)
    public double    latitude		;//	false
    // public long    number(double)
    public double    longitude		;//	false
    // public long    number(double)
    public String    mobile		;//	false
    // public long    string
    public String    provinceId	;//		false
    // public long    string
    public String    streetId		;//	false

    public void set(AdressInfor user) {
        accountId=user.accountId;
        address=user.address;
        areaId=user.areaId;
        cityId=user.cityId;
        consignee=user.consignee;
        deleted=user.deleted;
        id=user.id;
        isDefault=user.isDefault;
        latitude=user.latitude;
        longitude=user.longitude;
        mobile=user.mobile;
        provinceId=user.provinceId;
        streetId=user.streetId;
    }

    public void setId(long getid) {
        id=getid;
    }
    // public long    string
}
