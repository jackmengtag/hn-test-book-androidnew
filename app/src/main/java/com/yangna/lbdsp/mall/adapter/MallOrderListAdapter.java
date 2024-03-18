package com.yangna.lbdsp.mall.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wwb.laobiao.address.FukuanActivity;
import com.wwb.laobiao.cangye.bean.PayInfor;
import com.wwb.laobiao.cangye.bean.PayModel;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.ListBaseAdapter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.utils.StringUtils;
import com.yangna.lbdsp.common.utils.TextStyleUtils;
import com.yangna.lbdsp.common.utils.TimeUtils;
import com.yangna.lbdsp.mall.impl.ClickListener;
import com.yangna.lbdsp.mall.view.CommentsActivity;
import com.yangna.lbdsp.mall.model.MallOrderListModel;
import com.yangna.lbdsp.wxapi.WXPayEntryActivity;

import java.util.HashMap;
import java.util.Map;

import static com.yangna.lbdsp.wxapi.util.WeiXinConstants.APP_ID;


/**
 * 订单管理adapter
 */
public class MallOrderListAdapter extends ListBaseAdapter<MallOrderListModel.MallOrderList> {

    private TextStyleUtils.TextStyle ts = new TextStyleUtils.TextStyle(ContextCompat.getColor(context, R.color.text_color_black), 10);

    private ClickListener mClickListener;

    public MallOrderListAdapter(Context context, ClickListener clickListener) {
        super(context);
        mClickListener = clickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        final MallOrderListModel.MallOrderList model = getItem(position);

        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_mall_order_list_item, null);
            holder = new ViewHolder();
            holder.type_tv = convertView.findViewById(R.id.type_tv);
            holder.status_tv = convertView.findViewById(R.id.status_tv);
            holder.name_tv = convertView.findViewById(R.id.name_tv);
            holder.money_tv = convertView.findViewById(R.id.money_tv);
            holder.time_tv = convertView.findViewById(R.id.time_tv);
            holder.goods_img = convertView.findViewById(R.id.goods_img);
            holder.ivIcon = convertView.findViewById(R.id.ivIcon);
            holder.one = convertView.findViewById(R.id.one);
            holder.two = convertView.findViewById(R.id.two);
            holder.three = convertView.findViewById(R.id.three);
            holder.four = convertView.findViewById(R.id.four);
            holder.status_time = convertView.findViewById(R.id.status_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.log);
        Glide.with(context).asDrawable().load(model.getImageUrl()).apply(options).into(holder.goods_img);

        Glide.with(context).asDrawable().load(model.getShoplogo()).apply(options).into(holder.ivIcon);

        if (model.getCreateTime() != null) {
            holder.status_time.setText(model.getCreateTime());
        }

        if (model.getShopName() != null) {
            holder.type_tv.setText(model.getShopName());
        } else {
            holder.type_tv.setText("暂时没有店铺的名字");
        }
//
//        // 订单状态 0待充值 1充值中 2已充值 3充值失败 4已失效
//        if (model.getStatus() != null && model.getStatus().equals("0")) {
//            holder.status_tv.setText("待支付");
//            holder.status_tv.setTextColor(ContextCompat.getColor(context,R.color.home_check));
//        } else if (model.getStatus() != null && model.getStatus().equals("1")) {
//            holder.status_tv.setText("支付中");
//            holder.status_tv.setTextColor(ContextCompat.getColor(context,R.color.text_color_black));
//        } else if (model.getStatus() != null && model.getStatus().equals("2")) {
//            holder.status_tv.setText("已支付");
//            holder.status_tv.setTextColor(ContextCompat.getColor(context,R.color.green));
//            if (model.getSendStatus() != null && model.getSendStatus().equals("0")) {
//                holder.status_tv.setText("待发货");
//                holder.status_tv.setTextColor(ContextCompat.getColor(context,R.color.green));
//            } else if (model.getSendStatus() != null && model.getSendStatus().equals("1")) {
//                holder.status_tv.setText("已发货");
//                holder.status_tv.setTextColor(ContextCompat.getColor(context,R.color.green));
//            } else if (model.getSendStatus() != null && model.getSendStatus().equals("2")) {
//                holder.status_tv.setText("已完成");
//                holder.status_tv.setTextColor(ContextCompat.getColor(context,R.color.green));
//            }
//        } else if (model.getStatus() != null && model.getStatus().equals("3")) {
//            holder.status_tv.setText("订单超时");
//            holder.status_tv.setTextColor(ContextCompat.getColor(context,R.color.text_color_light_gray));
//        } else if (model.getStatus() != null && model.getStatus().equals("4")) {
//            holder.status_tv.setText("已失效");
//            holder.status_tv.setTextColor(ContextCompat.getColor(context,R.color.text_color_light_gray));
//        } else {
//            holder.status_tv.setText("");
//            holder.status_tv.setTextColor(ContextCompat.getColor(context,R.color.text_color_light_gray));
//        }
//
        //订单状态(0=未支付，1=已支付，待发货，2=已发货，待收货，3=待核销,4=待自提,5=已收货，待评价，6=交易完成，
        // 7=已关闭，8=已退款,9=待退款，10=全部，11=已拒绝退款，12=申请退货，13=同意退货, 99=售后 )
        if (model.getOrderStatus() != null && model.getOrderStatus().equals("10")) {
            holder.one.setText("再次购买");
            holder.two.setText("退货");
            holder.three.setVisibility(View.INVISIBLE);
            holder.four.setVisibility(View.INVISIBLE);
        } else if (model.getOrderStatus() != null && model.getOrderStatus().equals("0")) {
            holder.one.setText("付款");
            holder.two.setText("取消订单");
            holder.three.setVisibility(View.INVISIBLE);
            holder.four.setVisibility(View.INVISIBLE);
            holder.one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    IWXAPI wxapi = WXAPIFactory.createWXAPI(context, APP_ID, false);
                    MyObserver<PayModel> observerx = new MyObserver<PayModel>() {
                        @Override
                        public void onNext(PayModel superiorModel) {
                            if (superiorModel == null) {
                                ToastManager.showToast(context, "null");
                                return;//
                            } else {
                                if (superiorModel.getState() == 200) {
                                    PayReq req = new PayReq();
                                    req.appId = superiorModel.getBody().appId;//wx35e432b6a10cde0f
                                    req.partnerId = superiorModel.getBody().partnerId;
                                    req.prepayId = superiorModel.getBody().prepayId;//wx14084951483143a08538978f1409630000
                                    req.packageValue = superiorModel.getBody().packageStr;
                                    req.nonceStr = superiorModel.getBody().nonceStr;
                                    req.timeStamp = superiorModel.getBody().timeStamp;
                                    req.sign = superiorModel.getBody().appSign;//a1bec05ab8b5d0b921f63949e0454b18
                                    WXPayEntryActivity.orderNo = superiorModel.getBody().orderNo;
                                    WXPayEntryActivity.money = superiorModel.getBody().money;
                                    Log.d("wxapi:", req.sign);
                                    boolean result = wxapi.sendReq(req);
                                    Log.v("支付状态:=======", "支付成功....");
                                    Log.v("支付结果:=======", result + "");
                                    Toast.makeText(context, superiorModel.getMsg() + " 调起支付结果:" + result, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, superiorModel.getMsg(), Toast.LENGTH_LONG).show();
                                }
                                Log.d("getState:", "" + superiorModel.getState());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastManager.showToast(context, "onEr");
                        }
                    };
                    PayInfor user = new PayInfor();
                    user.setOrderId(model.getOrderSn());
                    user.setChannel(String.format("%02d", 2));
                    user.setMoney(String.format("%4.2f", model.getTotalAmount()));
                    NetWorks.getInstance().PayOrder(context, user, observerx);
                }
            });
        } else if (model.getOrderStatus() != null && model.getOrderStatus().equals("3")) {
            holder.one.setText("查看券码");
            holder.two.setText("申请退款");
            holder.three.setVisibility(View.INVISIBLE);
            holder.four.setVisibility(View.INVISIBLE);
        } else if (model.getOrderStatus() != null && model.getOrderStatus().equals("1")) {
            holder.one.setText("确定收货");
            holder.two.setText("查看物流");
            holder.three.setVisibility(View.INVISIBLE);
            holder.four.setVisibility(View.INVISIBLE);
        } else if (model.getOrderStatus() != null && model.getOrderStatus().equals("5")) {
            holder.one.setText("立即评价");
            holder.two.setText("再来一单");
            holder.three.setVisibility(View.INVISIBLE);
            holder.four.setVisibility(View.INVISIBLE);
            holder.two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(model.getOrderSn());
                }
            });
        } else if (model.getOrderStatus() != null && model.getOrderStatus().equals("12") || model.getOrderStatus().equals("13") || model.getOrderStatus().equals("11") || model.getOrderStatus().equals("9") || model.getOrderStatus().equals("8")) {
            holder.one.setText("查看详情");
            holder.two.setText("删除记录");
            holder.three.setVisibility(View.INVISIBLE);
            holder.four.setVisibility(View.INVISIBLE);
        }
        //名称
        if (model.getProductName() != null) {
            holder.name_tv.setText(model.getProductName());
        } else {
            holder.name_tv.setText("商品内容出错");
        }

        //订单金额
        holder.money_tv.setText(ts.clear()
                .spanColorAndSize("￥")
                .span(StringUtils.formatDouble(model.getTotalAmount()))
                .getText());


//        StringBuilder point = new StringBuilder(StringUtils.formatDouble(model.getMallPoint()));
//        StringBuilder money = new StringBuilder(StringUtils.formatDouble(model.getMoney()));
//        if (model.getMoney() == 0) {
//            //积分
//            if (model.getMallPoint() != 0) {
//                holder.money_tv.setText(point.append(context.getString(R.string.integral)));
//            }
//        } else {
//            //现金
//            if (model.getMallPoint() == 0) {
//                holder.money_tv.setText(money.insert(0,context.getString(R.string.unit_money)));
//            } else {//现金+积分
//                holder.money_tv.setText(money.insert(0,context.getString(R.string.unit_money))
//                        .append("+")
//                        .append(point.append(context.getString(R.string.integral)))
//
//                );
//            }
//        }
//
//        if (model.getPayDateStr() != null) {
//            holder.time_tv.setText(model.getPayDateStr());
//        } else {
//            holder.time_tv.setText(holder.status_tv.getText());
//        }
//
//        //去详情
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (model.getStatus().equals("0")) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", model.getId());
//                    if (model.getGoodsType().equals("1")) {
////                        UIManager.switcher(context, map, MallActualCheckStandActivity.class);
//                    } else {
////                        UIManager.switcher(context, map, MallCheckStandActivity.class);
//                    }
//                } else {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", model.getId());
//                    if (model.getGoodsType().equals("1")) {
////                        UIManager.switcher(context, map, MallActualOrderDetailsActivity.class);
//                    } else {
////                        UIManager.switcher(context, map, MallOrderDetailsActivity.class);
//                    }
//
//                }
//            }
//        });
        //去评价

        if (model.getOrderStatus() != null && model.getOrderStatus().equals("5")) {
            holder.one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("ordersn", model.getOrderSn());
                    UIManager.switcher(context, map, CommentsActivity.class);
                }
            });
        }

        return convertView;
    }

    class ViewHolder {
        TextView type_tv, status_tv, name_tv, money_tv, time_tv, status_time;
        ImageView goods_img, ivIcon;
        Button pingjia, one, two, three, four;
    }

}

