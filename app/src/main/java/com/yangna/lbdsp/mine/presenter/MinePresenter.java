package com.yangna.lbdsp.mine.presenter;

import android.content.Context;

import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.home.bean.GainVideoBean;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.login.model.UserInfoModel;
import com.yangna.lbdsp.mine.impl.MineView;
import com.yangna.lbdsp.mine.model.BasePageRequestParam;

import java.util.Map;

public class MinePresenter extends BasePresenter {

    //主页回调
    private MineView mineView;

    public MinePresenter(Context context) {
        super(context);
    }

    @Override
    protected void detachView() {
        mineView = null;
    }

    public void setMineView(MineView mineView) {
        this.mineView = mineView;
    }


    /**
     * 个人信息
     */
    public void getMineInfo(final Context context) {
//        Map<String, String> map = UrlConfig.getCommonMap();
//        map.put("token", BaseApplication.getInstance().getUserId());
        BasePageRequestParam requestParam = new BasePageRequestParam();
        requestParam.setCurrentPage("1");
        requestParam.setPageSize("4");
        requestParam.setToken(BaseApplication.getInstance().getUserId());
        NetWorks.getInstance().getMineInfo(context, requestParam, new MyObserver<UserInfoModel>() {
            @Override
            public void onNext(UserInfoModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        //BaseApplication.getInstance().getUserId()
                        BaseApplication.getInstance().SetPromotionCode(model.getBody().getAccount().getPromotionCode());
                        BaseApplication.getInstance().SetAccountId(model.getBody().getAccount().getId());
                        BaseApplication.getInstance().SetUserLevel(model.getBody().getAccount().getUserLevel());
                        BaseApplication.getInstance().SetAccountName(model.getBody().getAccount().getAccountName());
                        mineView.onGetMineInfo(model);
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
     * 获取个人点赞过的视频
     */
    public void getUserLoveVideoList(final Context context,BasePageRequestParam requestParam) {

        NetWorks.getInstance().getStatisticeVideo(context, requestParam,new MyObserver<VideoListModel>() {
            @Override
            public void onNext(VideoListModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        mineView.onUserLoveVideo(model);
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

}
