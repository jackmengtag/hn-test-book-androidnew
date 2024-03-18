package com.yangna.lbdsp.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wwb.laobiao.address.FukuanActivity;
import com.wwb.laobiao.cangye.bean.PayInfor;
import com.wwb.laobiao.cangye.bean.PayModel;
import com.wwb.laobiao.instance.UserOrder;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;

import static com.yangna.lbdsp.wxapi.util.WeiXinConstants.APP_ID;

//import static com.wwb.laobiao.wxapi.util.WeiXinConstants.APP_ID;
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler, View.OnClickListener {
    public static String orderNo="";
    public static String money="";
    public static String payDate;
    private final String TAG = "WXPayEntryActivity";
    LinearLayout mlaypay;
    LinearLayout mlay1;
    LinearLayout mlay2;
    LinearLayout mlay1mm;
    LinearLayout mlay2mm;
    RelativeLayout mlay0;
    private IWXAPI api;
    private TextView textView1;
    private TextView textView3;
    private Boolean bfpay;
//    private TextView textview0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        Activitytransfer();
//        mlay0 =findViewById(R.id.lay0);
//        mlay1 =findViewById(R.id.lay1);
//        mlay2 =findViewById(R.id.lay2);
        mlay1mm =findViewById(R.id.lay010);
        mlay2mm =findViewById(R.id.lay020);

//        mlaypay =findViewById(R.id.lay3);
        textView1=findViewById(R.id.textView1);
        textView3=findViewById(R.id.textView3);
	findViewById(R.id.textView1).setOnClickListener(this);//订单编号: 201811000
	findViewById(R.id.textView2).setOnClickListener(this);//实付金额
	findViewById(R.id.textView3).setOnClickListener(this);// 109.00元
	findViewById(R.id.textView4).setOnClickListener(this);//支付方式 : 微信支付
	findViewById(R.id.textView5).setOnClickListener(this);//查看订单
	findViewById(R.id.textView6).setOnClickListener(this);//再来一单
    findViewById(R.id.account_balance_back).setOnClickListener(this);//back

          if(bfpay)
          {
              onsucesspay();
          }
          else
          {
              onfailpay();
          }
    }
    private void Activitytransfer() {
        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();
        if(bundle!=null)
        {
            int pay = bundle.getInt("pay",-1);
            if(pay<0)
            {
                api = WXAPIFactory.createWXAPI(this, APP_ID);
                if(api!=null)
                {
                    api.handleIntent(getIntent(), this);
                }
            }
            else
            {
                if(pay==0)
                {
                    bfpay=false;
                }
                else
                {
                    bfpay=true;
                }
                Log.d("wxpay","pay "+pay);
            }

        }
        else
        {
            api = WXAPIFactory.createWXAPI(this, APP_ID);
            if(api!=null)
            {
                api.handleIntent(getIntent(), this);
            }
        }

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq req) {
     //   api.sendReq(req);
    }
//     *      0.成功
//     *      -1.错误
//     *      2.用户取消
    @Override
    public void onResp(BaseResp resp) {
        Log.i(TAG,"errCode = " + resp.errCode);
        //最好依赖于商户后台的查询结果
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //如果返回-1，很大可能是因为应用签名的问题。用官方的工具生成
            //签名工具下载：https://open.weixin.qq.com/zh_CN/htmledition/res/dev/download/sdk/Gen_Signature_Android.apk
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("提示");
//            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//            switch (resp.errCode)
//            {
//                case 0:
//                    builder.setMessage(String.format("微信支付结果：%s","支付成功"));
////                    if(textview0!=null)
////                    {
////                        textview0.setText(String.format("微信支付结果：%s","支付成功"));
////                    }
//                    break;
//                case -1:
//                    builder.setMessage(String.format("微信支付结果：%s",String.valueOf(resp.errCode)));
////                    if(textview0!=null)
////                    {
////                        textview0.setText(String.format("微信支付结果：%s","支付失败"));
////                    }
//                    break;
//                case 2:
//                    builder.setMessage(String.format("微信支付结果：%s","用户取消支付"));
////                    if(textview0!=null)
////                    {
////                        textview0.setText(String.format("微信支付结果：%s","用户取消支付"));
////                    }
//                    break;
//                default:
//                    builder.setMessage("其他错误");
//            }
            if(resp.errCode==0)
            {
//                builder.setMessage(String.format("微信支付结果：%s","支付成功"));
                bfpay=true;
            }
            else
            {
//                builder.setMessage(String.format("微信支付结果：%s","支付失败"));
                bfpay=false;
            }
//            builder.show();
        }
        else
        {
            bfpay=false;
        }
//        if(textview0!=null)
//        {
////            textview0.setText(" "+resp.errStr+"\r\n"+resp.transaction+"\r\n"+resp.openId);
//        }
    }
    @Override
    public void onClick(View v) {
           switch (v.getId())
           {
               case R.id.textView1:
//                   onfailpay();
                   break;
               case R.id.textView2:
//                   onsucesspay();
                   break;
               case R.id.textView3:
                   break;
               case R.id.textView4:
                   break;
               case R.id.textView5: //查看订单

//                   Bundle bundle=new Bundle();
                   Intent intent=new Intent(this, LookorderActivity.class);
//                   intent.putExtras(bundle);//bundle
                   startActivity(intent);
//                   startActivityForResult(intent,0);
               if(0==1)
               {
                   MyObserver<PayModel> observerx= new MyObserver<PayModel>() {
                       @Override
                       public void onNext(PayModel superiorModel) {
                           if(superiorModel==null)
                           {

                           }
                           else
                           {
                               if(superiorModel.getState()==200)
                               {

                               }
                           }
                       }
                       @Override
                       public void onError(Throwable e) {
//                           ToastManager.showToast(this, "onEr");
                       }
                   };
                   PayInfor user=new PayInfor();
                   NetWorks.getInstance().PayOrder(this,user,observerx);
               }
                   break;
               case R.id.textView6:
                     finish();
                   break;
			    case R.id.account_balance_back:
					 finish();
                   break;

           }
    }

    private void onsucesspay() {
        mlay1mm.setVisibility(View.GONE);
        mlay2mm.setVisibility(View.VISIBLE);
//        mlay2.setVisibility(View.VISIBLE);
//        mlaypay.setBackgroundResource(R.drawable.bt_0_background);
//        mlay0.setBackgroundColor(Color.parseColor("#00BC1B"));// ColorUtils.blendARGB()
//        mlay1.setBackgroundColor(Color.parseColor("#00BC1B"));// ColorUtils.blendARGB()

        textView1.setText(String.format("订单编号: %s",orderNo));
        textView3.setText(String.format("%s 元",money));

        UserOrder.getInstance().orderNo=orderNo;
        UserOrder.getInstance().money=money;
    }

    private void onfailpay() {
        mlay1mm.setVisibility(View.VISIBLE);
        mlay2mm.setVisibility(View.GONE);
//        mlay2.setVisibility(View.GONE);
//        mlaypay.setBackgroundResource(R.drawable.zhifushibaibiaoqing);
//        mlay0.setBackgroundColor(Color.parseColor("#EF1200"));// ColorUtils.blendARGB()
//        mlay1.setBackgroundColor(Color.parseColor("#EF1200"));// ColorUtils.blendARGB()
    }
}
