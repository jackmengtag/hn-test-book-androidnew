package com.hn.book.model;

import com.hn.book.bean.HnBook;
import com.yangna.lbdsp.common.base.BaseModel;
import java.util.ArrayList;


public class BookListModel extends BaseModel {

    private BookModel body;

    public BookModel getBody() {
        return body;
    }

    public void setBody(BookModel body) {
        this.body = body;
    }

    public class  BookModel {
        private int currentPage;
        private int maxResults;
        private int totalPages;//总页数
        private int totalRecords;//总数量
        private ArrayList<HnBook> records;

        public ArrayList<HnBook> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<HnBook> records) {
            this.records = records;
        }

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

    }



}
