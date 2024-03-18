package com.wwb.laobiao.instance;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;
import java.util.Observable;

public class WeiXinPay  {
    private WeiXinPay(Context context) {
        mContext = context;
    }
    private static WeiXinPay mWeiXinPay;
    private IWXAPI mIWXAPI;
    private Context mContext;
    public static WeiXinPay getInstance( Context mContext) {
        if (mWeiXinPay == null) {
            synchronized (WeiXinPay.class) {
                if (mWeiXinPay == null) {
                    mWeiXinPay = new WeiXinPay(mContext);
                }
            }

        }
        return mWeiXinPay;
    }
    /**
     * 初始化微信支付接口
     *
     * @param appId
     */
    public void init(String appId) {
        mIWXAPI = WXAPIFactory.createWXAPI(mContext, null);
        mIWXAPI.registerApp(appId);
    }
//    /**
//     * 调起支付
//     *
//     * @param appId     应用ID
//     * @param partnerId 商户ID
//     * @param prepayId  预付订单
//     * @param nonceStr  随机字符串
//     * @param timeStamp 时间戳
//     * @param sign      签名
//     */
    public int startWXPay(PayReq request ) {
        init(request.appId);
        if (!checkWx()) {
//            if (listener != null) {
//                listener.onPayError(WEIXIN_VERSION_LOW, "未安装微信或者微信版本过低");
//            }
            return -1;
        }

//        PayReq request = new PayReq();
//        request.appId = appId;
//        request.partnerId = partnerId;
//        request.prepayId = prepayId;
//        request.packageValue = "Sign=WXPay";
//        request.nonceStr = nonceStr;
//        request.timeStamp = timeStamp;
//        request.sign = sign;
          if(mIWXAPI.sendReq(request))
          {
              return 1;
          }
          else
          {
              return 0;
          }
    }
    //检测微信客户端是否支持微信支付
    private boolean checkWx() {
        return isWeiXinAvailable() && mIWXAPI.isWXAppInstalled() && mIWXAPI.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }
    /**
     * 判断微信是否安装
     *
     * @return 微信是否安装
     */
    public boolean isWeiXinAvailable() {
        return appIsAvailable("com.tencent.mm");
    }
    /**
     * 判断app是否安装
     *
     * @param packageName 应用包名
     * @return 是否安装
     */
    private boolean appIsAvailable(String packageName) {
        final PackageManager packageManager = mContext.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
