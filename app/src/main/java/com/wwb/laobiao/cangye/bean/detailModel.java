package com.wwb.laobiao.cangye.bean;
import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;

public class detailModel extends BaseModel implements Serializable {
    PayBody body;
    public class PayBody {
//        public   String   appId;//	微信APP支付返回	String
//        public   String appSign	;//微信APP支付返回	String
//        public   String channel	;//支付渠道	String
//        public    String money	;//支付金额	String
//        public    String nonceStr	;//微信APP/小程序支付返回	String
//        public    String  orderNo	;//支付订单号	String
//        public    String  packageStr	;//微信APP/小程序支付返回	String
//        public    String  partnerId	;//微信APP支付返回	String
//        public    String  payDate	;//支付日期	String
//        public    String  payInfo	;//支付宝支付返回	String
//        public    String  paySign	;//微信小程序支付返回	String
//        public    String  prepayId ;//	微信APP支付返回	String
//        public    String  signType;//	微信APP/小程序支付返回 默认:MD5	String
//        public    String  timeStamp;//	微信APP/小程序支付返回	String
public   String        carrier;//	承运人	string
public   String        createTime;//	创建时间	string(date-time)
public   String        defaultAddress;//	账户地址	TAccountAdress	TAccountAdress
public   String        deliverFee;//	运费	object
public   String        discountAmount;//	优惠金额	object
public   String        editTime;//	修改时间	string(date-time)
public   String        id;//		string
public   String        isDeliverGoods;//	是否已发货(0=未发货，1=已发货)	integer(int32)
public   String        isReturnGoods;//	是否已退货(0=未退，1=已退)	integer(int32)
public   String        orderDesc;//	订单描述	string
public   String        orderSn;//	订单号	string
public   String        orderStatus;//	订单状态(0=未支付，1=已支付，待发货，2=已发货，待收货，3=待核销,4=待自提,5=已收货，待评价，6=交易完成，7=已关闭，8=已退款,9=待退款，10=全部，11=已拒绝退款)	integer(int32)
public   String        payTime;//	支付时间	string(date-time)
public   String        payType;//	支付方式(0==微信，1=支付宝，3=银行卡,4=消费)	integer(int32)
public   String        productMiddleList;//	订单商品	array	订单-商品-中间表--列表
public   String        realPayAmount;//	实付金额	object
public   String        receiverAddress;//	收货人地址	string
public   String        receiverPhone;//	收货人电话	string
public   String        receveidName;//	收货人名称	string
public   String        shippingType;//	配送方式	string
public   String        shopId;//	商家	string
public   String        shoppingUser;//	买家	string
public   String        totalAmount;//	订单金额（总金额）	object
public   String        virificationCode;//	核销码

    }

    public PayBody getBody() {
        return body;
    }
}
