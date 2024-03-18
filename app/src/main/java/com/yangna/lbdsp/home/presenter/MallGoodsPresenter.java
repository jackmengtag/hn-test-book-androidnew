package com.yangna.lbdsp.home.presenter;

import android.content.Context;
import android.util.Log;

import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.mall.bean.MallGoodsModel;
import com.yangna.lbdsp.mall.bean.MallGoodsWPBLBaseModel;
import com.yangna.lbdsp.mall.bean.MallGoodsWPBLRecords;
import com.yangna.lbdsp.mall.bean.RequestShopProduct;
import com.yangna.lbdsp.home.impl.MallGoodsView;

import java.util.List;

import io.reactivex.annotations.NonNull;

/* 店内商品列表 */
public class MallGoodsPresenter extends BasePresenter {

    private MallGoodsView mallGoodsView;

    public MallGoodsView getMallGoodsView() {
        return mallGoodsView;
    }

    public void setMallGoodsView(MallGoodsView mallGoodsView) {
        this.mallGoodsView = mallGoodsView;
    }

    public MallGoodsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void detachView() {
        mallGoodsView = null;
    }

    /**
     * 获取门店内商品列表
     *
     * @param shopId      门店id
     * @param currentPage 当前访问页数
     * @param pageSize    每页获取的商品个数
     */
    public void getGoodsByShopId(final Context context, String shopId, int currentPage, String pageSize) {
        RequestShopProduct shopProduct = new RequestShopProduct();
        shopProduct.setShopId(shopId);
        shopProduct.setCurrentPage(String.valueOf(currentPage));
        shopProduct.setPageSize(pageSize);
        NetWorks.getInstance().getGoodsByShopId(context, shopProduct, new MyObserver<MallGoodsWPBLBaseModel>() {
            @Override
            public void onNext(@NonNull MallGoodsWPBLBaseModel result) {
                try {
                    if (UrlConfig.RESULT_OK == result.getState()) {
//                        Log.w("获取门店内商品列表", "成功。但是有没有商品还不一定");
//                        List<MallGoodsWPBLRecords> mallGoodsWPBLRecords = result.getRecords();
                        mallGoodsView.onGetMallGoodsData(result/*result.getRecords()*/);
                    } else {
                        ToastManager.showToast(context, result.getMsg());
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
