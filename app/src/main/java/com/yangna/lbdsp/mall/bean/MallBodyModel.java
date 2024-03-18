package com.yangna.lbdsp.mall.bean;

import java.util.List;

public class MallBodyModel {
    private int currentPage;
    private int maxResults;
    private int totalPages;
    private int totalRecords;
    private List<MallRecordsModel> records;

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

    public List<MallRecordsModel> getRecords() {
        return records;
    }

    public void setRecords(List<MallRecordsModel> records) {
        this.records = records;
    }
}
