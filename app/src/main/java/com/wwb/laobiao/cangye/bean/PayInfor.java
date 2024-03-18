package com.wwb.laobiao.cangye.bean;

import java.util.ArrayList;
import java.util.List;

public class PayInfor {
    public String channel;//支付渠道 01-支付宝 02-微信支付
    public String money;//支付金额
    public String orderId;//订单Id
    public List<String>orderIdList;

    public void setChannel(String channel) {
        this.channel = channel;
    }
    public void setMoney(String money) {
        this.money = money;
    }
    public String getChannel() {
        return channel;
    }
    public String getMoney() {
        return money;
    }

    public List<String> getOrderIdList() {
        return orderIdList;
    }
    public void setOrderIdList(List<String> orderIdList) {
        this.orderIdList = orderIdList;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderId() {
        return orderId;
    }
}
