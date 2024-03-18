package com.yangna.lbdsp.mall.presenter;

import android.content.Context;
import android.util.Log;

import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.mall.bean.DeteShopCartBean;
import com.yangna.lbdsp.mall.bean.ShopCartBean;
import com.yangna.lbdsp.mall.impl.ShopPingCratView;
import com.yangna.lbdsp.mall.model.ShopPingCartModel;

import java.util.ArrayList;

public class ShopPingCartPresenter extends BasePresenter {

    private static final String TAG = "ShopPingCartPresenter";

    private ShopPingCratView shopPingCratView;

    public ShopPingCartPresenter(Context context) {
        super(context);
    }

    public void setShopPingCratView(ShopPingCratView shopPingCratView) {
        this.shopPingCratView = shopPingCratView;
    }

    @Override
    protected void detachView() {
        shopPingCratView = null;
    }

    /**
     * 购物车列表
     */
    public void getShopCartList(final Context context) {
        ShopCartBean shopCartBean = new ShopCartBean();
        shopCartBean.setCurrentPage("1");
        shopCartBean.setPageSize("10");
        NetWorks.getInstance().getShopCart(context, shopCartBean, new MyObserver<ShopPingCartModel>() {
            @Override
            public void onNext(ShopPingCartModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        Log.i(TAG, "获取成功购物车列表----" + model);
                        shopPingCratView.onGetShopCartList(model);
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("ShoppingCartActivity", "报错了----" + e);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }
        });
    }


    /**
     * 删除物品
     */
    public void deteShopCart(final Context context, ArrayList<String> ids) {
        DeteShopCartBean deteShopCartBean = new DeteShopCartBean();
        deteShopCartBean.getIds().clear();
        if (ids.size() <= 0) {

        } else {
            deteShopCartBean.setIds(ids);
        }

        NetWorks.getInstance().deteShopCart(context, deteShopCartBean, new MyObserver<BaseModel>() {
            @Override
            public void onNext(BaseModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        Log.i(TAG, "删除购物车商品----");
                        shopPingCratView.onDeteShopCart(model);
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("ShoppingCartActivity", "报错了----" + e);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);
            }
        });

    }
}
