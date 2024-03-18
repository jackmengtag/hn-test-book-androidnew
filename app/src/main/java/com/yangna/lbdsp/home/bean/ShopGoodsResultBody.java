package com.yangna.lbdsp.home.bean;

import com.yangna.lbdsp.common.base.BaseModel;

import java.util.List;

public class ShopGoodsResultBody {

    private Integer currentPage;

    private Integer maxResults;

    private Integer totalPages;

    private Integer totalRecords;

    private List<TGoods> records;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<TGoods> getRecords() {
        return records;
    }

    public void setRecords(List<TGoods> records) {
        this.records = records;
    }
}
