package com.wwb.laobiao.cangye.bean.Friend;

public class FriendapplyInfor {
//    private long accountId;
    public long  adressId;
    public long  amount;//channel
    public String channel;//支付渠道 01-支付宝 02-微信支付
    public String goodsId;//商品id
    public String money;//支付金额
    public String orderDesc; //订单描述
    public String productSpecficationId; //规格id

    public void setadressId( long adressId) {
        this.adressId=adressId;
    }
    public void setgoodsId(String goodsId) {
        this.goodsId=goodsId;
    }
    public void setamount(int getamount) {
        amount=getamount;
    }

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

}
