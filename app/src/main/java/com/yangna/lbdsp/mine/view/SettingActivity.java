package com.yangna.lbdsp.mine.view;

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
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wwb.laobiao.address.UserAdaddressActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.manager.DialogManager;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.utils.RxQRCode;
import com.yangna.lbdsp.common.utils.Utils;
import com.yangna.lbdsp.home.impl.HomeView;
import com.yangna.lbdsp.home.model.AppSettingModel;
import com.yangna.lbdsp.home.view.MallActivity;
import com.yangna.lbdsp.login.view.WebViewActivity;
import com.yangna.lbdsp.widget.AppUpdateProgressDialog;
import com.yangna.lbdsp.widget.DownloadService;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/* 设置页面 */
public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.yaoqingma)//邀请码
            TextView yaoqingma;
    @BindView(R.id.dqbbh)//当前软件版本号
            TextView dangqianbanbenhao;
    @BindView(R.id.tuiguangma)//推广码
            TextView tuiguangma;
    @BindView(R.id.rl_fuzhiyaoqingma)//复制邀请码按钮框
            RelativeLayout fzyqm;
    @BindView(R.id.rl_fuzhituiguangma)//复制推广码按钮框
            RelativeLayout fztgm;
    @BindView(R.id.yqm_erweima_tp)//邀请码转换二维码后显示的图片
            ImageView yq_erweima;
    @BindView(R.id.rl_bangdingyaoqingma)//绑定邀请码
            RelativeLayout bdyqm;
    @BindView(R.id.rl_adrlay)//地址管理
            RelativeLayout adrlay;

    private String inviteCode = "";//邀请码
    private String promotionCode = "";//推广码
    private HomeView homeViewl;
    private AlertDialog alertDialog;
    private AlertDialog alert;
    private AppUpdateProgressDialog dialog = null;
    private ServiceConnection conn;

    private int mMaxProgress;//百分比

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
//        inviteCode = sp.getString("inviteCode", "你没有邀请码");
//        yaoqingma.setText(inviteCode);
//        promotionCode = sp.getString("promotionCode", "你没有推广码");
//        tuiguangma.setText(promotionCode);
//        if (promotionCode.equals("") || promotionCode.equals("你没有推广码") || promotionCode == null) {//没有绑定
//            fzyqm.setVisibility(View.GONE);//隐藏
//            fztgm.setVisibility(View.GONE);//隐藏
//        }else {
//            //二维码生成方式一  推荐此方法
//            RxQRCode.builder(inviteCode).backColor(0xFFFFFFFF).codeColor(0xFF000000).codeSide(600).into(yq_erweima);
//            fzyqm.setVisibility(View.VISIBLE);//显示，给人复制或扫码
//            fztgm.setVisibility(View.GONE);//隐藏
//        }
        adrlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this, UserAdaddressActivity.class);
//                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override//恢复时运行
    public void onResume() {
        super.onResume();
        // 在当前的activity中注册广播
        IntentFilter filter = new IntentFilter();//实例化IntentFilter
        filter.addAction("ExitApp");//设置接收广播的类型
        dangqianbanbenhao.setText("当前版本  " + getAppVersionName(this));


        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("AccountInfo", MODE_PRIVATE);//SharedPreferences 是一个轻量级的存储类，主要是保存一些小的数据，一些状态信息
        inviteCode = sp.getString("inviteCode", "你没有邀请码");
        yaoqingma.setText(inviteCode);
        promotionCode = sp.getString("promotionCode", "你没有推广码");
        tuiguangma.setText(promotionCode);
        if (promotionCode.equals("") || promotionCode.equals("你没有推广码") || promotionCode == null) {//没有绑定
            fzyqm.setVisibility(View.GONE);//隐藏
            fztgm.setVisibility(View.GONE);//隐藏
            bdyqm.setVisibility(View.VISIBLE);//显示，给人点击跳转后期绑定
        } else {
            //二维码生成方式一  推荐此方法
            RxQRCode.builder(inviteCode).backColor(0xFFFFFFFF).codeColor(0xFF000000).codeSide(600).into(yq_erweima);
            fzyqm.setVisibility(View.VISIBLE);//显示，给人复制或扫码
            fztgm.setVisibility(View.GONE);//隐藏
            bdyqm.setVisibility(View.GONE);//隐藏
        }
    }

    /* 获取当前app version name */
    public static String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("获取版本名失败", e.getMessage());
        }
        return appVersionName;
    }

    /* 绑定邀请码 */
    @OnClick(R.id.rl_bangdingyaoqingma)
    void doBDYQM(View view) {
        Intent bdyqm = new Intent(this, AddInvitationCodeActivity.class);
        startActivity(bdyqm);
    }

    /* 点击 查询新版本 */
    @OnClick(R.id.layout_jianchagengxin)
    void doJCGX(View view) {
        if (Utils.isFastClick5000()) {// 进行点击事件后的逻辑操作 0.8 秒内禁止重复点击
            Log.d("本地版本", "appVersion" + BaseApplication.getInstance().AppVersion);
            NetWorks.getInstance().getAppVersion(this, new MyObserver<AppSettingModel>() {
                @Override
                public void onNext(AppSettingModel model) {
                    //{
                    //    "body":{
                    //        "versions":"1.0.7",
                    //        "constraint":"0",
                    //        "url":"http://www.lbdsp.com/download/lbdsp.apk"
                    //    },
                    //    "state":200,
                    //    "msg":"请求成功"
                    //}
                    try {
                        if (UrlConfig.RESULT_OK == model.getState()) {
                            //服务器上最新的版本
                            int sVersion = Integer.parseInt(model.getBody().getVersions().replace(".", ""));
                            //本地版本
                            int appVersion = Integer.parseInt(BaseApplication.getInstance().AppVersion.replace(".", ""));
                            Log.e("服务器上最新的版本", "sVersion = " + model.getBody().getConstraint());
                            if (model != null && model.getBody() != null) {
                                try {
                                    //本地版本 < 服务器最低版本 修改为强制更新
                                    if (model.getBody().getConstraint().equals("0") && sVersion > appVersion) {
                                        alertDialog = new AlertDialog.Builder(SettingActivity.this).create();
//                                        alertDialog.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                                        alertDialog.setTitle("检测到新版本！");//设置对话框的标题
                                        alertDialog.setCancelable(false);
//                            alert.setMessage("");//设置要显示的内容
                                        //添加确定按钮
                                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                downApk(model);
                                            }
                                        });
                                        alertDialog.show();// 创建对话框并显示
                                    } else if (sVersion > appVersion && model.getBody().getConstraint().equals("1")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                                        builder.setTitle("提示");
                                        //设置提示消息
                                        builder.setMessage("检测到新版本，请问是否更新");
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //点击确定按钮之后的回调
                                                downApk(model);
                                            }
                                        });
                                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //点击取消按钮之后的回调
                                            }
                                        });
                                        builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //点击中间的按钮的回调
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();// 创建对话框并显示
                                    } else {
                                        Toast toast = Toast.makeText(SettingActivity.this, "无需更新", Toast.LENGTH_SHORT);
                                        LinearLayout layout = (LinearLayout) toast.getView();
                                        TextView tv = (TextView) layout.getChildAt(0);
                                        tv.setTextSize(15);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            ToastManager.showToast(SettingActivity.this, model.getMsg());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    ToastManager.showToast(SettingActivity.this, e);
                }
            });
        } else {
            alert = new AlertDialog.Builder(SettingActivity.this).create();
//            alert.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
            alert.setTitle("点击过于频繁");//设置对话框的标题
            alert.setCancelable(false);
            alert.setMessage("请稍后再试");//设置要显示的内容
            //添加确定按钮
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                                    finish();//不用结束页面
                }
            });
            alert.show();// 创建对话框并显示
        }
    }

    private void downApk(AppSettingModel model) {
        dialog = new AppUpdateProgressDialog(this);
        //正在下载时，不可关闭dialog
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Toast.makeText(SettingActivity.this, "正在下载请稍后", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }
        });
        dialog.show();
        if (conn == null) {
            conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                    DownloadService myService = binder.getService();
                    myService.downApk(model, new DownloadService.DownloadCallback() {
                        @Override
                        public void onPrepare() {

                        }

                        @Override
                        public void onProgress(int progress) {
                            //显示进度条
                            if (progress <= mMaxProgress) {//progress 下载进度  mMaxProgress//百分比
                                try {
                                    Message msg = myHandle.obtainMessage();
                                    msg.what = 100;
                                    msg.obj = progress;
                                    myHandle.sendMessage(msg);
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onComplete(File file) {
                            showComplete(file);
                        }

                        @Override
                        public void onFail(String msg) {
                            Toast.makeText(SettingActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
            Intent intent = new Intent(this, DownloadService.class);
            this.bindService(intent, conn, Service.BIND_AUTO_CREATE);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler myHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                int progress = (int) msg.obj;
                dialog.setProgress(progress);
                if (100 == progress) {
                    dialog.dismiss();
                    Toast.makeText(SettingActivity.this, "下载完成，跳转到安装界面", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void showComplete(File file) {
        AndPermission.with(this)
                .install()
                .file(file)
                .start();
    }

    @OnClick(R.id.rl_fuzhiyaoqingma)/* 点击复制 邀请码 */
    void doFuZhiYQM(View view) {
        if (inviteCode.equals("你没有邀请码") || inviteCode.equals("") || inviteCode == null) {
            Toast.makeText(SettingActivity.this, "获取 邀请码 失败\n" + "请联系后台客服处理", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("复制邀请码成功", "inviteCode = " + inviteCode);
            Toast toast = Toast.makeText(this, "邀请码 " + inviteCode + " 复制成功\n请分享给朋友", Toast.LENGTH_LONG);
            LinearLayout layout = (LinearLayout) toast.getView();
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setTextSize(20);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", inviteCode);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
        }
    }

    /* 清理缓存 */
    @OnClick(R.id.layout_qinglihuancun)
    void doQLHC(View view) {
        Toast toast = Toast.makeText(this, "缓存已清除", Toast.LENGTH_LONG);
        LinearLayout layout = (LinearLayout) toast.getView();
        TextView tv = (TextView) layout.getChildAt(0);
        tv.setTextSize(20);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @OnClick(R.id.layout_tuichudenglu)/* 退出登录 */
    void doTC(View view) {
        DialogManager.logoutDialog(this);
    }

    @OnClick(R.id.layout_guanyuwomen)
    void doGYWM(View view) {
        Intent it = new Intent(this, WebActivity.class);
        it.putExtra("httpUrl", "http://www.lbdsp.com");
        startActivity(it);
    }

    @OnClick(R.id.setting_back)//退出当前页面
    public void doBack(View view) {
        finish();
    }

    @OnClick(R.id.layout_bianjiziliao)//跳转编辑资料页面
    public void doEditData(View view) {
        Intent sa = new Intent(this, EditDataActivity.class);
        startActivity(sa);
    }

}
