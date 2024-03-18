package com.yangna.lbdsp.mine.presenter;

import android.content.Context;

import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.home.bean.GainVideoBean;
import com.yangna.lbdsp.home.model.VideoListModel;
import com.yangna.lbdsp.mine.impl.PersonalView;
import com.yangna.lbdsp.mine.model.BasePageRequestParam;

public class HomepagePresenter extends BasePresenter {

    private PersonalView personalView;

    private HomepagePresenter homepagePresenter;

    public HomepagePresenter(Context context) {
        super(context);
    }

    public void setPersonalView(PersonalView personalView) {
        this.personalView = personalView;
    }

    @Override
    protected void detachView() {
        personalView = null;
    }


    /**
     * 获取个人视频
     */
    public void getMineInfo(final Context context) {

        GainVideoBean gainVideoBean = new GainVideoBean();
        gainVideoBean.setCurrentPage(0);

        NetWorks.getInstance().getMineVideo(context, gainVideoBean, new MyObserver<VideoListModel>() {
            @Override
            public void onNext(VideoListModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                          personalView.onMineVideo(model);
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
        
        NetWorks.getInstance().getStatisticeVideo(context,requestParam, new MyObserver<VideoListModel>() {
            @Override
            public void onNext(VideoListModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        personalView.onUserLoveVideo(model);
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
