package com.yangna.lbdsp.home.presenter;

import com.yangna.lbdsp.common.base.IBaseView;

import rx.Subscription;

public class BaseGoodsPresenter<V extends IBaseView> {
    public V mView;
    protected Subscription mSubscription;

    public BaseGoodsPresenter(V view){
        mView = view;
    }

    public void detach() {
        mView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}