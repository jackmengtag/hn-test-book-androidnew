package com.yangna.lbdsp.mall.model;

import com.yangna.lbdsp.common.base.BaseModel;

import java.util.ArrayList;


public class MallOrderListModel extends BaseModel {

    private MallOrderModel body;

    public MallOrderModel getBody() {
        return body;
    }

    public void setBody(MallOrderModel body) {
        this.body = body;
    }

    public class MallOrderModel {
        private int currentPage;
        private int maxResults;
        private int totalPages;//总页数
        private int totalRecords;//总数量
        private ArrayList<MallOrderList> records;

        public ArrayList<MallOrderList> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<MallOrderList> records) {
            this.records = records;
        }

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

    }

    public class MallOrderList {
        private String receveidName; //收货人姓名
        private String orderSn; //订单号
        private String createTime;//创建时间
        private double totalAmount; //订单金额（总金额）
        private String shopId;//商家
        private String shopName;//店铺名称
        private String shoplogo;//店铺logo
        private String orderStatus;//订单状态
        private int shoppingUser;//	买家
        private String imageUrl;//	商品图片
        private String productName;//商品名称

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShoplogo() {
            return shoplogo;
        }

        public void setShoplogo(String shoplogo) {
            this.shoplogo = shoplogo;
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

        private double real_pay_amount; //实付价钱

        public String getReceveidName() {
            return receveidName;
        }

        public void setReceveidName(String receveidName) {
            this.receveidName = receveidName;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getShoppingUser() {
            return shoppingUser;
        }

        public void setShoppingUser(int shoppingUser) {
            this.shoppingUser = shoppingUser;
        }

        public double getReal_pay_amount() {
            return real_pay_amount;
        }

        public void setReal_pay_amount(double real_pay_amount) {
            this.real_pay_amount = real_pay_amount;
        }
    }

}
