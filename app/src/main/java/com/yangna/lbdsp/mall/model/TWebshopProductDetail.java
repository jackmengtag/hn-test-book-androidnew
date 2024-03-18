package com.yangna.lbdsp.mall.model;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 商品(TWebshopProduct)表实体类--详情
 *
 * @author makejava
 * @since 2020-12-15 15:54:04
 */

public class TWebshopProductDetail implements Serializable {

    private String id;
    /**
     * 商品名称
     */

    private String productName;
    /**
     * 商品单位
     */

    private String productUnite;
    /**
     * 商品货号
     */
    private String shelfNumber;
    /**
     * 品牌id
     */

    private String brandId;
    /**
     * 商品类型id
     */

    private String productTypeId;
    /**
     * 库存
     */

    private Integer stockCount;
    /**
     * 限购数
     */

    private Integer limitShoppingCount;
    /**
     * 销量
     */

    private Integer salesCount;
    /**
     * 发布时间
     */

    private String deployTime;
    /**
     * 创建时间
     */

    private String createTime;
    /**
     * 商品价格
     */

    private Double productPrice;
    /**
     * 搜索关键词
     */

    private String productKeyword;
    /**
     * 商品描述
     */

    private String productDesc;
    /**
     * 商城价
     */

    private Double cityPrice;
    /**
     * 市场价
     */

    private Double marketPrice;
    /**
     * 警戒数
     */

    private Integer alarmCount;
    /**
     * 商家分类id
     */

    private String categoryId;
    /**
     * 是否开启规格(0=未开启，1=开启)
     */

    private Integer isOpenSpecification;
    /**
     * 运费模板id
     */

    private String carriageId;
    /**
     * 是否上架
     */

    private Integer isOnShelf;
    /**
     * 是否虚拟商品（0=不是，1=是虚拟商品）
     */

    private Integer isVirtualProduct;
    /**
     * 虚拟商品用，是否长期有效（0=长期有效，1=自定义日期）
     */

    private Integer isLongTimeValid;
    /**
     * 开始时间
     */

    private String startTime;
    /**
     * 结束时间
     */

    private String endTime;
    /**
     * 是否支持退款(0=支持退款，1=不支持退款)
     */

    private Integer isSupportRetrunMoney;
    /**
     * 核销码生效时间(0=立即生效，1=付款完几小时生效，2=次日生效)
     */

    private Long isVerificationCodeRightNow;
    /**
     * 核销码付款几小时
     */

    private Integer afterPaymentHoursCount;
    /**
     * 使用须知
     */

    private String useDes;
    /**
     * 所属店铺id
     */

    private String shopId;
    /**
     * 商品评分
     */

    private Integer productScore;
    /**
     * 发布类型(0=普通，1=精选，2=活动，3=动态，4=上新,5=猜你喜欢)
     */

    private Integer deployType;
    /**
     * 评论数
     */

    private Integer appraiseCount;
    /**
     * 好评率
     */

    private Double goodAppraiseRate;
    /**
     * 浏览量
     */

    private Integer viewCount;

    //商品标签
    private String productTag;

    /**
     * 评论
     */

    private List<TWebshopOrderComment> appraiseList=new ArrayList<>();

    /**
     * 浏览量
     */

    private List<String> mainImageUrls=new ArrayList<>();

    /**
     * 是否自营
     */

    private Integer isOwnOperate ;//是否自营

    /**
     * 商品详情图片
     */

    private List<String> detailImageUrls=new ArrayList<>();

    /**
     * 商品标签
     */

    private String[] productTags=new String[10];

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

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getLimitShoppingCount() {
        return limitShoppingCount;
    }

    public void setLimitShoppingCount(Integer limitShoppingCount) {
        this.limitShoppingCount = limitShoppingCount;
    }

    public Integer getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(Integer salesCount) {
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
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

    public Double getCityPrice() {
        return cityPrice;
    }

    public void setCityPrice(Double cityPrice) {
        this.cityPrice = cityPrice;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(Integer alarmCount) {
        this.alarmCount = alarmCount;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getIsOpenSpecification() {
        return isOpenSpecification;
    }

    public void setIsOpenSpecification(Integer isOpenSpecification) {
        this.isOpenSpecification = isOpenSpecification;
    }

    public String getCarriageId() {
        return carriageId;
    }

    public void setCarriageId(String carriageId) {
        this.carriageId = carriageId;
    }

    public Integer getIsOnShelf() {
        return isOnShelf;
    }

    public void setIsOnShelf(Integer isOnShelf) {
        this.isOnShelf = isOnShelf;
    }

    public Integer getIsVirtualProduct() {
        return isVirtualProduct;
    }

    public void setIsVirtualProduct(Integer isVirtualProduct) {
        this.isVirtualProduct = isVirtualProduct;
    }

    public Integer getIsLongTimeValid() {
        return isLongTimeValid;
    }

    public void setIsLongTimeValid(Integer isLongTimeValid) {
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

    public Integer getIsSupportRetrunMoney() {
        return isSupportRetrunMoney;
    }

    public void setIsSupportRetrunMoney(Integer isSupportRetrunMoney) {
        this.isSupportRetrunMoney = isSupportRetrunMoney;
    }

    public Long getIsVerificationCodeRightNow() {
        return isVerificationCodeRightNow;
    }

    public void setIsVerificationCodeRightNow(Long isVerificationCodeRightNow) {
        this.isVerificationCodeRightNow = isVerificationCodeRightNow;
    }

    public Integer getAfterPaymentHoursCount() {
        return afterPaymentHoursCount;
    }

    public void setAfterPaymentHoursCount(Integer afterPaymentHoursCount) {
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

    public Integer getProductScore() {
        return productScore;
    }

    public void setProductScore(Integer productScore) {
        this.productScore = productScore;
    }

    public Integer getDeployType() {
        return deployType;
    }

    public void setDeployType(Integer deployType) {
        this.deployType = deployType;
    }

    public Integer getAppraiseCount() {
        return appraiseCount;
    }

    public void setAppraiseCount(Integer appraiseCount) {
        this.appraiseCount = appraiseCount;
    }

    public Double getGoodAppraiseRate() {
        return goodAppraiseRate;
    }

    public void setGoodAppraiseRate(Double goodAppraiseRate) {
        this.goodAppraiseRate = goodAppraiseRate;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getProductTag() {
        return productTag;
    }

    public void setProductTag(String productTag) {
        this.productTag = productTag;
    }

    public List<TWebshopOrderComment> getAppraiseList() {
        return appraiseList;
    }

    public void setAppraiseList(List<TWebshopOrderComment> appraiseList) {
        this.appraiseList = appraiseList;
    }

    public List<String> getMainImageUrls() {
        return mainImageUrls;
    }

    public void setMainImageUrls(List<String> mainImageUrls) {
        this.mainImageUrls = mainImageUrls;
    }

    public Integer getIsOwnOperate() {
        return isOwnOperate;
    }

    public void setIsOwnOperate(Integer isOwnOperate) {
        this.isOwnOperate = isOwnOperate;
    }

    public List<String> getDetailImageUrls() {
        return detailImageUrls;
    }

    public void setDetailImageUrls(List<String> detailImageUrls) {
        this.detailImageUrls = detailImageUrls;
    }

    public String[] getProductTags() {
        return productTags;
    }

    public void setProductTags(String[] productTags) {
        this.productTags = productTags;
    }

    public List<TWebshopProductSpceficationPage> getSpecficationList() {
        return specficationList;
    }

    public void setSpecficationList(List<TWebshopProductSpceficationPage> specficationList) {
        this.specficationList = specficationList;
    }
}