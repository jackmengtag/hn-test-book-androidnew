package com.yangna.lbdsp.home.model;

import com.yangna.lbdsp.common.base.BaseModel;

public class VideoDetailModel extends BaseModel {

    /**
     * body : {"praiseTotal":0,"commentTotal":0,"relayTotal":0,"viewsTotal":0,"hasPraise":false}
     */

    public BodyBean body;

    public static class BodyBean {
        /**
         * praiseTotal : 0
         * commentTotal : 0
         * relayTotal : 0
         * viewsTotal : 0
         * hasPraise : false
         */

        public int praiseTotal;

        public int getPraiseTotal() {
            return praiseTotal;
        }

        public void setPraiseTotal(int praiseTotal) {
            this.praiseTotal = praiseTotal;
        }

        public int getCommentTotal() {
            return commentTotal;
        }

        public void setCommentTotal(int commentTotal) {
            this.commentTotal = commentTotal;
        }

        public int getRelayTotal() {
            return relayTotal;
        }

        public void setRelayTotal(int relayTotal) {
            this.relayTotal = relayTotal;
        }

        public int getViewsTotal() {
            return viewsTotal;
        }

        public void setViewsTotal(int viewsTotal) {
            this.viewsTotal = viewsTotal;
        }

        public boolean isHasPraise() {
            return hasPraise;
        }

        public void setHasPraise(boolean hasPraise) {
            this.hasPraise = hasPraise;
        }

        public int commentTotal;
        public int relayTotal;
        public int viewsTotal;
        public boolean hasPraise;
    }
}
