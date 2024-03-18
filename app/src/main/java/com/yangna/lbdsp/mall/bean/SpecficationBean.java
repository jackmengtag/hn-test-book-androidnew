package com.yangna.lbdsp.mall.bean;

public class SpecficationBean {

    private String id;
    private String ruleName;
    private String createTime;
    private String productId;
    private String typeId;
    private String productPrice;

    /* 2021年1月23日22:37:15 新版商品详情接口 /api/tWebshopProduct/getGoodsDetailByGoodsId */
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
    //		"productTag": null,
    //		"appraiseList": [],
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPrice() {
        return productPrice;
    }

}
