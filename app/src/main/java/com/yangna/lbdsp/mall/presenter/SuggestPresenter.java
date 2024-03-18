package com.yangna.lbdsp.mall.presenter;

import android.content.Context;


import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.mall.bean.SuggestBean;
import com.yangna.lbdsp.mall.impl.SuggestView;



public class SuggestPresenter extends BasePresenter {

    private SuggestView suggestView;

    public SuggestPresenter(Context context) {
        super(context);
    }

    public void setSuggestView(SuggestView suggestView) {
        this.suggestView = suggestView;
    }

    @Override
    protected void detachView() {
        suggestView = null;
    }


    /**
     * 网络请求 意见反馈
     */
    public void getSuggest(final Context context,String suggest, String ordersn) {
        SuggestBean suggestBean = new SuggestBean();
        suggestBean.setContent(suggest);
        suggestBean.setOrderSn(ordersn);

        NetWorks.getInstance().getSuggest(context, suggestBean, new MyObserver<BaseModel>() {
            @Override
            public void onNext(BaseModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        suggestView.onSuggestData(model);
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
