package com.yangna.lbdsp.mall.model;

import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopPingCartModel extends BaseModel implements Serializable {

    public ShopCartlist body;

    public ShopCartlist getBody() {
        return body;
    }

    public void setBody(ShopCartlist body) {
        this.body = body;
    }

    public class ShopCartlist implements Serializable {
        private int currentPage;
        private int maxResults;
        private int totalPages;
        private int totalRecords;
        private ArrayList<MallList> records;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getMaxResults() {
            return maxResults;
        }

        public void setMaxResults(int maxResults) {
            this.maxResults = maxResults;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public ArrayList<MallList> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<MallList> records) {
            this.records = records;
        }
    }

    public class MallList implements Serializable {
        private ArrayList<CardProducts> cardProduct;
        private String shopId;
        private String shopName; //商铺名称

        public ArrayList<CardProducts> getCardProduct() {
            return cardProduct;
        }

        public void setCardProduct(ArrayList<CardProducts> cardProduct) {
            this.cardProduct = cardProduct;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }


        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }
    }

    public class CardProducts implements Serializable {
        private String id;
        private String imageUrl;//商品图片
        private String productName;//商品名称
        private String productSpecficationId;//规格ID
        private String productSpecficationName;//规格名称
        private int productNumber; //商品数量
        private double productPrice; //商品价钱

        public String getProductSpecficationId() {
            return productSpecficationId;
        }

        public void setProductSpecficationId(String productSpecficationId) {
            this.productSpecficationId = productSpecficationId;
        }

        public String getProductSpecficationName() {
            return productSpecficationName;
        }

        public void setProductSpecficationName(String productSpecficationName) {
            this.productSpecficationName = productSpecficationName;
        }

        public int getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(int productNumber) {
            this.productNumber = productNumber;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(double productPrice) {
            this.productPrice = productPrice;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }


    }


}
