package com.hn.book.model;

import com.hn.book.bean.HnBook;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.mall.model.TWebshopProductDetail;

public class BookDetailResultModel extends BaseModel {

    private HnBook body;

    public HnBook getBody() {
        return body;
    }

    public void setBody(HnBook body) {
        this.body = body;
    }
}
