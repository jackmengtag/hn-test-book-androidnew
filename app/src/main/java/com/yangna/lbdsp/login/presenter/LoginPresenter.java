package com.yangna.lbdsp.login.presenter;


import android.content.Context;
import android.util.Log;
import android.widget.Button;

import com.yangna.lbdsp.MainActivity;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.login.bean.CreateFlie;
import com.yangna.lbdsp.login.bean.User;
import com.yangna.lbdsp.login.bean.YzCode;
import com.yangna.lbdsp.login.impl.LoginView;
import com.yangna.lbdsp.login.impl.UploadAddresView;
import com.yangna.lbdsp.login.model.FileModel;
import com.yangna.lbdsp.login.model.LoginModel;
import com.yangna.lbdsp.login.model.YzCodeModel;
import com.yangna.lbdsp.widget.CountDownButton;

/**
 * artifact  登录业务操作
 */
public class LoginPresenter extends BasePresenter {

    private LoginView loginView;

    private UploadAddresView uploadAddresView;

    public LoginPresenter(Context context) {
        super(context);
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void setUploadAddresView(UploadAddresView uploadAddresView) {
        this.uploadAddresView = uploadAddresView;
    }

    @Override
    protected void detachView() {
        loginView = null;
    }

    /**
     * 登录
     *
     * @param context  上下文
     * @param mobile     用户名
     * @param verifyCode 密码
     */
    public void goLogin(final Context context, String area, String mobile, String verifyCode,String promotionCode) {
        User user = new User();
        user.setArea(area);
        user.setMobile(mobile);
        user.setVerifyCode(verifyCode);
        user.setpromotionCode(promotionCode);
        user.setDeviceToken(BaseApplication.getInstance().getDeviceId());

        NetWorks.getInstance().goLogin(context, user, new MyObserver<LoginModel>() {
            @Override
            public void onNext(LoginModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        Log.w("最早拿到188个字符","的TOKEN的地方，model.getBody() = " + model.getBody());
                        loginView.onGetLoginData(model);
//                        UIManager.switcher(context, VideoListActivity.class);
                        ToastManager.showToast(context, "登陆成功");
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                        BaseApplication.getInstance().CheckDeviceToken();
                    }
                    loginView.onGetDataLoading(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }
        });

    }

    /**
     * 注册
     *
     * @param context 上下文
     * @param mobile   手机号
     * @param verifyCode     验证码
     */
    public void goRegister(final Context context, String area, String mobile, String verifyCode) {
        User user = new User();
        user.setMobile(mobile);
        user.setVerifyCode(verifyCode);
        user.setArea(area);

        NetWorks.getInstance().getRegister(context, user, new MyObserver<LoginModel>() {
            @Override
            public void onNext(LoginModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
//                        loginView.onGetLoginData(model);
                        UIManager.switcher(context, MainActivity.class);
                        ToastManager.showToast(context, "注册成功");
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }
        });

    }


    /**
     * 发送注册短信
     *
     * @param context 上下文
     * @param phone   手机号
     */
    public void sendCode(final Context context, String phone, final Button get_code_btn) {
        YzCode yzCode = new YzCode();
        yzCode.setMobile(phone);

        NetWorks.getInstance().checkPhoneCode(context, yzCode, new MyObserver<YzCodeModel>() {
            @Override
            public void onNext(YzCodeModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        CountDownButton btn = new CountDownButton(60000, 1000, context, get_code_btn, 1);
                        btn.start();
                        ToastManager.showToast(context, model.getMsg());
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }
        });
    }

    /**
     * loading页面
     */
    public void initLoadingView() {
        loginView.onGetDataLoading(false);
    }



}
