package com.yangna.lbdsp.login.model;

import com.yangna.lbdsp.common.base.BaseModel;

public class UserInfoModel extends BaseModel {

    //获取用户信息
    //返回值 2020年11月11日13:39:18
    //{
    //	"body": {
    //		"uuid": "1b63c671-de28-4ef8-90e6-32bf24d3df5e",
    //		"account": {
    //			"id": 8,                                    自增id
    //			"accountName": "9826566431",                老表视频号，仅可修改一次
    //			"mobile": "18978065947",                    注册手机号
    //			"createTime": 1598413744678,
    //			"inviteCode": "rS4e02uW",                   邀请码
    //			"status": "0",                              0正常1冻结2删除
    //			"nickName": "dztz",                         昵称
    //			"logo": "/",                                头像
    //			"des": "666",                               描述
    //			"area": "zzz",                              所在地区
    //			"identityAuth": "S",                        身份认证 U普通用户 G官方用户 V认证用户 S商家用户 E政府机构
    //			"identityName": null,                       认证名称
    //			"promotionCode": "KTAWKEoC",                推广码
    //			"relationStatus": null,                     关系状态
    //			"userLevel": null                           用户等级
    //		},
    //		"loginTime": "2020-11-11 11:02:18",
    //		"expireTime": "2020-11-14 01:27:46",
    //		"ipaddr": "125.73.102.239",
    //		"loginLocation": "广西 柳州",
    //		"browser": "Unknown",
    //		"os": "Unknown",
    //		"platform": null
    //	},
    //	"state": 200,
    //	"msg": "请求成功"
    //}

    private UserInfoData body;

    public UserInfoData getBody() {
        return body;
    }

    public void setBody(UserInfoData body) {
        this.body = body;
    }

    public class UserInfoData{
        private AccountData account;
        private String browser;
        private String expireTime;
        private String ipaddr;
        private String loginLocation;

        public AccountData getAccount() {
            return account;
        }

        public void setAccount(AccountData account) {
            this.account = account;
        }

        public String getBrowser() {
            return browser;
        }

        public void setBrowser(String browser) {
            this.browser = browser;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public String getIpaddr() {
            return ipaddr;
        }

        public void setIpaddr(String ipaddr) {
            this.ipaddr = ipaddr;
        }

        public String getLoginLocation() {
            return loginLocation;
        }

        public void setLoginLocation(String loginLocation) {
            this.loginLocation = loginLocation;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        private String loginTime;
        private String os;
        private String platform;
        private String uuid;
        public class AccountData{
            //		"account": {
            //			"id": 8,                                    自增id
            //			"accountName": "9826566431",                老表视频号，仅可修改一次
            //			"mobile": "18978065947",                    注册手机号
            //			"createTime": 1598413744678,
            //			"inviteCode": "rS4e02uW",                   邀请码
            //			"status": "0",                              0正常1冻结2删除
            //			"nickName": "dztz",                         昵称
            //			"logo": "/",                                头像
            //			"des": "666",                               描述
            //			"area": "zzz",                              所在地区
            //			"identityAuth": "S",                        身份认证 U普通用户 G官方用户 V认证用户 S商家用户 E政府机构
            //			"identityName": null,                       认证名称
            //			"promotionCode": "KTAWKEoC",                推广码
            //			"relationStatus": null,                     关系状态
            //			"userLevel": null                           用户等级
            //		},
            private String id; //自增id
            private String accountName; //老表视频号
            private String mobile; //注册手机号
            private String createTime;
            private String inviteCode; //邀请码
            private String status; //用户状态
            private String nickName; //昵称
            private String logo; //头像
            private String des; //描述
            private String area; //所在地区
            private String identityAuth;//身份认证U普通用户G官方用户V认证用户S商家用户E政府机构
            private String identityName; //认证名称
            private String promotionCode; //推广码
            private String relationStatus; //关系状态
            private String userLevel; //用户等级
            //星座
            private String constellation;
            //用户标签
            private String userTag;
            //年龄
            private Integer userAge;

            public void setRelationStatus(String relationStatus) {
                this.relationStatus = relationStatus;
            }

            public String getPromotionCode() {
                return promotionCode;
            }

            public void setPromotionCode(String promotionCode) {
                this.promotionCode = promotionCode;
            }

            public String getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(String userLevel) {
                this.userLevel = userLevel;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIdentityAuth() {
                return identityAuth;
            }

            public void setIdentityAuth(String identityAuth) {
                this.identityAuth = identityAuth;
            }

            public String getIdentityName() {
                return identityName;
            }

            public void setIdentityName(String identityName) {
                this.identityName = identityName;
            }

            public String getInviteCode() {
                return inviteCode;
            }

            public void setInviteCode(String inviteCode) {
                this.inviteCode = inviteCode;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

//            public UserStatus getRelationStatus() {
//                return ;
//            }
//
//            public void setRelationStatus(UserStatus relationStatus) {
//                this.relationStatus = relationStatus;
//            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            private class UserStatus{
                private String fansStatus;
                private String followsStatus;

            }

            public String getConstellation() {
                return constellation;
            }

            public void setConstellation(String constellation) {
                this.constellation = constellation;
            }

            public String getUserTag() {
                return userTag;
            }

            public void setUserTag(String userTag) {
                this.userTag = userTag;
            }

            public String getRelationStatus() {
                return relationStatus;
            }

            public Integer getUserAge() {
                return userAge;
            }

            public void setUserAge(Integer userAge) {
                this.userAge = userAge;
            }
        }
    }
}
