package com.yangna.lbdsp.common.base;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.multidex.MultiDexApplication;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.wwb.laobiao.common.ChatUMessage;
import com.wwb.laobiao.common.Receiver.TransactionReceiver;
import com.yangna.lbdsp.MainActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.ServiceConfig;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.common.net.PersistentCookieStore;
import com.yangna.lbdsp.common.utils.AppUtils;
import com.yangna.lbdsp.common.utils.SpUtils;
import com.yangna.lbdsp.common.utils.StringUtils;
import com.yangna.lbdsp.login.view.LoginActivity;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * artifact  BaseApplication
 */
public class BaseApplication extends MultiDexApplication {// 低版本安卓手机适配MultiDexApplication
    private static final String TAG = BaseApplication.class.getName();
    private Handler handler;
    // 创建application对象
    private static BaseApplication mInstance;

    private static Context mContext;

    private String deviceId;
    //Activity栈保存打开的activity，退出时关闭所有的activity
    private List<Activity> mList = new LinkedList<>();

    //UserId根据userId获取用户信息
    private String userId = "";
    //手机号
    private String phone = "";
    //app版本
    public String AppVersion = "";
    //渠道名称
    public String channelName = "";
    //首次运行
    public static String shouciyunxing = "";//判断是不是首次运行，记录0和1
    public static Display BaseDisplay;//存一份公共屏幕宽高，全局使用
    public static String jintiankanguoguanggao = "";//判断今天是不是看过一次广告，记录今天的年月日 yyyy-MM-dd

    private long accountId = -1;
    private int userLevel = -1;
    private String promotionCode;
    private boolean Agreebf;
    private String AccountName;

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = this;
        Agreebf = false;

        AppVersion = AppUtils.getVersionName(this);
//        channelName = AppUtils.getUMENGCHANNEL(mInstance);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);//百度地图专用 2021年2月7日9:49:53
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.GCJ02);//百度地图专用 2021年2月7日9:49:58

        //友盟统计，分享
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, UrlConfig.UMENG_SECRET);

        //普通统计场景
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //可以理解为页面时长统计和页面名字统计分离
//        MobclickAgent.openActivityDurationTrack(false);
        ServiceConfig.getRootUrl();
        //平方字体
        //TypefaceUtil.replaceSystemDefaultFont(this,"fonts/pingfangregular.ttf");
//        initUMengShare();
//        initUMengPush();
        initUMengPush();
    }

    private void initUMengPush() {
        //com.qzy.laobiao.hongbao.view.RankingActivity
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, "5f9be0522065791705fd5a3b"/*应用申请的Appkey*/,
                "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "7a1a22c749670ce55f9fed33746ccd07");
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //服务端控制声音
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
                deviceId = deviceToken;
                //AiU38psFWqWRPDpFr612X22GJULzKiHJhwAst3GLeVBB
//                Toast.makeText(getApplicationContext(), deviceToken, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
        mPushAgent.enable(new IUmengCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "onFailure");
            }
        });
//        mPushAgent.setDebugMode(true);//设置为debug模式，输出log日志
        handler = new Handler(getMainLooper());
//        YouMengPushIntentService youMengPushIntentService;
//        mPushAgent.setPushIntentServiceClass(YouMengPushIntentService.class);
//        mPushAgent.setDebugMode(true);
//        mPushAgent.setPushIntentServiceClass(PushIntentService.class)
///        >>>>>>>>>>>>>>自定义行为的回调处理 Start  >>>>>>>>>>>>>>
        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

//        >>>>>>>>>>>>>>自定义行为的回调处理 End  >>>>>>>>>>>>>>
//        >>>>>>>>>>>>>>自定义消息/通知栏样式的回调处理 Start  >>>>>>>>>>>>>>

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                        Log.e(TAG, "自定义消息：--------> " + msg.custom);
                        Map<String, String> mmvap = msg.extra;
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        GetuserUpgradeid(msg);
                        return super.getNotification(context, msg);
                }
            }

            private void GetuserUpgradeid(UMessage msg) {
//{"decideAccountId":24,"inviteAccountId":23,"type":"0"}
                Intent RcvIntent = new Intent();
                RcvIntent.setAction(TransactionReceiver.TRANS_ACTION);
                Bundle bundle = new Bundle();
                ChatUMessage chatUMessage = new ChatUMessage();
                chatUMessage.setmsg(msg);
                bundle.putSerializable(TransactionReceiver.TRANS_Chat, (Serializable) chatUMessage);
//                Intent intent=new Intent(getContext(), WebMainActivity.class);
                RcvIntent.putExtra("bundle", bundle);
                sendBroadcast(RcvIntent);
                Log.e(TAG, "UMessage" + msg.ticker + " ->" + msg.title + " ->" + msg.text);
//               Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                ToastUtil.showToast(customstr);
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
        /**
         * 初始化厂商通道
         */
        //小米通道
        MiPushRegistar.register(this, "填写您在小米后台APP对应的xiaomi id", "填写您在小米后台APP对应的xiaomi key");
        //华为通道，注意华为通道的初始化参数在minifest中配置
        HuaWeiRegister.register(this);
        //魅族通道
        MeizuRegister.register(this, "填写您在魅族后台APP对应的app id", "填写您在魅族后台APP对应的app key");
        //OPPO通道
        OppoRegister.register(this, "填写您在OPPO后台APP对应的app key", "填写您在魅族后台APP对应的app secret");
        //VIVO 通道，注意VIVO通道的初始化参数在minifest中配置
        VivoRegister.register(this);
    }
    /**
     * 分享
     */
//    private void initUMengShare() {
//        //初始化分享sdk
//        UMShareAPI.get(this);
//        //微信
//        PlatformConfig.setWeixin(UrlConfig.UMENG_SHARE_WX_ID, UrlConfig.UMENG_SHARE_WX_KEY);
//        //QQ
//        PlatformConfig.setQQZone(UrlConfig.UMENG_SHARE_QQ_ID, UrlConfig.UMENG_SHARE_QQ_KEY);
//    }

    /**
     * 推送
     **/
//    @SuppressLint("HardwareIds")
//    private void initUMengPush() {
//        //获取消息推送代理示例
//        final PushAgent mPushAgent = PushAgent.getInstance(this);
//
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
//                LogUtils.i("注册成功：deviceToken：-------->  " + deviceToken);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                LogUtils.i("注册失败：-------->  " + "s:" + s + ",s1:" + s1);
//            }
//        });
//
//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//
//            /**
//             * 自定义通知栏样式的回调方法
//             */
//            @Override
//            @SuppressWarnings("deprecation")
//            public Notification getNotification(Context context, UMessage msg) {
//                switch (msg.builder_id) {
//                    case 1:
//                        Notification.Builder builder;
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            builder = new Notification.Builder(context, "1");
//                        } else {
//                            builder = new Notification.Builder(context);
//                        }
//                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),                                R.layout.notification_view);
//                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon,                                getSmallIconId(context, msg));
//                        builder.setContent(myNotificationView)
//                                .setSmallIcon(getSmallIconId(context, msg))
//                                .setTicker(msg.ticker)
//                                .setAutoCancel(true);
//
//                        return builder.build();
//                    default:
//                        //默认为0，若填写的builder_id并不存在，也使用默认。
//                        return super.getNotification(context, msg);
//                }
//            }
//        };
//        mPushAgent.setMessageHandler(messageHandler);
//
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
//            @Override
//            public void dealWithCustomAction(Context context, UMessage msg) {
//                LogUtils.i("umeng推送：--------> click ");
//            }
//
//        };
//
//        mPushAgent.setNotificationClickHandler(notificationClickHandler);
//
//    }

    /**
     * 创建application便于没有application的类中调用
     *
     * @return BaseApplication
     */
    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    /**
     * 注销账户 -- 清除账号
     * 跳转登录界面
     */
    public void exitLogin(Context context) {
        setUserId("");
        setAccount("");
        try {
            PersistentCookieStore.clearCookie();//本地cookie清空
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences("AccountInfo", MODE_PRIVATE);//SharedPreferences 是一个轻量级的存储类，主要是保存一些小的数据，一些状态信息
        //结束所有Activity，清空当前用户数据，返回主页面
        Intent tuichu = new Intent(getContext(), MainActivity.class);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("logo", "");//写空
        editor.putString("mobile", "");//写空
        editor.putString("nickName", "");//写空
        editor.putString("accountName", "");//写空
        editor.putString("des", "");//写空
        editor.putString("area", "");//写空
        editor.putString("promotionCode", "");//写空
        editor.putString("inviteCode", "");//写空
        editor.apply();
        tuichu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(tuichu);
//        UIManager.switcher(context, LoginActivity.class);

    }

    /**
     * 注销账户 -- 清除账号
     * 跳转登录界面
     */
    public void exitTokenLogin(Context context) {
        setUserId("");
        try {
            PersistentCookieStore.clearCookie();//本地cookie清空
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIManager.switcher(context, LoginActivity.class);
    }

    //退出APP
    public void exitApp() {
        try {
            for (Activity activity : mList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 设置userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
        SpUtils.put(getApplicationContext(), UrlConfig.USERID, null == userId ? "" : userId);
        Log.e("用他最早的方法设置userId", "结果好像是188个字符的token：" + userId);
    }

    /**
     * 获取账号
     */
    public String getAccount() {
        if (!StringUtils.isEmpty(phone)) {
            return phone;
        } else {
            return (String) SpUtils.get(getApplicationContext(), UrlConfig.ACCOUNT, "");
        }
    }

    /**
     * 设置账号
     */
    public void setAccount(String phone) {
        this.phone = phone;
        SpUtils.put(getApplicationContext(), UrlConfig.ACCOUNT, null == phone ? "" : phone);
    }

    /**
     * 判断用户是否登录
     * true已登录  false未登录
     */
    public boolean isLogin() {
        return !StringUtils.isEmpty(getUserId());
    }

    /**
     * 获取userId
     */
    public String getUserId() {
        if (!StringUtils.isEmpty(userId)) {
            return userId;
        } else {
            return (String) SpUtils.get(getApplicationContext(), UrlConfig.USERID, "");
        }
    }

    //add activity
    public void addActivity(Activity activity) {
        if (mList != null) {
            mList.add(activity);
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public void SetAccountId(String accountIdstr) {
        try {
            accountId = Long.valueOf(accountIdstr);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void SetUserLevel(String UserLevelstr) {
        try {
            userLevel = Integer.valueOf(UserLevelstr);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void SetAccountName(String AccountName) {
        this.AccountName=AccountName;
    }

    public String getAccountName() {
        return AccountName;
    }

    public String getPromotionCode() {
        return promotionCode;
//        return "666666a";
    }

    public void SetPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public boolean getAgree() {
        return Agreebf;
    }

    public void setAgree(boolean ssbf) {
        Agreebf = ssbf;
    }

    public void CheckDeviceToken() {
         accountId=-1;
        if(deviceId==null)
        {
            initUMengPush();
        }

    }
}

