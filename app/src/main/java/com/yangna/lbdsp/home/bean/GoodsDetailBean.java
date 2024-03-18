package com.yangna.lbdsp.home.bean;

import com.yangna.lbdsp.common.base.BaseModel;

import java.util.Date;
import java.util.List;

public class GoodsDetailBean extends BaseModel {

    /* 2021年1月19日16:35:32 新版商品详情接口 /api/tWebshopProduct/getGoodsDetailByGoodsId */
    //{
    //    "body":{
    //        "id":"757f85ab42183f46a1b92a8ce9420b0a3a84",
    //        "productName":"wbtp",                             商品名称
    //        "productUnite":"ye",                              商品单位
    //        "shelfNumber":"645364536453643",                  商品货号
    //        "brandId":"1350308066834309122",                  品牌id
    //        "productTypeId":"1350307196952436738",            商品类型id
    //        "stockCount":580,                                 库存
    //        "limitShoppingCount":2,                           限购数
    //        "salesCount":70,                                  销量
    //        "deployTime":"2021-01-19 00:00:00",               发布时间
    //        "createTime":null,                                创建时间
    //        "productPrice":34.0,                              商品价格
    //        "productKeyword":"wb",                            搜索关键词
    //        "productDesc":"kkb",                              商品描述
    //        "cityPrice":5.0,                                  商城价
    //        "marketPrice":2.0,                                市场价
    //        "alarmCount":1,                                   警戒数
    //        "categoryId":null,                                类别
    //        "isOpenSpecification":1,                          是否开启规格(0=未开启，1=开启)
    //        "carriageId":null,                                运费模板id
    //        "isOnShelf":1,                                    是否上架
    //        "isVirtualProduct":1,                             是否虚拟商品（0=不是，1=是虚拟商品）
    //        "isLongTimeValid":0,                              虚拟商品用，是否长期有效（0=长期有效，1=自定义日期）
    //        "startTime":"2021-01-19 00:00:00",                开始时间
    //        "endTime":"2021-01-19 00:00:00",                  结束时间
    //        "isSupportRetrunMoney":1,                         是否支持退款(0=支持退款，1=不支持退款)
    //        "isVerificationCodeRightNow":0,                   核销码生效时间(0=立即生效，1=付款完几小时生效，2=次日生效)
    //        "afterPaymentHoursCount":1,                       核销码付款几小时
    //        "useDes":"my",                                    使用须知
    //        "shopId":"1351004305464053762",                   所属店铺id
    //        "productScore":null,                              商品评分
    //        "deployType":2,                                   发布类型(0=普通，1=精选，2=活动，3=动态，4=上新,5=猜你喜欢)
    //        "appraiseCount":null,                             评价数
    //        "goodAppraiseRate":null,                          好评率
    //        "viewCount":null,                                 浏览量
    //        "productTag":null,                                产品标签
    //        "appraiseList":[                                  评价列表
    //        ],
    //        "mainImageUrls":[                                 商品主图
    //            "http://lbdsp.com/1611029086402VagaDG.jpg",
    //            "http://lbdsp.com/16110290835553QB9tq.jpg",
    //            "http://lbdsp.com/1611029081240o6qncg.jpg",
    //            "http://lbdsp.com/1611029078773aH9JKx.jpg",
    //            "http://lbdsp.com/1611029076326iSHUi5.jpg",
    //            "http://lbdsp.com/1611029073821xfKSPs.jpg"
    //        ],
    //        "isOwnOperate":null,                              是否自营
    //        "detailImageUrls":[                               商品详情图片
    //            "http://lbdsp.com/1611029069708urPQVu.jpg",
    //            "http://lbdsp.com/16110290548386uU4bm.jpg",
    //            "http://lbdsp.com/1611029052289OahCvK.jpg",
    //            "http://lbdsp.com/1611029047447M00iR2.jpg",
    //            "http://lbdsp.com/1611029041714AlS1Mp.jpg",
    //            "http://lbdsp.com/1611029038599kRZ4W2.jpg",
    //            "http://lbdsp.com/1611029035965q6Fs9o.jpg",
    //            "http://lbdsp.com/1611029033913G0Olfb.jpg",
    //            "http://lbdsp.com/1611029031828lMiXHr.jpg"
    //        ],
    //        "productTags":[                                   商品标签
    //            null,
    //            null,
    //            null,
    //            null,
    //            null,
    //            null,
    //            null,
    //            null,
    //            null,
    //            null
    //        ]
    //    },
    //    "state":200,
    //    "msg":"请求成功"
    //}

    private GoodsDetailBody body;

    public void setBody(GoodsDetailBody body) {
        this.body = body;
    }
    public GoodsDetailBody getBody() {
        return body;
    }

    public class GoodsDetailBody {

        private String id;
        private String productName;
        private String productUnite;
        private String shelfNumber;
        private String brandId;
        private String productTypeId;
        private int stockCount;
        private int limitShoppingCount;
        private int salesCount;
        private Date deployTime;
        private String createTime;
        private int productPrice;
        private String productKeyword;
        private String productDesc;
        private int cityPrice;
        private int marketPrice;
        private int alarmCount;
        private String categoryId;
        private int isOpenSpecification;
        private String carriageId;
        private int isOnShelf;
        private int isVirtualProduct;
        private int isLongTimeValid;
        private Date startTime;
        private Date endTime;
        private int isSupportRetrunMoney;
        private int isVerificationCodeRightNow;
        private int afterPaymentHoursCount;
        private String useDes;
        private String shopId;
        private String productScore;
        private int deployType;
        private String appraiseCount;
        private String goodAppraiseRate;
        private String viewCount;
        private String productTag;
        private List<String> appraiseList;
        private List<String> mainImageUrls;
        private String isOwnOperate;
        private List<String> detailImageUrls;
        private List<String> productTags;

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
        public String getProductName() {
            return productName;
        }

        public void setProductUnite(String productUnite) {
            this.productUnite = productUnite;
        }
        public String getProductUnite() {
            return productUnite;
        }

        public void setShelfNumber(String shelfNumber) {
            this.shelfNumber = shelfNumber;
        }
        public String getShelfNumber() {
            return shelfNumber;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }
        public String getBrandId() {
            return brandId;
        }

        public void setProductTypeId(String productTypeId) {
            this.productTypeId = productTypeId;
        }
        public String getProductTypeId() {
            return productTypeId;
        }

        public void setStockCount(int stockCount) {
            this.stockCount = stockCount;
        }
        public int getStockCount() {
            return stockCount;
        }

        public void setLimitShoppingCount(int limitShoppingCount) {
            this.limitShoppingCount = limitShoppingCount;
        }
        public int getLimitShoppingCount() {
            return limitShoppingCount;
        }

        public void setSalesCount(int salesCount) {
            this.salesCount = salesCount;
        }
        public int getSalesCount() {
            return salesCount;
        }

        public void setDeployTime(Date deployTime) {
            this.deployTime = deployTime;
        }
        public Date getDeployTime() {
            return deployTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
        public String getCreateTime() {
            return createTime;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }
        public int getProductPrice() {
            return productPrice;
        }

        public void setProductKeyword(String productKeyword) {
            this.productKeyword = productKeyword;
        }
        public String getProductKeyword() {
            return productKeyword;
        }

        public void setProductDesc(String productDesc) {
            this.productDesc = productDesc;
        }
        public String getProductDesc() {
            return productDesc;
        }

        public void setCityPrice(int cityPrice) {
            this.cityPrice = cityPrice;
        }
        public int getCityPrice() {
            return cityPrice;
        }

        public void setMarketPrice(int marketPrice) {
            this.marketPrice = marketPrice;
        }
        public int getMarketPrice() {
            return marketPrice;
        }

        public void setAlarmCount(int alarmCount) {
            this.alarmCount = alarmCount;
        }
        public int getAlarmCount() {
            return alarmCount;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }
        public String getCategoryId() {
            return categoryId;
        }

        public void setIsOpenSpecification(int isOpenSpecification) {
            this.isOpenSpecification = isOpenSpecification;
        }
        public int getIsOpenSpecification() {
            return isOpenSpecification;
        }

        public void setCarriageId(String carriageId) {
            this.carriageId = carriageId;
        }
        public String getCarriageId() {
            return carriageId;
        }

        public void setIsOnShelf(int isOnShelf) {
            this.isOnShelf = isOnShelf;
        }
        public int getIsOnShelf() {
            return isOnShelf;
        }

        public void setIsVirtualProduct(int isVirtualProduct) {
            this.isVirtualProduct = isVirtualProduct;
        }
        public int getIsVirtualProduct() {
            return isVirtualProduct;
        }

        public void setIsLongTimeValid(int isLongTimeValid) {
            this.isLongTimeValid = isLongTimeValid;
        }
        public int getIsLongTimeValid() {
            return isLongTimeValid;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }
        public Date getStartTime() {
            return startTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }
        public Date getEndTime() {
            return endTime;
        }

        public void setIsSupportRetrunMoney(int isSupportRetrunMoney) {
            this.isSupportRetrunMoney = isSupportRetrunMoney;
        }
        public int getIsSupportRetrunMoney() {
            return isSupportRetrunMoney;
        }

        public void setIsVerificationCodeRightNow(int isVerificationCodeRightNow) {
            this.isVerificationCodeRightNow = isVerificationCodeRightNow;
        }
        public int getIsVerificationCodeRightNow() {
            return isVerificationCodeRightNow;
        }

        public void setAfterPaymentHoursCount(int afterPaymentHoursCount) {
            this.afterPaymentHoursCount = afterPaymentHoursCount;
        }
        public int getAfterPaymentHoursCount() {
            return afterPaymentHoursCount;
        }

        public void setUseDes(String useDes) {
            this.useDes = useDes;
        }
        public String getUseDes() {
            return useDes;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }
        public String getShopId() {
            return shopId;
        }

        public void setProductScore(String productScore) {
            this.productScore = productScore;
        }
        public String getProductScore() {
            return productScore;
        }

        public void setDeployType(int deployType) {
            this.deployType = deployType;
        }
        public int getDeployType() {
            return deployType;
        }

        public void setAppraiseCount(String appraiseCount) {
            this.appraiseCount = appraiseCount;
        }
        public String getAppraiseCount() {
            return appraiseCount;
        }

        public void setGoodAppraiseRate(String goodAppraiseRate) {
            this.goodAppraiseRate = goodAppraiseRate;
        }
        public String getGoodAppraiseRate() {
            return goodAppraiseRate;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }
        public String getViewCount() {
            return viewCount;
        }

        public void setProductTag(String productTag) {
            this.productTag = productTag;
        }
        public String getProductTag() {
            return productTag;
        }

        public void setAppraiseList(List<String> appraiseList) {
            this.appraiseList = appraiseList;
        }
        public List<String> getAppraiseList() {
            return appraiseList;
        }

        public void setMainImageUrls(List<String> mainImageUrls) {
            this.mainImageUrls = mainImageUrls;
        }
        public List<String> getMainImageUrls() {
            return mainImageUrls;
        }

        public void setIsOwnOperate(String isOwnOperate) {
            this.isOwnOperate = isOwnOperate;
        }
        public String getIsOwnOperate() {
            return isOwnOperate;
        }

        public void setDetailImageUrls(List<String> detailImageUrls) {
            this.detailImageUrls = detailImageUrls;
        }
        public List<String> getDetailImageUrls() {
            return detailImageUrls;
        }

        public void setProductTags(List<String> productTags) {
            this.productTags = productTags;
        }
        public List<String> getProductTags() {
            return productTags;
        }

    }
}
