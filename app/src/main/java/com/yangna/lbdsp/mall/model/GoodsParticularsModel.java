package com.yangna.lbdsp.mall.model;

public class GoodsParticularsModel {

    private String id;
    private String goodsId;
    private String des;

    // 商品详情 /account/good/getGoodsDetailByGoodsId 接口返回值 本来不需要model值的，只是为了重复写一个值而已，反正是临时用，2020年11月7日15:05:04
    //{
    //  "currentPage": 0,
    //  "maxResults": 10,
    //  "totalPages": 0,
    //  "totalRecords": 0,
    //  "records": [
    //    {
    //      "id": 14,           传回来的没什么用的id
    //      "goodsId": 14,      传回来的单个商品id
    //      "des": "ykyjgjgj"   详情
    //    }
    //  ]
    //}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
