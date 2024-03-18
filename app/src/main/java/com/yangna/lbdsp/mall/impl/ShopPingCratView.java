package com.yangna.lbdsp.mall.impl;

import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.mall.model.ShopPingCartModel;

public interface ShopPingCratView {

    void onGetShopCartList(ShopPingCartModel shopPingCartModel);

    void onDeteShopCart(BaseModel baseModel);
}
