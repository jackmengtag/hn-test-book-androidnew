package com.yangna.lbdsp.home.impl;

import com.yangna.lbdsp.common.base.IBaseView;
import com.yangna.lbdsp.mall.bean.MallGoodsModel;

import java.util.List;

public interface MallGoodsGridItemView extends IBaseView {
    void onSucces(List<MallGoodsModel> data);
}
