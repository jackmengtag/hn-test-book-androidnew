package com.wwb.laobiao.address.bean;

import java.io.Serializable;

public class AdressInfor implements Serializable {
	public static final String KEY = "AdressInfor";
//    private long accountId;
//    private String  address;
//    private String  mobile;
	public AdressInfor()
	{
//		consignee="维维";
//		address="航生路2号";
//		mobile="13387956939";
		consignee="";
		address="";
		mobile="";
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

	public void setId(long getid) {
		id=id;
	}
	// public long    string
}
