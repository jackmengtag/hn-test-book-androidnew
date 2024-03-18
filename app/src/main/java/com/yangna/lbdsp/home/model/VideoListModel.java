package com.yangna.lbdsp.home.model;

import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.videoCom.VideoBean;

import java.util.ArrayList;


public class VideoListModel extends BaseModel {
    private Information body;

    public Information getBody() {
        return body;
    }

    public void setBody(Information body) {
        this.body = body;
    }

    public class Information {
        private int currentPage;
        private int maxResults;
        private int totalPages;//视频总页数
        private int totalRecords;//视频总数量
        private ArrayList<VideoBean> records;

        public ArrayList<VideoBean> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<VideoBean> records) {
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

    public class VideoMsg{
    private int id;
    private int VideoMsg;
    private String originalVideoId;
    private int videoScore;
    private long createTime;
    private String title;
    private String description;
    private String coverUrl;
    private String tag;
    private String status;
    private String area;
    private String length;
    private String fileName;
    private String fileSize;
    private String upload_address;

        public String getUpload_address() {
            return upload_address;
        }

        public void setUpload_address(String upload_address) {
            this.upload_address = upload_address;
        }

        private String viewAuth;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getVideoMsg() {
            return VideoMsg;
        }

        public void setVideoMsg(int videoMsg) {
            VideoMsg = videoMsg;
        }

        public String getOriginalVideoId() {
            return originalVideoId;
        }

        public void setOriginalVideoId(String originalVideoId) {
            this.originalVideoId = originalVideoId;
        }

        public int getVideoScore() {
            return videoScore;
        }

        public void setVideoScore(int videoScore) {
            this.videoScore = videoScore;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }



        public String getViewAuth() {
            return viewAuth;
        }

        public void setViewAuth(String viewAuth) {
            this.viewAuth = viewAuth;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String type;

    }
}
