package com.yangna.lbdsp.mall.model;

import java.util.List;

public class GoodsParticularsBaseModel {

    private String currentPage;
    private String maxResults;
    private String totalPages;
    private String totalRecords;
    private List<GoodsParticularsModel> records;

    // 商品详情 /account/good/getGoodsDetailByGoodsId 接口返回值 本来不需要model值的，只是为了重复写一个值而已，反正是临时用，2020年11月7日15:05:04
    //{
    //  "currentPage": 0,
    //  "maxResults": 10,
    //  "totalPages": 0,
    //  "totalRecords": 0,
    //  "records": [
    //    {
    //      "id": 14,           传回来的没什么用的id
    //      "goodsId": 14,      传回来的单个商品id
    //      "des": "ykyjgjgj"   详情
    //    }
    //  ]
    //}

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(String maxResults) {
        this.maxResults = maxResults;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<GoodsParticularsModel> getRecords() {
        return records;
    }

    public void setRecords(List<GoodsParticularsModel> records) {
        this.records = records;
    }

}
