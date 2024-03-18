package com.yangna.lbdsp.mall.model;

import java.io.Serializable;
import java.util.Date;


/**
 * 商品规格(TWebshopProductSpcefication)表实体类--列表
 *
 * @author makejava
 * @since 2021-01-19 16:02:52
 */

public class TWebshopProductSpceficationPage implements Serializable {

    private String id;
    /**
     *规格名称
     */

    private String ruleName;
    /**
     *创建时间
     */

    private String createTime;
    /**
     *商品id
     */

    private String productId;
    /**
     *规格类型id
     */

    private String typeId;
    /**
     *商品价格
     */

    private Double productPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}