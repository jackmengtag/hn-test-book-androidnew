package com.yangna.lbdsp.mall.model;

import java.util.List;

public class MallModel {

    /* 门店列表 /account/shop/getShopByAddress 接口返回值 */
    //{
    //	"currentPage": 0,
    //	"maxResults": 10,
    //	"totalPages": 0,
    //	"totalRecords": 0,
    //	"records": [{
    //		"id": 1,
    //		"accountId": 1,
    //		"shopName": "wandayingcheng",                               门店名称
    //		"address": "liuzhou",                                       门店所在区域
    //		"mapAddress": "https://csdnimg.cn/images/csdnqr.png",       门店头像图片url
    //      "shopLogoUrl": "https://bkimg.cdn.bcebos.com/pic/c2f.jpg",  门店头像图片url
    //		"enterpriseCertification": "1",                             企业认证，0未通过，1已通过
    //		"mallLevel": 1,                                             门店等级，1-5级
    //		"focus": 0,                                                 当前用户是否已关注该门店，0未关注，1已关注
    //		"firstFigure1": "https://csdnimg.cn/images/st1.png",        门店首图1，有就返回url，没有就返回 “” 空
    //		"firstFigure2": "https://csdnimg.cn/images/st2.png",        门店首图2，有就返回url，没有就返回 “” 空
    //		"firstFigure3": "",                                         门店首图3，有就返回url，没有就返回 “” 空
    //		"firstFigure4": "",                                         门店首图4，有就返回url，没有就返回 “” 空
    //		"status": "1",
    //		"createTime": 20201009
    //	}]
    //}

    /* 2021年1月14日22:33:43 总店铺列表接口 /tWebshopShop */
    //{
    //  "body": {
    //    "currentPage": 2,
    //    "maxResults": 4,
    //    "totalPages": 2,
    //    "totalRecords": 8,
    //    "records": [
    //
    //      {
    //        "id": "2",
    //        "shopName": "东环水果店",
    //        "startTime": null,
    //        "endTime": null,
    //        "createTime": "2020-10-06 16:39:47",
    //        "chargingMode": null,
    //        "shopLevelId": "1",
    //        "status": 0,
    //        "shopAccount": null,
    //        "companyName": null,
    //        "companyAddress": "A区2号",
    //        "businessCertification": "450203600116452",
    //        "operationRange": "水果",
    //        "organizationCode": null,
    //        "score": null,
    //        "currentPackage": null,
    //        "numberOfProductsThatCanBeReleased": null,
    //        "provinceId": "2",
    //        "cityId": "2",
    //        "areaId": "110101",
    //        "streetId": "110101001",
    //        "shopSaledCount": null,
    //        "shopSubscribeCount": null,
    //        "newArrivalDate": null,
    //        "userId": null,
    //        "longitude": 109.41105,
    //        "latitude": 24.303792,
    //        "isBranchCompany": null,
    //        "shopLogo": null,
    //        "deleted": 0
    //      }
    //    ]
    //  },
    //  "state": 200,
    //  "msg": "请求成功"
    //}

    private Body body;
    private int state;
    private String msg;

    public void setBody(Body body) {
        this.body = body;
    }
    public Body getBody() {
        return body;
    }

    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public static class Body {

        private int currentPage;
        private int maxResults;
        private int totalPages;
        private int totalRecords;
        private List<Records> records;

        public static class Records {
            private String id;
            private String shopName;
            private String startTime;
            private String endTime;
            private String createTime;
            private String chargingMode;
            private String shopLevelId;
            private String status;
            private String shopAccount;
            private String companyName;
            private String companyAddress;
            private String businessCertification;
            private String operationRange;
            private String organizationCode;
            private String score;
            private String currentPackage;
            private String numberOfProductsThatCanBeReleased;
            private String provinceId;
            private String cityId;
            private String areaId;
            private String streetId;
            private String shopSaledCount;
            private String shopSubscribeCount;
            private String newArrivalDate;
            private String userId;
            private String longitude;
            private String latitude;
            private String isBranchCompany;
            private String shopLogo;
            private String deleted;

            public void setId(String id) {
                this.id = id;
            }
            public String getId() {
                return id;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }
            public String getShopName() {
                return shopName;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }
            public String getStartTime() {
                return startTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }
            public String getEndTime() {
                return endTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
            public String getCreateTime() {
                return createTime;
            }

            public void setChargingMode(String chargingMode) {
                this.chargingMode = chargingMode;
            }
            public String getChargingMode() {
                return chargingMode;
            }

            public void setShopLevelId(String shopLevelId) {
                this.shopLevelId = shopLevelId;
            }
            public String getShopLevelId() {
                return shopLevelId;
            }

            public void setStatus(String status) {
                this.status = status;
            }
            public String getStatus() {
                return status;
            }

            public void setShopAccount(String shopAccount) {
                this.shopAccount = shopAccount;
            }
            public String getShopAccount() {
                return shopAccount;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }
            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyAddress(String companyAddress) {
                this.companyAddress = companyAddress;
            }
            public String getCompanyAddress() {
                return companyAddress;
            }

            public void setBusinessCertification(String businessCertification) {
                this.businessCertification = businessCertification;
            }
            public String getBusinessCertification() {
                return businessCertification;
            }

            public void setOperationRange(String operationRange) {
                this.operationRange = operationRange;
            }
            public String getOperationRange() {
                return operationRange;
            }

            public void setOrganizationCode(String organizationCode) {
                this.organizationCode = organizationCode;
            }
            public String getOrganizationCode() {
                return organizationCode;
            }

            public void setScore(String score) {
                this.score = score;
            }
            public String getScore() {
                return score;
            }

            public void setCurrentPackage(String currentPackage) {
                this.currentPackage = currentPackage;
            }
            public String getCurrentPackage() {
                return currentPackage;
            }

            public void setNumberOfProductsThatCanBeReleased(String numberOfProductsThatCanBeReleased) {
                this.numberOfProductsThatCanBeReleased = numberOfProductsThatCanBeReleased;
            }
            public String getNumberOfProductsThatCanBeReleased() {
                return numberOfProductsThatCanBeReleased;
            }

            public void setProvinceId(String provinceId) {
                this.provinceId = provinceId;
            }
            public String getProvinceId() {
                return provinceId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }
            public String getCityId() {
                return cityId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }
            public String getAreaId() {
                return areaId;
            }

            public void setStreetId(String streetId) {
                this.streetId = streetId;
            }
            public String getStreetId() {
                return streetId;
            }

            public void setShopSaledCount(String shopSaledCount) {
                this.shopSaledCount = shopSaledCount;
            }
            public String getShopSaledCount() {
                return shopSaledCount;
            }

            public void setShopSubscribeCount(String shopSubscribeCount) {
                this.shopSubscribeCount = shopSubscribeCount;
            }
            public String getShopSubscribeCount() {
                return shopSubscribeCount;
            }

            public void setNewArrivalDate(String newArrivalDate) {
                this.newArrivalDate = newArrivalDate;
            }
            public String getNewArrivalDate() {
                return newArrivalDate;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
            public String getUserId() {
                return userId;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
            public String getLongitude() {
                return longitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }
            public String getLatitude() {
                return latitude;
            }

            public void setIsBranchCompany(String isBranchCompany) {
                this.isBranchCompany = isBranchCompany;
            }
            public String getIsBranchCompany() {
                return isBranchCompany;
            }

            public void setShopLogo(String shopLogo) {
                this.shopLogo = shopLogo;
            }
            public String getShopLogo() {
                return shopLogo;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }
            public String getDeleted() {
                return deleted;
            }

        }

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

        public void setRecords(List<Records> records) {
            this.records = records;
        }
        public List<Records> getRecords() {
            return records;
        }

    }



}
