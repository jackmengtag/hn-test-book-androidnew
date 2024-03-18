package com.yangna.lbdsp.mall.model;

import com.yangna.lbdsp.common.base.BaseModel;

public class ProductDetailResultModel extends BaseModel {

    private TWebshopProductDetail body;

    public TWebshopProductDetail getBody() {
        return body;
    }

    public void setBody(TWebshopProductDetail body) {
        this.body = body;
    }
}
