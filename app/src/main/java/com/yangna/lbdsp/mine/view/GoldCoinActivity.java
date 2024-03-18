package com.yangna.lbdsp.mine.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.ServiceConfig;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.LoadingDialog;
import com.yangna.lbdsp.common.utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* 金币页面 */
public class GoldCoinActivity extends AppCompatActivity {

//    @BindView(R.id.jinbi)//显示金币
//            TextView jinbi;
//
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//    private String gold_date = "";//金币返回值数据
//    private BigDecimal jinbishu = BigDecimal.valueOf(0);//金币数 BigDecimal类型 等待网络端返回更新数值
//    private BigDecimal bijiaoshu = BigDecimal.valueOf(25);//比较数 BigDecimal类型 本地写死做比较
//    private AlertDialog alert1;
//    private LoadingDialog dialog;
//    private String chaochangTOKEN = "";

    @Override//创建
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_coin);
        ButterKnife.bind(this);
//        chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(GoldCoinActivity.this, UrlConfig.USERID, ""));
//        new Thread(runnableJinBiXiangQing).start();
//        Log.e("创建时", "拿金币");
    }

    @OnClick(R.id.gold_coin_back)//退出当前页面
    public void doBack(View view) {
        finish();
    }

//    @OnClick(R.id.tixian_daoka)//提现到卡
//    public void doTXDK(View view) {
//        if (jinbishu.compareTo(bijiaoshu) == -1) {
//            Toast toast = Toast.makeText(GoldCoinActivity.this, "余额不足25，无法提现！", Toast.LENGTH_LONG);
//            LinearLayout layout = (LinearLayout) toast.getView();
//            TextView tv = (TextView) layout.getChildAt(0);
//            tv.setTextSize(25);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//        } else {
//            Intent txdk = new Intent(this, WithdrawalCardActivity.class);
//            startActivity(txdk);
//        }
//    }

    @Override//启动时
    protected void onStart() {
        super.onStart();
//        Log.e("启动时", "拿金币");

    }

    @Override//恢复时，再运行
    protected void onResume() {
        super.onResume();
//        Log.e("恢复时", "拿金币");
//        if (chaochangTOKEN.equals("") || chaochangTOKEN == null) {
//            Toast toast = Toast.makeText(GoldCoinActivity.this, "操作过快！请过一会儿再来查看余额！", Toast.LENGTH_LONG);
//            LinearLayout layout = (LinearLayout) toast.getView();
//            TextView tv = (TextView) layout.getChildAt(0);
//            tv.setTextSize(25);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//        } else {
//            //加载弹窗
////            LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
////                    .setMessage("加载中...")
////                    .setCancelable(true)//返回键是否可点击
////                    .setCancelOutside(false);//窗体外是否可点击
////            dialog = loadBuilder.create();
////            dialog.show();//显示弹窗
//            new Thread(runnableJinBiXiangQing).start();
//        }
    }

    /* 子线程中的Handler 获取金币详情 联网判断 /api/wallet/referSum 是否联网成功 */
//    private Handler mJinBiXiangQingHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 goodsId 参数完成  */
//    private Runnable runnableJinBiXiangQing = new Runnable() {
//        @Override
//        public void run() {
//            gold_date = "";//请求之前先清空一次
////            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(AccountBalanceActivity.this, UrlConfig.USERID, ""));
////            try {
//            JSONObject json = new JSONObject();
////                json.put("goodsId", id);
//            RequestBody body = RequestBody.create(JSON, String.valueOf(json));
//            Log.i("发送JSON拿金币", String.valueOf(json));
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(ServiceConfig.getRootUrl() + "/api/wallet/referSum")//获取金币详情 端口
////                    .addHeader("_USER", uuid)//参数全部放Header里面
//                    .addHeader("Authorization", chaochangTOKEN)
//                    .post(body)
//                    .build();
//            client.newCall(request).enqueue(new Callback() {
//                @SuppressLint("HandlerLeak")
//                @Override
//                public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
//                    if (!GoldCoinActivity.this.isFinishing()) {
////                        dialog.dismiss();//销毁加载框
//                        Log.e("获取金币详情 未知联网错误", "失败 e=" + e);
//                        Looper.prepare();
//                        mJinBiXiangQingHT_net_judge = new Handler() {
//                            @Override
//                            public void handleMessage(@NonNull Message msg) {
//                                super.handleMessage(msg);
//                                alert1 = new AlertDialog.Builder(GoldCoinActivity.this).create();
////                                alert1.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
//                                alert1.setTitle("未知联网错误");//设置对话框的标题
//                                alert1.setCancelable(false);
//                                alert1.setMessage(e + "\n\n请联系后台客服处理");//设置要显示的内容
//                                //添加确定按钮
//                                alert1.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
////                                //结束所有Activity，返回登录页
//                                    }
//                                });
//                                alert1.show();// 创建对话框并显示
//                            }
//                        };
//                        mJinBiXiangQingHT_net_judge.sendEmptyMessage(1);
//                        Looper.loop();
//                    }
//                }
//
//                @Override
//                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                    if (!GoldCoinActivity.this.isFinishing()) {
////                        dialog.dismiss();//销毁加载框
//                        assert response.body() != null;
//                        gold_date = response.body().string();//获取到数据
//                        try {
//                            jsongetJinBiXiangQing(gold_date);//把数据传入解析josn数据方法
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
//        }
//    };

    /* 解析 获取金币详情 返回值，存集合 */
//    @SuppressLint("HandlerLeak")
//    private void jsongetJinBiXiangQing(String date) throws JSONException {//响应200代码后才会来到这里，但是还要再次判断"_CODE"=200的
//        Message msg = new Message();//处理完成后给handler发送消息//处理完成后给handler发送消息
//        if (date != null && !date.equals("")) {//判断数据是空
//            //{
//            //	"body": {
//            //		"gold": "0"
//            //	},
//            //	"state": 200,
//            //	"msg": "请求成功"
//            //}
//            Log.w("获取金币详情 返回值》》》", "返回date值：" + date);
//            // 门店内金币列表 /account/good/getGoodsDetailByGoodsId 接口返回值
//            JSONObject jsonObject = new JSONObject(date);
//            String body = jsonObject.optString("body", "");//获取返回数据中 records 的值
//            if (body == null || body.equals("")) {
//                Log.e("获取金币详情 失败》》》", "返回records值：" + date);
//                msg.what = 2;//失败
//                mJinBiXiangQingHT.sendMessage(msg);
//            } else {//
//                JSONObject jo = new JSONObject(body);
//                String gold = jo.optString("gold", "————");
//                if (gold.equals("————") || gold.equals("") || gold == null) {
//                    Log.e("获取金币详情 失败》》》", "返回records值：" + date);
//                    msg.what = 2;//失败
//                    mJinBiXiangQingHT.sendMessage(msg);
//                } else {
//                    jinbishu = new BigDecimal(gold);
//                    Log.w("获取金币详情 成功》》》", "返回records值：" + date);
//                    msg.what = 1;
//                    mJinBiXiangQingHT.sendMessage(msg);
//                }
//            }
//        } else {
//            Log.e("获取金币详情 失败", "没有任何返回值");
//            msg.what = 2;//失败
//            mJinBiXiangQingHT.sendMessage(msg);
//        }
//    }

    /* 子线程 获取金币详情 判断的Handler 返回值判断 */
//    @SuppressLint("HandlerLeak")
//    private Handler mJinBiXiangQingHT = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
//            switch (msg.what) {
//                case 1://获取成功
////                    Toast toast0 = Toast.makeText(AccountBalanceActivity.this, "余额详情：\n\n" + gold_date, Toast.LENGTH_LONG);
////                    LinearLayout layout0 = (LinearLayout) toast0.getView();
////                    TextView tv0 = (TextView) layout0.getChildAt(0);
////                    tv0.setTextSize(20);
////                    toast0.setGravity(Gravity.CENTER, 0, 0);
////                    toast0.show();
//                    jinbi.setText(jinbishu.toString());//设置显示金币数
//                    break;
//                case 2://获取失败
//                    Toast toast = Toast.makeText(GoldCoinActivity.this, "获取金币详情 失败", Toast.LENGTH_LONG);
//                    LinearLayout layout = (LinearLayout) toast.getView();
//                    TextView tv = (TextView) layout.getChildAt(0);
//                    tv.setTextSize(20);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override//销毁时
    protected void onDestroy() {
        super.onDestroy();

    }

}
