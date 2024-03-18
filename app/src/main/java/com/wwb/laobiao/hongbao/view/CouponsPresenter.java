package com.wwb.laobiao.hongbao.view;

import android.content.Context;

import com.wwb.laobiao.hongbao.model.HongbaoModel;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;

//import com.qzy.laobiao.common.UrlConfig;
//import com.qzy.laobiao.common.base.BaseApplication;
//import com.qzy.laobiao.common.base.BasePresenter;
//import com.qzy.laobiao.common.manager.ToastManager;
//import com.qzy.laobiao.common.net.HongbaoModel;
//import com.qzy.laobiao.common.net.MyObserver;
//import com.qzy.laobiao.common.net.NetWorks;
//import com.qzy.laobiao.login.model.UserInfoModel;

@SuppressWarnings("JoinDeclarationAndAssignmentJava")
public class CouponsPresenter extends BasePresenter {

    private HongbaoFragment hongbaoFragment;

    protected CouponsPresenter(Context context) {
        super(context);
        gethongbao(context);
    }

    public void gethongbao(Context context) {
//        Map<String, String> map = UrlConfig.getCommonMap();
//        map.put("token", BaseApplication.getInstance().getUserId());
        {
//

            MyObserver<HongbaoModel> observerx= new MyObserver<HongbaoModel>() {
//                @Override
//                public void onSubscribe(Disposable d) {
//                    ToastManager.showToast(context, "onS");
//                }
                @Override
                public void onNext(HongbaoModel hongbaoModel) {
                    ToastManager.showToast(context, ""+hongbaoModel.getMsg());
                }
                @Override
                public void onError(Throwable e) {
                    ToastManager.showToast(context, "onEr");
                }
//                @Override
//                public void onComplete() {
//                    ToastManager.showToast(context, "onC");
//                }
            };
//            Hongbao userx= new Hongbao();
            NetWorks.getInstance().gethongbao(context,observerx);
        }

    }

    @Override
    protected void detachView() {

    }
    public void ssv() {
    }
    public void sethongbaoview(HongbaoFragment hongbaoFragment) {
        this.hongbaoFragment=hongbaoFragment;
    }
}
