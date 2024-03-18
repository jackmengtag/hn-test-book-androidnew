package com.wwb.laobiao.cangye.bean;
import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;
import java.util.List;

public class OrderListModel extends BaseModel implements Serializable {
    public Body body;
    public class OrderBody {
        public	String     carrier;//	承运人	string
        public	String     createTime;//	创建时间	string
        public	int     deleted;//	逻辑删除	integer(int32)
        public	double     deliverFee;//	运费	number(double)
        public	double     discountAmount;//	优惠金额	number(double)
        public	String     editTime;//	修改时间	string
        public	String     id;//		string
        public	String     imageUrl;//	商品图片	string
        public	int     isDeliverGoods;//	是否已发货(0=未发货，1=已发货)	integer(int32)
        public	int     isReturnGoods;//	是否已退货(0=未退，1=已退)	integer(int32)
        public	String     orderSn;//	订单号	string
        public	int     orderStatus;//	订单状态(0=未支付，1=已支付，待发货，2=已发货，待收货，3=待核销,4=待自提,5=已收货，待评价，6=交易完成，7=已关闭，8=已退款,9=待退款，10=全部，11=已拒绝退款，12=申请退货，13=同意退货)	integer(int32)
        public	String     payTime;//	支付时间	string
        public	int     payType;//	支付方式(0==微信，1=支付宝，3=银行卡,4=消费)	integer(int32)
        public	String     productName;//	商品名称	string
        public	double     realPayAmount;//	实付金额	number(double)
        public	String     receiverAddress;//	收货人地址	string
        public	String     receiverPhone;//	收货人手机号	string
        public	String     receveidName;//	收货人姓名	string
        public	String     shippingType;//	配送方式	string
        public	String     shopId;//	商家	string
        public	String     shoppingUser;//	买家	string
        public	double     totalAmount;//	订单金额（总金额）	number(double)
        public	String     virificationCode;//	核销码
    }
    public class Body {
        public   List<OrderBody>list;
    }
}
