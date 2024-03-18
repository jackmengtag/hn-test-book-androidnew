package com.yangna.lbdsp.videoCom;

import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidSts;
import com.yangna.lbdsp.videoCom.AliPlay.IVideoSourceModel;
import com.yangna.lbdsp.videoCom.AliPlay.VideoSourceType;

import java.io.Serializable;

/**
 * create by libo
 * create on 2020-06-03
 * description 视频实体类
 */
public class VideoBean implements IVideoSourceModel, Serializable {
    /**
     * 视频播放id
     */
    private long id;
    /**
     * 视频播放资源
     */
    private int videoRes;
    /**
     * 封面图片资源
     */
    private String coverUrl;
    /**
     * 视频文案内容
     */
    private String description;
    /**
     * 作者
     */
    private UserBean userBean;
    /**
     * 是否已点赞
     */
    private boolean hasPraise;
    /**
     * 发布城市
     */
    private String area;
    /**
     * 与视频发布距离
     */
    private float distance;
    /**
     * 是否已关注
     */
    private boolean isFocused;
    /**
     * 点赞数
     */
    private String praiseAmount;
    /**
     * 评论数
     */
    private String commentAmount;
    /**
     * 转发数
     */
    private int shareCount;
    /**
     * 视频地址
     */
    private String uploadAddress;
    /**
     * 发布人称
     */
    private String nickName;
    /**
     * 发布人头像
     */
    private String logo;
    /**
     * 发布人等级
     */
    private String identityAuth;
    /**
     * 发布人商户ID
     */
    private Long shopId;
    /**
     * 发布人商户图片
     */
    private String shopLogoUrl;
    /**
     * 发布人商户名字
     */
    private String shopName;

    /**
     * 视频uuid
     */
    private String originalVideoId;

    /**
     * 用户id
     */
    private String accountId;

    public String getOriginalVideoId() {
        return originalVideoId;
    }

    public void setOriginalVideoId(String originalVideoId) {
        this.originalVideoId = originalVideoId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getUploadAddress() {
        return uploadAddress;
    }

    public void setUploadAddress(String uploadAddress) {
        this.uploadAddress = uploadAddress;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getShopLogoUrl() {
        return shopLogoUrl;
    }

    public void setShopLogoUrl(String shopLogoUrl) {
        this.shopLogoUrl = shopLogoUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(String identityAuth) {
        this.identityAuth = identityAuth;
    }


    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVideoRes() {
        return videoRes;
    }

    public void setVideoRes(int videoRes) {
        this.videoRes = videoRes;
    }


    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public boolean isHasPraise() {
        return hasPraise;
    }

    public void setHasPraise(boolean hasPraise) {
        this.hasPraise = hasPraise;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public String getPraiseAmount() {
        return praiseAmount;
    }

    public void setPraiseAmount(String praiseAmount) {
        this.praiseAmount = praiseAmount;
    }

    public String getCommentAmount() {
        return commentAmount;
    }

    public void setCommentAmount(String commentAmount) {
        this.commentAmount = commentAmount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 选用url播放
     */
    @Override
    public VideoSourceType getSourceType() {
        return VideoSourceType.TYPE_URL;
    }

    /**
     * 获取视频的唯一标示
     *
     * @return
     */
    @Override
    public String getUUID() {
        return id + "";
    }

    /**
     * 获取视频url相关信息，用于播放该资源，使用URL播放
     *
     * @return
     */
    @Override
    public UrlSource getUrlSource() {
        UrlSource urlSource = new UrlSource();
        urlSource.setUri(uploadAddress);
        return urlSource;
    }

    @Override
    public VidSts getVidStsSource() {
        return null;
    }

    /**
     * 获取首帧路径
     *
     * @return
     */
    @Override
    public String getFirstFrame() {
        return coverUrl;
    }


    public static class UserBean {
        private int uid;
        private String nick_name;
        private int head;
        /**
         * 座右铭
         */
        private String sign;
        /**
         * 是否已关注
         */
        private boolean isFocused;
        /**
         * 获赞数量
         */
        private int subCount;
        /**
         * 关注数量
         */
        private int focusCount;
        /**
         * 粉丝数量
         */
        private int fansCount;
        /**
         * 作品数量
         */
        private int workCount;
        /**
         * 动态数量
         */
        private int dynamicCount;
        /**
         * 喜欢数量
         */
        private int likeCount;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getNickName() {
            return nick_name == null ? "" : nick_name;
        }

        public void setNickName(String nickName) {
            this.nick_name = nick_name;
        }

        public int getHead() {
            return head;
        }

        public void setHead(int head) {
            this.head = head;
        }

        public String getSign() {
            return sign == null ? "" : sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public boolean isFocused() {
            return isFocused;
        }

        public void setFocused(boolean focused) {
            isFocused = focused;
        }

        public int getSubCount() {
            return subCount;
        }

        public void setSubCount(int subCount) {
            this.subCount = subCount;
        }

        public int getFocusCount() {
            return focusCount;
        }

        public void setFocusCount(int focusCount) {
            this.focusCount = focusCount;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public int getWorkCount() {
            return workCount;
        }

        public void setWorkCount(int workCount) {
            this.workCount = workCount;
        }

        public int getDynamicCount() {
            return dynamicCount;
        }

        public void setDynamicCount(int dynamicCount) {
            this.dynamicCount = dynamicCount;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }
    }
}
