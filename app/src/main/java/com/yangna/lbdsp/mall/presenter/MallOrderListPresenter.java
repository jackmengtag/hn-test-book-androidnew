package com.yangna.lbdsp.mall.presenter;

import android.content.Context;
import android.util.Log;

import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.common.base.BasePresenter;
import com.yangna.lbdsp.common.eventbus.BusMallActual;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.mall.bean.MallOrderTagBean;
import com.yangna.lbdsp.mall.bean.SuggestBean;
import com.yangna.lbdsp.mall.impl.MallOrderView;
import com.yangna.lbdsp.mall.model.MallOrderListModel;

import org.greenrobot.eventbus.EventBus;


public class MallOrderListPresenter extends BasePresenter {

    private MallOrderView mallOrderView;
    private static final String TAG = "MallOrderListFragment";

    public MallOrderListPresenter(Context context) {
        super(context);
    }

    public void setMallOrderView(MallOrderView mallOrderView) {
        this.mallOrderView = mallOrderView;
    }

    /**
     * 订单列表
     */
    public void getMallAllOrder(final Context context, int sendStatus) {
        MallOrderTagBean mallOrderTagBean = new MallOrderTagBean();
        Log.i(TAG, "oilStatus" + sendStatus);
        mallOrderTagBean.setOrderStatus(sendStatus);
            mallOrderTagBean.setOrderStatus(sendStatus);
            mallOrderTagBean.setCurrentPage(1);
            mallOrderTagBean.setPageSize(10000);

        NetWorks.getInstance().getMallAllOrder(context, mallOrderTagBean, new MyObserver<MallOrderListModel>() {
            @Override
            public void onNext(MallOrderListModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        Log.d("获取到订单列表了", "订单个数" + model.getBody().getRecords().size());
                        mallOrderView.onGetMallOrderData(model);
                    } else {

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
     * 退货
     */
    public void OnReturnGoods(final Context context,String orderSn) {

        SuggestBean suggestBean = new SuggestBean();
        suggestBean.setOrderSn(orderSn);


        NetWorks.getInstance().OnReturnGoods(context, suggestBean, new MyObserver<BaseModel>() {
            @Override
            public void onNext(BaseModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        mallOrderView.OnReturnGoods(model);
                        EventBus.getDefault().post(new BusMallActual());
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

    @Override
    protected void detachView() {
        mallOrderView = null;
    }
}
