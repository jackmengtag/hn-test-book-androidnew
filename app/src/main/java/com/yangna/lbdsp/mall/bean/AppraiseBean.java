package com.yangna.lbdsp.mall.bean;

public class AppraiseBean {

    private String id;
    private String orderSn;
    private String shoppingUser;
    private String productId;
    private String createTime;
    private String deleted;
    private String content;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String isResponse;
    private String status;
    private String parentId;

    /* 2021年2月3日14:44:23 新版商品详情接口 /api/tWebshopProduct/getGoodsDetailByGoodsId */
    //{
    //	"body": {
    //		"id": "13e1e94055cfb64a2128ed56083feb0d92ac",
    //		"productName": "万达冰淇淋",
    //		"productUnite": "个",
    //		"shelfNumber": "9527",
    //		"brandId": "1350308066834309122",
    //		"productTypeId": "1350306400957423618",
    //		"stockCount": 9527,
    //		"limitShoppingCount": 9527,
    //		"salesCount": 9527,
    //		"deployTime": "2021-01-01 00:00:00",
    //		"createTime": null,
    //		"productPrice": 9527.0,
    //		"productKeyword": "9527",
    //		"productDesc": "9527",
    //		"cityPrice": 9527.0,
    //		"marketPrice": 9527.0,
    //		"alarmCount": 9527,
    //		"categoryId": null,
    //		"isOpenSpecification": 1,
    //		"carriageId": null,
    //		"isOnShelf": 1,
    //		"isVirtualProduct": 1,
    //		"isLongTimeValid": 0,
    //		"startTime": "2021-01-01 00:00:00",
    //		"endTime": "2021-01-02 00:00:00",
    //		"isSupportRetrunMoney": 0,
    //		"isVerificationCodeRightNow": 0,
    //		"afterPaymentHoursCount": 11,
    //		"useDes": "11",
    //		"shopId": "1350303633785524226",
    //		"productScore": null,
    //		"deployType": 0,
    //		"appraiseCount": null,
    //		"goodAppraiseRate": null,
    //		"viewCount": null,
    //		"productTag": "好吃、便宜、受欢迎",
    //		"appraiseList": [{                                              订单评论表
    //			"id": "1",
    //			"orderSn": "1",                                             订单号
    //			"shoppingUser": "1",                                        买家
    //			"productId": "18f68311ab40f04eb438915b642dfdc6a242",    	商品id
    //			"createTime": null,                                         创建时间
    //			"deleted": 0,                                               评论状态
    //			"content": "6666",                                          内容
    //			"imageUrl1": null,                                          图片1
    //			"imageUrl2": null,                                          图片2
    //			"imageUrl3": null,                                          图片3
    //			"isResponse": 0,                                            是否是商家回复（0=消费者，1=商家回复）
    //			"status": 0,                                                状态(0=未回复，1=已回复)
    //			"parentId": null                                            父类评论id
    //		}, {
    //			"id": "2",
    //			"orderSn": "1",
    //			"shoppingUser": "1",
    //			"productId": "18f68311ab40f04eb438915b642dfdc6a242",
    //			"createTime": null,
    //			"deleted": 0,
    //			"content": "物美价廉",
    //			"imageUrl1": null,
    //			"imageUrl2": null,
    //			"imageUrl3": null,
    //			"isResponse": 0,
    //			"status": 0,
    //			"parentId": null
    //		}],
    //		"mainImageUrls": ["http://lbdsp.com/1610777915205n0Ywey.png", "http://lbdsp.com/1610777918746oRuDDO.png", "http://lbdsp.com/1610777922410sucBt6.png", "http://lbdsp.com/1610777930338de26n9.JPG", "http://lbdsp.com/1610777932680yn22XE.JPG", "http://lbdsp.com/16107779350384j9GTh.JPG"],
    //		"isOwnOperate": null,
    //		"detailImageUrls": ["http://lbdsp.com/1610777938246Rcucrv.jpg", "http://lbdsp.com/1610777942624qyvpTs.png", "http://lbdsp.com/1610777945433L2h2C6.JPG", "http://lbdsp.com/1610777949727NaeFnQ.png", "http://lbdsp.com/1610777953279XsucfE.JPG", "http://lbdsp.com/1610777957946cfS3vO.jpg", "http://lbdsp.com/1610777960515wErajw.JPG", "http://lbdsp.com/1610777962856V5Eqw3.JPG", "http://lbdsp.com/1610777965640d3CdFZ.jpg"],
    //		"productTags": [null, null, null, null, null, null, null, null, null, null],
    //		"specficationList": [{
    //			"id": "1",
    //			"ruleName": "黄色",
    //			"createTime": null,
    //			"productId": "13e1e94055cfb64a2128ed56083feb0d92ac",
    //			"typeId": "1",
    //			"productPrice": 10.0
    //		}, {
    //			"id": "1352901497267249154",
    //			"ruleName": "白色",
    //			"createTime": null,
    //			"productId": "13e1e94055cfb64a2128ed56083feb0d92ac",
    //			"typeId": "1",
    //			"productPrice": 9.9
    //		}, {
    //			"id": "2",
    //			"ruleName": "蓝色",
    //			"createTime": null,
    //			"productId": "13e1e94055cfb64a2128ed56083feb0d92ac",
    //			"typeId": "1",
    //			"productPrice": 12.0
    //		}, {
    //			"id": "3",
    //			"ruleName": "黄色",
    //			"createTime": null,
    //			"productId": "13e1e94055cfb64a2128ed56083feb0d92ac",
    //			"typeId": "1",
    //			"productPrice": 15.0
    //		}]
    //	},
    //	"state": 200,
    //	"msg": "请求成功"
    //}

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

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
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

    public String getIsResponse() {
        return isResponse;
    }

    public void setIsResponse(String isResponse) {
        this.isResponse = isResponse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
