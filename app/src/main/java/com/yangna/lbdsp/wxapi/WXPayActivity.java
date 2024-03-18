package com.yangna.lbdsp.wxapi;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.wxapi.util.MD5;

import okhttp3.OkHttpClient;

import static com.yangna.lbdsp.wxapi.util.WeiXinConstants.APP_ID;


//import android.support.v7.app.AppCompatActivity;
//import com.example.R;
//import com.example.wxapi.util.MD5;

public class WXPayActivity extends AppCompatActivity {
    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    private static final String TAG = "PayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay);
        final IWXAPI wxapi = WXAPIFactory.createWXAPI(this, APP_ID,false);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PayReq request = new PayReq();
//                request.appId = Constants.WEIXIN_AppId;//应用的appid
//                request.partnerId =   String.valueOf(map.get("mch_id"));//微信支付分配的商户号
//                request.prepayId = String.valueOf(map.get("prepay_id"));//微信返回的支付交易会话ID
//                request.packageValue = "Sign=WXPay";//暂填写固定值Sign=WXPay
//                request.nonceStr = String.valueOf(map.get("nonce_str"));//随机字符串
//                long timeMills = System.currentTimeMillis() / 1000;
//                request.timeStamp = String.valueOf(timeMills);//时间戳 以秒为单位，
                PayReq req = new PayReq();
                req.appId = "wxb066fdba033fba34";
                req.partnerId = "1605644902";
                req.prepayId = "wx131824203880952b10eea24435da8a0000";
                req.packageValue = "Sign=WXPay";
                req.nonceStr = "KcEyv1vLADfA2hJltcwrqmWxXgFe6aF7";
                req.timeStamp = "3333333333packageValue";
//              req.extData = "33333333232323";
                req.sign = "a1bec05ab8b5d0b921f63949e0454b18";
                boolean result = wxapi.sendReq(req);
                Toast.makeText(WXPayActivity.this, "调起支付结果:" +result, Toast.LENGTH_LONG).show();

            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                button.setEnabled(false);
//                String url = "商户服务端接口";
//                Request request = new Request.Builder().url(url).build();
//                Call call = okHttpClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        button.setEnabled(true);
//                        Toast.makeText(WXPayActivity.this, "请求失败", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        if (response.isSuccessful()) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(response.body().string());
//                                Log.i(TAG,jsonObject.toString());
//                                int code = jsonObject.getInt("code");
//                                if (code == 0) {
//                                    JSONObject data = jsonObject.getJSONObject("data");
//                                    String appId = data.getString("appid");
//                                    String partnerId = data.getString("partnerid");
//                                    String prepayId = data.getString("prepayid");
//                                    String packageValue = data.getString("package");
//                                    String nonceStr = data.getString("noncestr");
//                                    String timeStamp = data.getString("timestamp");
//                                    String extData = data.getString("extdata");
//                                    String sign = data.getString("sign");
//                                    PayReq req = new PayReq();
//                                    req.appId = appId;
//                                    req.partnerId = partnerId;
//                                    req.prepayId = prepayId;
//                                    req.packageValue = packageValue;
//                                    req.nonceStr = nonceStr;
//                                    req.timeStamp = timeStamp;
//                                    req.extData = extData;
//                                    req.sign = sign;
//                                    boolean result = wxapi.sendReq(req);
//                                    Toast.makeText(WXPayActivity.this, "调起支付结果:" +result, Toast.LENGTH_LONG).show();
////
//                                } else {
//                                    Toast.makeText(WXPayActivity.this, "数据出错", Toast.LENGTH_LONG).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        button.setEnabled(true);
//                    }
//                });
//            }
//        });
    }

    private String signNum(String partnerId, String prepayId, String packageValue, String nonceStr, String timeStamp, String key){
        String stringA=
                "appid="+ SyncStateContract.Constants.ACCOUNT_NAME
                        +"&noncestr="+nonceStr
                        +"&package="+packageValue
                        +"&partnerid="+partnerId
                        +"&prepayid="+prepayId
                        +"&timestamp="+timeStamp;


        String stringSignTemp = stringA+"&key="+key;
        String sign = MD5.getMessageDigest(stringSignTemp.getBytes()).toUpperCase();
        return  sign;
    }
}
