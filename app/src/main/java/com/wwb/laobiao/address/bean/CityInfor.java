package com.wwb.laobiao.address.bean;

public class CityInfor {
//  	"cityCode": "110000",
//            "currentPage": "0",
//            "pageSize": "999"
//	"currentPage": "0",
//            "pageSize": "999"
    String currentPage;
    String pageSize;
    String provinceCode;

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
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
