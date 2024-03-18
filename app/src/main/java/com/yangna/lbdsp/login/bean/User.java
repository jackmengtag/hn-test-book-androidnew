package com.yangna.lbdsp.login.bean;

public class User {
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private String area;
    private String deviceToken;
    private String mobile ;
    private String promotionCode;
    private String verifyCode;
//"area": "string",
//        "deviceToken": "string",
//        "mobile": "string",
//        "promotionCode": "string",
//        "verifyCode": "string"


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
}

    public void setDeviceToken(String deviceToken) {
        this.deviceToken=deviceToken;
    }

    public void setpromotionCode(String promotionCode) {
     this.promotionCode=promotionCode;
    }
}
