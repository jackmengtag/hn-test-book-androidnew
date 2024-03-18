package com.yangna.lbdsp.mine.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.ServiceConfig;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.CustomActivity;
import com.yangna.lbdsp.common.base.LoadingDialog;
import com.yangna.lbdsp.common.ewmsm.CodeUtils;
import com.yangna.lbdsp.common.ewmsm.Intents;
import com.yangna.lbdsp.common.ewmsm.UriUtils;
import com.yangna.lbdsp.common.manager.DialogManager;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.utils.RxQRCode;
import com.yangna.lbdsp.common.utils.SpUtils;
import com.yangna.lbdsp.home.impl.HomeView;
import com.yangna.lbdsp.home.model.AppSettingModel;
import com.yangna.lbdsp.widget.AppUpdateProgressDialog;
import com.yangna.lbdsp.widget.DownloadService;
import com.yanzhenjie.permission.AndPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/* 绑定邀请码 页面 */
public class AddInvitationCodeActivity extends AppCompatActivity {
    @BindView(R.id.saomiao_shuru_yaoqingma)//邀请码输入框
    EditText saomiaoshuruyaoqingma;
//    @BindView(R.id.yaoqingma)//邀请码
//            TextView yaoqingma;
//    @BindView(R.id.dqbbh)//当前软件版本号
//            TextView dangqianbanbenhao;
//    @BindView(R.id.tuiguangma)//推广码
//            TextView tuiguangma;
//    @BindView(R.id.rl_fuzhiyaoqingma)//复制邀请码按钮框
//            RelativeLayout fzyqm;
//    @BindView(R.id.rl_fuzhituiguangma)//复制推广码按钮框
//            RelativeLayout fztgm;
//    @BindView(R.id.yqm_erweima_tp)//邀请码转换二维码后显示的图片
//            ImageView yq_erweima;
//    @BindView(R.id.rl_bangdingyaoqingma)//绑定邀请码
//            RelativeLayout bdyqm;

    public static final String KEY_TITLE = "key_title";
    public static final String KEY_IS_QR_CODE = "key_code";
    public static final String KEY_IS_CONTINUOUS = "key_continuous_scan";
    private Class<?> cls;
    private String title;
    public static final int REQUEST_CODE_SCAN = 0X01;
    public static final int REQUEST_CODE_PHOTO = 0X02;
    public static final int RC_CAMERA = 0X01;
    public static final int RC_READ_PHOTO = 0X02;
    private boolean isContinuousScan;
    private LoadingDialog dialog;
    private AlertDialog alert2;

    private String erweimajieguo = "";//别人给的二维码代理申请，先设为空“”
    private String body_data = "";//绑定邀请码 返回值
public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invitation_code);
        ButterKnife.bind(this);
    }

    @SuppressLint("SetTextI18n")
    @Override//恢复时运行
    public void onResume() {
        super.onResume();
    }

    /* 点击跳转 相机扫描 */
    @OnClick(R.id.start_scan)
    public void doScan(View view) {
        this.cls = CustomActivity.class;
        this.title = "相机扫码";
        checkCameraPermissions();
    }

    /* 点击跳转 图片扫描 */
    @OnClick(R.id.start_bendi_erweima)
    public void doScan2(View view) {
        this.cls = CustomActivity.class;
        this.title = "图片扫码";
        checkExternalStoragePermissions();
    }

    /* 检测拍摄权限 */
    @AfterPermissionGranted(RC_CAMERA)
    private void checkCameraPermissions() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            startScan(cls, title);
        } else {
            EasyPermissions.requestPermissions(this, "App扫码需要用到拍摄权限", RC_CAMERA, perms);
        }
    }

    /* 检测图片访问权限 */
    @AfterPermissionGranted(RC_READ_PHOTO)
    private void checkExternalStoragePermissions() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            startPhotoCode();
        } else {
            EasyPermissions.requestPermissions(this, "App需要用到读写权限", RC_READ_PHOTO, perms);
        }
    }

    private void startPhotoCode() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }

    /**
     * 扫码
     *
     * @param cls
     * @param title
     */
    private void startScan(Class<?> cls, String title) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.in, R.anim.out);
        Intent intent = new Intent(this, cls);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_IS_CONTINUOUS, isContinuousScan);
        ActivityCompat.startActivityForResult(this, intent, REQUEST_CODE_SCAN, optionsCompat.toBundle());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    String result = data.getStringExtra(Intents.Scan.RESULT);
                    erweimajieguo = result;
                    saomiaoshuruyaoqingma.setText("" + result);//不能加中文
                    break;
                case REQUEST_CODE_PHOTO:
                    parsePhoto(data);
                    break;
            }
        }
    }

    private void parsePhoto(Intent data) {
        final String path = UriUtils.getImagePath(this, data);
        Log.d("本地扫描二维码", "路径：" + path);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        //异步解析
        asyncThread(new Runnable() {
            @Override
            public void run() {
                final String result = CodeUtils.parseCode(path);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("异步解析", "本地扫描二维码：" + result);
                        erweimajieguo = result;
                        saomiaoshuruyaoqingma.setText("" + result);//不能加中文
                    }
                });

            }
        });
    }

    private void asyncThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    @OnClick(R.id.queding_bangding)/* 点击 绑定邀请码 */
    void doBDYQM(View view) {
        erweimajieguo = saomiaoshuruyaoqingma.getText().toString();//
        if (erweimajieguo.equals("")) {
            Toast toast = Toast.makeText(this, "请先获取别人给的推荐码", Toast.LENGTH_SHORT);
            LinearLayout layout = (LinearLayout) toast.getView();
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setTextSize(25);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            //加载弹窗
            LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
                    .setMessage("加载中...")
                    .setCancelable(true)//返回键是否可点击
                    .setCancelOutside(false);//窗体外是否可点击
            dialog = loadBuilder.create();
            dialog.show();//显示弹窗
            new Thread(runnableBangDingYaoQingMa).start();//启动线程，加入上线代理
        }
    }

    //子线程中的Handler 绑定邀请码 联网判断  /api/account/addInvitationCode 是否联网成功
    private Handler mBangDingYaoQingMaHT_net_judge;

    /* 子线程：使用POST方法向服务器发送 邀请码 参数完成 绑定邀请码 */
    private Runnable runnableBangDingYaoQingMa = new Runnable() {
        @Override
        public void run() {
            String chaochangTOKEN = (String) Objects.requireNonNull(SpUtils.get(AddInvitationCodeActivity.this, UrlConfig.USERID, ""));
            try {
                JSONObject json = new JSONObject();
                json.put("inviteCode", erweimajieguo);//当前输入的邀请码
                RequestBody body = RequestBody.create(JSON, String.valueOf(json));
                Log.i("发送JSON", String.valueOf(json));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(ServiceConfig.getRootUrl() + "/api/account/addInvitationCode")//绑定邀请码 端口
//                    .addHeader("_USER", uuid)//参数全部放Header里面
                        .addHeader("Authorization", chaochangTOKEN)//绑定邀请码 需要加入头参数
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @SuppressLint("HandlerLeak")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {//未知联网错误，全部显示
                        if (!AddInvitationCodeActivity.this.isFinishing()) {
                            dialog.dismiss();//销毁加载框
                            Log.e("putAwayGoods 未知联网错误", "失败 e=" + e);
                            Looper.prepare();
                            mBangDingYaoQingMaHT_net_judge = new Handler() {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    alert2 = new AlertDialog.Builder(AddInvitationCodeActivity.this).create();
//                                    alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                                    alert2.setTitle("未知联网错误");//设置对话框的标题
                                    alert2.setCancelable(false);
                                    alert2.setMessage(e + "\n\n请联系后台客服处理");//设置要显示的内容
                                    //添加确定按钮
                                    alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                //结束所有Activity，返回登录页
                                        }
                                    });
                                    alert2.show();// 创建对话框并显示
                                }
                            };
                            mBangDingYaoQingMaHT_net_judge.sendEmptyMessage(1);
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!AddInvitationCodeActivity.this.isFinishing()) {
                            dialog.dismiss();//销毁加载框
                            assert response.body() != null;
                            body_data = response.body().string();//获取到数据
                            try {
                                jsonBangDingYaoQingMa(body_data);//把数据传入解析josn数据方法
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

    /* 解析 绑定邀请码 返回值，存集合 */
    @SuppressLint("HandlerLeak")
    private void jsonBangDingYaoQingMa(String date) throws JSONException {//响应200代码后才会来到这里，但是还要再次判断"_CODE"=200的
        Message msg = new Message();//处理完成后给handler发送消息//处理完成后给handler发送消息
        if (date != null && !date.equals("")) {//判断数据是空
            //
            Log.i("绑定邀请码 有返回值", "date = " + date);
            JSONObject jsonObject = new JSONObject(date);
            String state = jsonObject.optString("state", "");//获取返回数据中 state 的值
            String body = jsonObject.optString("body", "");//获取返回数据中 body 的值
            if (state == null || state.equals("")) {
                Log.e("绑定邀请码失败》》》", "返回date值：" + date);
                msg.what = 2;//失败
                mBangDingYaoQingMaHT.sendMessage(msg);
            } else {//
                if (state.equals("200")) {
                    //{
                    //	"body": "加入邀请成功",
                    //	"state": 200,
                    //	"msg": "请求成功"
                    //}
                    Log.w("绑定邀请码成功》》》", "返回date值：" + date);
//                    JSONObject jo = new JSONObject(body);
//                    String account = jo.optString("account", "");
//                    JSONObject jo2 = new JSONObject(account);
                    BaseApplication.getInstance().CheckDeviceToken();
                    SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("AccountInfo", MODE_PRIVATE);//SharedPreferences 是一个轻量级的存储类，主要是保存一些小的数据，一些状态信息
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("promotionCode", erweimajieguo);//手写更新本地数据
                    editor.apply();
                    msg.what = 1;
                    mBangDingYaoQingMaHT.sendMessage(msg);
                } else {
                    //{
                    //	"body": null,
                    //	"state": 500,
                    //	"msg": "邀请码错误"
                    //}
                    Log.e("绑定邀请码失败》》》", "返回date值：" + date);
                    msg.what = 2;//失败
                    mBangDingYaoQingMaHT.sendMessage(msg);
                }
            }
        } else {
            Log.e("绑定邀请码失败", "没有任何返回值");
            msg.what = 2;//失败
            mBangDingYaoQingMaHT.sendMessage(msg);
        }
    }

    //子线程 绑定邀请码 判断的Handler 返回值判断
    @SuppressLint("HandlerLeak")
    private Handler mBangDingYaoQingMaHT = new Handler() {
        @Override
        public void handleMessage(Message msg) {//在子线程中通知主线程，让主线程做更新操作
            switch (msg.what) {
                case 1://成功
                    alert2 = new AlertDialog.Builder(AddInvitationCodeActivity.this).create();
//                    alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                    alert2.setTitle("绑定 成功");//设置对话框的标题
                    alert2.setCancelable(false);
//                  alert.setMessage("");//设置要显示的内容
                    //添加确定按钮
                    alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //发送广播，给“我的”页面刷新个人信息
                            Intent intent2 = new Intent();
                            intent2.setAction("SX_GRXX");
                            AddInvitationCodeActivity.this.sendBroadcast(intent2);
                            finish();//结束页面
                        }
                    });
                    alert2.show();// 创建对话框并显示
                    break;
                case 2://失败
                    Toast toast = Toast.makeText(AddInvitationCodeActivity.this, "绑定邀请码失败", Toast.LENGTH_LONG);
                    LinearLayout layout = (LinearLayout) toast.getView();
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setTextSize(25);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick(R.id.add_invitation_code_back)//退出当前页面
    public void doBack(View view) {
        finish();
    }

}
