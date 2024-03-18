package com.yangna.lbdsp.mall.bean;

import java.util.List;

public class CommonBody<T> {

    private int currentPage;
    private int maxResults;
    private int totalPages;
    private int totalRecords;
    private List<T> records;

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
    public int getMaxResults() {
        return maxResults;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
    public int getTotalRecords() {
        return totalRecords;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
    public List<T> getRecords() {
        return records;
    }
}
