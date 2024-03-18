package com.yangna.lbdsp.mall.presenter;

import android.content.Context;

import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.mall.impl.ProductView;
import com.yangna.lbdsp.mall.model.ProductDetailResultModel;
import com.yangna.lbdsp.mall.model.TWebshopProductDetail;
import com.yangna.lbdsp.mall.param.RequestProductDetailParam;

public class ProductPresenter extends BasePresenter {

    //主页回调
    private ProductView productView;

    public ProductPresenter(Context context) {
        super(context);
    }

    @Override
    protected void detachView() {
        productView = null;
    }

    public void setProductView(ProductView productView) {
        this.productView = productView;
    }


    /**
     *获取商品详情
     */
    public void getGoodsDetailByGoodsId(final Context context,String productId) {
//      Map<String, String> map = UrlConfig.getCommonMap();
//      map.put("token", BaseApplication.getInstance().getUserId());
        RequestProductDetailParam requestParam = new RequestProductDetailParam();
        requestParam.setGoodsId(productId);

        NetWorks.getInstance().getGoodsDetailByGoodsId(context, requestParam, new MyObserver<ProductDetailResultModel>() {
            @Override
            public void onNext(ProductDetailResultModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        TWebshopProductDetail productDetail=model.getBody();
                        productView.getGoodsDetailByGoodsId(model);
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
