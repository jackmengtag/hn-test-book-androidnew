package com.yangna.lbdsp.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.yangna.lbdsp.common.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class MallGoodsModel extends BaseModel {
    /* 2021年1月15日12:52:31 新版 门店内商品列表 /api/tWebshopProduct 接口返回值 */
    //{
    //	"body": {
    //		"currentPage": 1,
    //		"maxResults": 4,
    //		"totalPages": 1,
    //		"totalRecords": 2,
    //		"records": [{
    //			"id": "20",
    //			"productName": "鲈鱼",
    //			"productUnite": "斤",
    //			"shelfNumber": "0902010900000",
    //			"brandId": null,
    //			"productTypeId": "4",
    //			"stockCount": 100,
    //			"limitShoppingCount": null,
    //			"salesCount": null,
    //			"deployTime": "2020-10-06 16:39:47",
    //			"createTime": "2020-10-06 16:39:47",
    //			"productPrice": 23.0,
    //			"productKeyword": "鲈鱼",
    //			"productDesc": "鲈鱼",
    //			"cityPrice": 23.0,
    //			"marketPrice": 23.0,
    //			"alarmCount": null,
    //			"categoryId": "4",
    //			"isOpenSpecification": null,
    //			"carriageId": null,
    //			"isOnShelf": null,
    //			"isVirtualProduct": 0,
    //			"isLongTimeValid": null,
    //			"startTime": "2020-08-24 16:49:50",
    //			"endTime": "2020-10-24 16:49:50",
    //			"isSupportRetrunMoney": 1,
    //			"isVerificationCodeRightNow": 1,
    //			"afterPaymentHoursCount": 10,
    //			"useDes": null,
    //			"shopId": "4",
    //			"productScore": null,
    //			"deployType": 1,
    //			"appraiseCount": null,
    //			"goodAppraiseRate": null,
    //			"viewCount": null,
    //			"mainImageUrls": ["http://lbdsp.com/1610681126617mxN0FI.jpg"],
    //			"detailImageUrls": []
    //		}]
    //	},
    //	"state": 200,
    //	"msg": "请求成功"
    //}

    public GoodsList body;

    public GoodsList getBody() {
        return body;
    }

    public void setBody(GoodsList body) {
        this.body = body;
    }

    public static class GoodsList{

        private int currentPage;
        private int maxResults;
        private int totalPages;
        private int totalRecords;
        private ArrayList<Records> records;

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

        public ArrayList<Records> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<Records> records) {
            this.records = records;
        }

        public class Records implements Parcelable{

            private String id;
            private String productName;
            private String productUnite;
            private String shelfNumber;
            private String brandId;
            private String productTypeId;
            private String stockCount;
            private String limitShoppingCount;
            private String salesCount;
            private String deployTime;
            private String createTime;
            private String productPrice;
            private String productKeyword;
            private String productDesc;
            private String cityPrice;
            private String marketPrice;
            private String alarmCount;
            private String categoryId;
            private String isOpenSpecification;
            private String carriageId;
            private String isOnShelf;
            private String isVirtualProduct;
            private String isLongTimeValid;
            private String startTime;
            private String endTime;
            private String isSupportRetrunMoney;
            private String isVerificationCodeRightNow;
            private String afterPaymentHoursCount;
            private String useDes;
            private String shopId;
            private String productScore;
            private String deployType;
            private String appraiseCount;
            private String goodAppraiseRate;
            private String viewCount;
            private String deleted;

            private int width;//后面加的，为了实现瀑布流效果
            private int height;

            public Records(){

            }

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

            public String getProductUnite() {
                return productUnite;
            }

            public void setProductUnite(String productUnite) {
                this.productUnite = productUnite;
            }

            public String getShelfNumber() {
                return shelfNumber;
            }

            public void setShelfNumber(String shelfNumber) {
                this.shelfNumber = shelfNumber;
            }

            public String getBrandId() {
                return brandId;
            }

            public void setBrandId(String brandId) {
                this.brandId = brandId;
            }

            public String getProductTypeId() {
                return productTypeId;
            }

            public void setProductTypeId(String productTypeId) {
                this.productTypeId = productTypeId;
            }

            public String getStockCount() {
                return stockCount;
            }

            public void setStockCount(String stockCount) {
                this.stockCount = stockCount;
            }

            public String getLimitShoppingCount() {
                return limitShoppingCount;
            }

            public void setLimitShoppingCount(String limitShoppingCount) {
                this.limitShoppingCount = limitShoppingCount;
            }

            public String getSalesCount() {
                return salesCount;
            }

            public void setSalesCount(String salesCount) {
                this.salesCount = salesCount;
            }

            public String getDeployTime() {
                return deployTime;
            }

            public void setDeployTime(String deployTime) {
                this.deployTime = deployTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(String productPrice) {
                this.productPrice = productPrice;
            }

            public String getProductKeyword() {
                return productKeyword;
            }

            public void setProductKeyword(String productKeyword) {
                this.productKeyword = productKeyword;
            }

            public String getProductDesc() {
                return productDesc;
            }

            public void setProductDesc(String productDesc) {
                this.productDesc = productDesc;
            }

            public String getCityPrice() {
                return cityPrice;
            }

            public void setCityPrice(String cityPrice) {
                this.cityPrice = cityPrice;
            }

            public String getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(String marketPrice) {
                this.marketPrice = marketPrice;
            }

            public String getAlarmCount() {
                return alarmCount;
            }

            public void setAlarmCount(String alarmCount) {
                this.alarmCount = alarmCount;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getIsOpenSpecification() {
                return isOpenSpecification;
            }

            public void setIsOpenSpecification(String isOpenSpecification) {
                this.isOpenSpecification = isOpenSpecification;
            }

            public String getCarriageId() {
                return carriageId;
            }

            public void setCarriageId(String carriageId) {
                this.carriageId = carriageId;
            }

            public String getIsOnShelf() {
                return isOnShelf;
            }

            public void setIsOnShelf(String isOnShelf) {
                this.isOnShelf = isOnShelf;
            }

            public String getIsVirtualProduct() {
                return isVirtualProduct;
            }

            public void setIsVirtualProduct(String isVirtualProduct) {
                this.isVirtualProduct = isVirtualProduct;
            }

            public String getIsLongTimeValid() {
                return isLongTimeValid;
            }

            public void setIsLongTimeValid(String isLongTimeValid) {
                this.isLongTimeValid = isLongTimeValid;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getIsSupportRetrunMoney() {
                return isSupportRetrunMoney;
            }

            public void setIsSupportRetrunMoney(String isSupportRetrunMoney) {
                this.isSupportRetrunMoney = isSupportRetrunMoney;
            }

            public String getIsVerificationCodeRightNow() {
                return isVerificationCodeRightNow;
            }

            public void setIsVerificationCodeRightNow(String isVerificationCodeRightNow) {
                this.isVerificationCodeRightNow = isVerificationCodeRightNow;
            }

            public String getAfterPaymentHoursCount() {
                return afterPaymentHoursCount;
            }

            public void setAfterPaymentHoursCount(String afterPaymentHoursCount) {
                this.afterPaymentHoursCount = afterPaymentHoursCount;
            }

            public String getUseDes() {
                return useDes;
            }

            public void setUseDes(String useDes) {
                this.useDes = useDes;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getProductScore() {
                return productScore;
            }

            public void setProductScore(String productScore) {
                this.productScore = productScore;
            }

            public String getDeployType() {
                return deployType;
            }

            public void setDeployType(String deployType) {
                this.deployType = deployType;
            }

            public String getAppraiseCount() {
                return appraiseCount;
            }

            public void setAppraiseCount(String appraiseCount) {
                this.appraiseCount = appraiseCount;
            }

            public String getGoodAppraiseRate() {
                return goodAppraiseRate;
            }

            public void setGoodAppraiseRate(String goodAppraiseRate) {
                this.goodAppraiseRate = goodAppraiseRate;
            }

            public String getViewCount() {
                return viewCount;
            }

            public void setViewCount(String viewCount) {
                this.viewCount = viewCount;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            protected Records(Parcel in) {
                this.width = in.readInt();
                this.height = in.readInt();
            }

            public  final Creator<Records> CREATOR = new Creator<Records>() {
                @Override
                public Records createFromParcel(Parcel in) {
                    return new Records(in);
                }

                @Override
                public Records[] newArray(int size) {
                    return new Records[size];
                }
            };

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.width);
                dest.writeInt(this.height);
            }
        }
    }
}
