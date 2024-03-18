package com.yangna.lbdsp.home.model;

import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.home.bean.FirstLevelBean;

import java.util.ArrayList;

public class GiveCommentsModel extends BaseModel {
    private ConmmentsList body;

    public ConmmentsList getBody() {
        return body;
    }

    public void setBody(ConmmentsList body) {
        this.body = body;
    }

    public static class ConmmentsList {
        private int currentPage;
        private int maxResults;
        private int totalPages;//总页数
        private int totalRecords;//总数量
        private ArrayList<FirstLevelBean> records;

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

        public ArrayList<FirstLevelBean> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<FirstLevelBean> records) {
            this.records = records;
        }
    }

    public class CommentS {
        private int id;
        private int videoId;
        private int accountId;
        private AccountEntitysBean accountEntity;
        private String contents;
        private String createTime;
        private int parentId;
        private String viewAuth;
        private int childCount;

        public AccountEntitysBean
         getAccountEntity() {
            return accountEntity;
        }

        public void setAccountEntity(AccountEntitysBean accountEntity) {
            this.accountEntity = accountEntity;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }


        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getViewAuth() {
            return viewAuth;
        }

        public void setViewAuth(String viewAuth) {
            this.viewAuth = viewAuth;
        }

        public int getChildCount() {
            return childCount;
        }

        public void setChildCount(int childCount) {
            this.childCount = childCount;
        }
    }

    public class AccountEntitysBean {
        private String nickName;
        private String logo;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }


    }
}
