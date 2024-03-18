package com.wwb.laobiao.cangye.bean;
import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;

public class PayModel extends BaseModel implements Serializable {
    OrderBody body;
    public class OrderBody {
        public   String   appId;//	微信APP支付返回	String
        public   String appSign	;//微信APP支付返回	String
        public   String channel	;//支付渠道	String
        public    String money	;//支付金额	String
        public    String nonceStr	;//微信APP/小程序支付返回	String
        public    String  orderNo	;//支付订单号	String
        public    String  packageStr	;//微信APP/小程序支付返回	String
        public    String  partnerId	;//微信APP支付返回	String
        public    String  payDate	;//支付日期	String
        public    String  payInfo	;//支付宝支付返回	String
        public    String  paySign	;//微信小程序支付返回	String
        public    String  prepayId ;//	微信APP支付返回	String
        public    String  signType;//	微信APP/小程序支付返回 默认:MD5	String
        public    String  timeStamp;//	微信APP/小程序支付返回	String
    }

    public OrderBody getBody() {
        return body;
    }
}
