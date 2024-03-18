package com.wwb.laobiao.address.bean;

public class AreaInfor {
    //  	"cityCode": "110000",
//            "currentPage": "0",
//            "pageSize": "999"
//	"currentPage": "0",
//            "pageSize": "999"
    String currentPage;
    String pageSize;
    String cityCode;

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPageSize() {
        return pageSize;
    }
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
