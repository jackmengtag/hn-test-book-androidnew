package com.yangna.lbdsp.mall.model;

import java.util.Date;
import java.io.Serializable;



/**
 * (TWebshopOrderComment)表实体类-
 *
 * @author makejava
 * @since 2020-12-31 11:01:50
 */

public class TWebshopOrderComment implements Serializable {



    private String id;

    /**
     * 订单号
     */

    private String orderSn;

    /**
     * 买家
     */

    private String shoppingUser;

    /**
     * 商品id
     */

    private String productId;

    /**
     * 创建时间
     */

    private String createTime;

    /**
     * 评论状态
     */

    private Integer deleted;



    private String content;



    private String imageUrl1;


    private String imageUrl2;


    private String imageUrl3;


    private Integer isResponse;


    private Integer status;


    private String parentId;


    public TWebshopOrderComment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getShoppingUser() {
        return shoppingUser;
    }

    public void setShoppingUser(String shoppingUser) {
        this.shoppingUser = shoppingUser;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    public Integer getIsResponse() {
        return isResponse;
    }

    public void setIsResponse(Integer isResponse) {
        this.isResponse = isResponse;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}