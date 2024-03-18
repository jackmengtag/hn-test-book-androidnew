package com.yangna.lbdsp.mall.param;

import com.yangna.lbdsp.mall.model.TWebshopProductSpceficationPage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品(TWebshopProduct)表实体类--详情
 *
 * @author makejava
 * @since 2020-12-15 15:54:04
 */

public class TWebshopProductSpecficationParam implements Serializable {

    private String id;
    /**
     * 商品名称
     */

    private String productName;

    /**
     * 商品价格
     */

    private Double productPrice;


   private String imageUrl;

    /**
     * 商品货号
     */
    private String shelfNumber;

    private Integer productNumber=1;

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    /**
     * 库存
     */

    private Integer stockCount;

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public String getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(String shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    /**
     * 商品规格
     */

    private List<TWebshopProductSpceficationPage> specficationList=new ArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<TWebshopProductSpceficationPage> getSpecficationList() {
        return specficationList;
    }

    public void setSpecficationList(List<TWebshopProductSpceficationPage> specficationList) {
        this.specficationList = specficationList;
    }
}