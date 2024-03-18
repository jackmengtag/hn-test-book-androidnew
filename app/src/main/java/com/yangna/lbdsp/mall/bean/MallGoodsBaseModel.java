package com.yangna.lbdsp.mall.bean;

import com.yangna.lbdsp.common.utils.BaseModel;

public class MallGoodsBaseModel extends BaseModel {
//不要乱用什么单词，后台返回什么，就用什么！！！！     records      ！！！！！
    // {
    //	"body": {
    //		"currentPage": 1,
    //		"maxResults": 20,
    //		"totalPages": 5,
    //		"totalRecords": 20,
    //		"records": [{
    //			"id": 1342397081007190018,
    //			"shopId": 68,
    //			"cateId": 0,
    //			"goodsName": "充电电池",
    //			"goodsPrice": 36.00,
    //			"goodsAmount": 52,
    //			"coverVideoId": null,
    //			"coverVideoUrl": null,
    //			"status": "1",
    //			"pictureUrl": "http://lbdsp.com/1609126220417YiiiMh.jpg"
    //		}]
    //	},
    //	"state": 200,
    //	"msg": "请求成功"
    //}
    /* 门店内商品列表 /account/good/getGoodsByShopId 接口返回值 */



    private Body body;
    private int state;
    private String msg;

    public void setBody(Body body) {
        this.body = body;
    }
    public Body getBody() {
        return body;
    }

    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
}
