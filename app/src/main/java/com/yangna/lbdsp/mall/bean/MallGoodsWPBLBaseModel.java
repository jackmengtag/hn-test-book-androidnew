package com.yangna.lbdsp.mall.bean;

import com.yangna.lbdsp.common.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class MallGoodsWPBLBaseModel extends BaseModel {
    /* 2021年1月15日13:04:39 新版商品列表返回值 /api/tWebshopProduct 接口 */
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

    private CommonBody<MallGoodsWPBLRecords> body;

    public CommonBody<MallGoodsWPBLRecords> getBody() {
        return body;
    }

    public void setBody(CommonBody<MallGoodsWPBLRecords> body) {
        this.body = body;
    }

}
