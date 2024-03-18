package com.yangna.lbdsp.mine.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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

/* 提现到卡页面 */
public class WithdrawalCardActivity extends AppCompatActivity {

    @BindView(R.id.yinhang_migncheng)
    EditText yhmc;//输入银行名称
    @BindView(R.id.yinhang_kahao)
    EditText yhkh;//输入银行卡号
    @BindView(R.id.yinhang_chiyourenmc)
    EditText yhcyrmc;//输入银行持有人名称

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String tixian_date = "";//提现返回值数据
    private String tixian_liangzhongzhi = "";//返回值数据 500 或 200
    private String yh_mc = "";//银行名称
    private String yh_kh = "";//银行卡号
    private String cyr_mc = "";//持有人姓名
    private LoadingDialog dialog;
    private AlertDialog alert1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_card);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.withdrawal_card_back)//退出当前页面
    public void doBack(View view) {
        finish();
    }

    @OnClick(R.id.queren_tixian)//确认提现
    public void doQRTX(View view) {
        yh_mc = yhmc.getText().toString();//获取银行名称输入框
        if (yh_mc == null || yh_mc.equals("")) {
            Toast toast = Toast.makeText(this, "请输入银行名称", Toast.LENGTH_SHORT);//
            LinearLayout layout = (LinearLayout) toast.getView();
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setTextSize(25);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if (yh_mc.length() > 20) {
                Toast toast = Toast.makeText(this, "银行名称不能超过20个字！", Toast.LENGTH_SHORT);//
                LinearLayout layout = (LinearLayout) toast.getView();
                TextView tv = (TextView) layout.getChildAt(0);
                tv.setTextSize(25);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                yh_kh = yhkh.getText().toString();//获取银行卡号输入框
                if (yh_kh == null || yh_kh.equals("")) {
                    Toast toast = Toast.makeText(this, "请输入银行卡号", Toast.LENGTH_SHORT);//
                    LinearLayout layout = (LinearLayout) toast.getView();
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setTextSize(25);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    if (yh_kh.length() > 20) {
                        Toast toast = Toast.makeText(this, "银行卡号不能超过20个字！", Toast.LENGTH_SHORT);//
                        LinearLayout layout = (LinearLayout) toast.getView();
                        TextView tv = (TextView) layout.getChildAt(0);
                        tv.setTextSize(25);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        cyr_mc = yhcyrmc.getText().toString();//获取银行卡持有人名称
                        if (cyr_mc == null || cyr_mc.equals("")) {
                            Toast toast = Toast.makeText(this, "请输入银行卡持有人名称", Toast.LENGTH_SHORT);//
                            LinearLayout layout = (LinearLayout) toast.getView();
                            TextView tv = (TextView) layout.getChildAt(0);
                            tv.setTextSize(25);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            if (cyr_mc.length() > 20) {
                                Toast toast = Toast.makeText(this, "持有人名称不能超过20个字！", Toast.LENGTH_SHORT);//
                                LinearLayout layout = (LinearLayout) toast.getView();
                                TextView tv = (TextView) layout.getChildAt(0);
                                tv.setTextSize(25);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {// 可以启动线程了
                                //加载弹窗
                                LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
                                        .setMessage("加载中...")
                                        .setCancelable(true)//返回键是否可点击
                                        .setCancelOutside(false);//窗体外是否可点击
                                dialog = loadBuilder.create();
                                dialog.show();//显示弹窗
                                new Thread(runnableTiXianDaoKa).start();//点击 提现到卡
                            }
                        }
                    }
                }
            }
        }

    }

//    @Override//恢复时，再运行
//    protected void onResume(){
//        super.onResume();
//        new Thread(runnableTiXianDaoKa).start();
//    }

    //子线程中的Handler 提现到卡 联网判断 /api/wallet/extract 是否联网成功
    private Handler mTiXianDaoKaHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 三个 子参数完成提现到卡  */
    private Runnable runnableTiXianDaoKa = new Runnable() {
        @Override
        public void run() {
            tixian_date = "";//请求之前先清空一次
            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(WithdrawalCardActivity.this, UrlConfig.USERID, ""));
            try {
                JSONObject json = new JSONObject();
                json.put("bank", yh_mc);
                json.put("cardNo", yh_kh);
                json.put("holderName", cyr_mc);
                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
                Log.i("发送JSON", String.valueOf(json));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ServiceConfig.getRootUrl() + "/api/wallet/extract")//提现到卡 端口
//                    .addHeader("_USER", uuid)//参数全部放Header里面
                        .addHeader("Authorization", chaochangTOKEN)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
                        dialog.dismiss();//销毁加载框
                        if (!WithdrawalCardActivity.this.isFinishing()) {
                            Log.e("提现到卡 未知联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mTiXianDaoKaHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    //
                                }
                            };
                            mTiXianDaoKaHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        dialog.dismiss();//销毁加载框
                        if (!WithdrawalCardActivity.this.isFinishing()) {
                            assert response.body() != null;
                            tixian_date = response.body().string();//获取到数据
                            try {
                                jsongetTiXianDaoKa(tixian_date);//把数据传入解析josn数据方法
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /* 解析 提现到卡 返回值，存集合 */
    @SuppressLint("HandlerLeak")
    private void jsongetTiXianDaoKa(String date) throws JSONException {//响应200代码后才会来到这里，但是还要再次判断"_CODE"=200的
        Message msg = new Message();//处理完成后给handler发送消息//处理完成后给handler发送消息
        if (date != null && !date.equals("")) {//判断数据是空
            Log.w("提现到卡 返回值》》》", "返回date值：" + date);
            // 提现到卡 /api/wallet/extract 接口返回值
            //
            // {
            //  "body": null,
            //  "state": 500,
            //  "msg": "银行卡错误，请确认"
            // }
            JSONObject jsonObject = new JSONObject(date);
            String state = jsonObject.optString("state", "");//获取返回数据中 state 的值
            String extract_msg = jsonObject.optString("msg", "");//获取返回数据中 msg 的值
            if (state == null || state.equals("") || state.equals("500")) {
                Log.e("提现到卡 失败》》》", "返回date值：" + date);
                tixian_liangzhongzhi = extract_msg;
                msg.what = 2;//失败
                mTiXianDaoKaHT.sendMessage(msg);
            } else {//
//                JSONObject jo = new JSONObject(body);
//                jinbishu = jo.optString("gold", "");
//                if (jinbishu == null || jinbishu.equals("")) {
//                    Log.e("提现到卡 失败》》》", "返回date值：" + date);
//                    msg.what = 2;//失败
//                    mTiXianDaoKaHT.sendMessage(msg);
//                } else {
                Log.w("提现到卡 成功》》》", "返回date值：" + date);
                tixian_liangzhongzhi = extract_msg;
                msg.what = 1;
                mTiXianDaoKaHT.sendMessage(msg);
//                }
            }
        } else {
            Log.e("提现到卡 失败", "没有任何返回值");
            msg.what = 2;//失败
            mTiXianDaoKaHT.sendMessage(msg);
        }
    }

    //子线程 提现到卡 判断的Handler 返回值判断
    @SuppressLint("HandlerLeak")
    private Handler mTiXianDaoKaHT = new Handler() {
        @Override
        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
            switch (msg.what) {
                case 1://提现到卡 成功

                    alert1 = new AlertDialog.Builder(WithdrawalCardActivity.this).create();
//                    alert1.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                    alert1.setTitle("提现到卡 成功");//设置对话框的标题
                    alert1.setCancelable(false);
                    //添加确定按钮
                    alert1.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();//点击结束本页面
                        }
                    });
                    alert1.show();// 创建对话框并显示
//                    jinbi.setText(jinbishu);//设置显示金币数
//                    Toast toast = Toast.makeText(WithdrawalCardActivity.this, tixian_liangzhongzhi, Toast.LENGTH_LONG);
//                    LinearLayout layout = (LinearLayout) toast.getView();
//                    TextView tv = (TextView) layout.getChildAt(0);
//                    tv.setTextSize(25);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
                    break;
                case 2://提现到卡 失败
                    alert1 = new AlertDialog.Builder(WithdrawalCardActivity.this).create();
//                    alert1.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                    alert1.setTitle("提现到卡 失败\n" + tixian_liangzhongzhi);//设置对话框的标题
                    alert1.setCancelable(false);
                    //添加确定按钮
                    alert1.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            finish();//点击结束本页面
                        }
                    });
                    alert1.show();// 创建对话框并显示
//                    Toast toast2 = Toast.makeText(WithdrawalCardActivity.this, tixian_liangzhongzhi, Toast.LENGTH_LONG);
//                    LinearLayout layout2 = (LinearLayout) toast2.getView();
//                    TextView tv2 = (TextView) layout2.getChildAt(0);
//                    tv2.setTextSize(25);
//                    toast2.setGravity(Gravity.CENTER, 0, 0);
//                    toast2.show();
                    break;
                default:
                    break;
            }
        }
    };

}
