package com.yangna.lbdsp.home.bean;

import com.yangna.lbdsp.common.base.BaseModel;

import java.math.BigDecimal;

public class TGoods extends BaseModel {

    private String id;
    //商店id
    private String shopId;
    //分类id
    private String cateId;
    //商品名称
    private String goodsName;
    //商品价格
    private String goodsPrice;
    //商品数量
    private String goodsAmount;

    private String coverVideoId;

    private String coverVideoUrl;
    //状态
    private String status;

    private String pictureUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getCoverVideoId() {
        return coverVideoId;
    }

    public void setCoverVideoId(String coverVideoId) {
        this.coverVideoId = coverVideoId;
    }

    public String getCoverVideoUrl() {
        return coverVideoUrl;
    }

    public void setCoverVideoUrl(String coverVideoUrl) {
        this.coverVideoUrl = coverVideoUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
