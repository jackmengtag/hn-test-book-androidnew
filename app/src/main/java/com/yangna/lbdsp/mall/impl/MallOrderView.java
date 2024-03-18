package com.yangna.lbdsp.mall.impl;


import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.mall.model.MallOrderListModel;

public interface MallOrderView {

    void onGetMallOrderData(MallOrderListModel model);

    void OnReturnGoods(BaseModel model);
}
