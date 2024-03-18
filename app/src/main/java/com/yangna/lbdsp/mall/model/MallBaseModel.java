package com.yangna.lbdsp.mall.model;

import com.yangna.lbdsp.common.base.BaseModel;

import java.util.List;

public class MallBaseModel extends BaseModel {

//    private String mall_icon;//店铺头像
//    private String mall_lingdai;//企业认证
//    private String mall_guanzhu;//关注
//    private String mall_xingji;//星级
//    private String mall_name;//店名
//    private String mall_ppld;//品牌老店

    private int currentPage;
    private int maxResults;
    private int totalPages;
    private int totalRecords;
    private List<MallModel> records;

    // 门店列表 /account/shop/getShopByAddress 接口返回值
    //{
    //	"currentPage": 0,
    //	"maxResults": 10,
    //	"totalPages": 0,
    //	"totalRecords": 0,
    //	"records": [{
    //		"id": 1,
    //		"accountId": 1,
    //		"shopName": "wandayingcheng",                               门店名称
    //		"address": "liuzhou",                                       门店所在区域
    //		"mapAddress": "https://csdnimg.cn/images/csdnqr.png",       门店头像图片url
    //      "shopLogoUrl": "https://bkimg.cdn.bcebos.com/pic/c2f.jpg",  门店头像图片url
    //		"enterpriseCertification": "1",                             企业认证，0未通过，1已通过
    //		"mallLevel": 1,                                             门店等级，1-5级
    //		"focus": 0,                                                 当前用户是否已关注该门店，0未关注，1已关注
    //		"firstFigure1": "https://csdnimg.cn/images/st1.png",        门店首图1，有就返回url，没有就返回 “” 空
    //		"firstFigure2": "https://csdnimg.cn/images/st2.png",        门店首图2，有就返回url，没有就返回 “” 空
    //		"firstFigure3": "",                                         门店首图3，有就返回url，没有就返回 “” 空
    //		"firstFigure4": "",                                         门店首图4，有就返回url，没有就返回 “” 空
    //		"status": "1",
    //		"createTime": 20201009
    //	}]
    //}

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

    public List<MallModel> getRecords() {
        return records;
    }

    public void setRecords(List<MallModel> records) {
        this.records = records;
    }

}
