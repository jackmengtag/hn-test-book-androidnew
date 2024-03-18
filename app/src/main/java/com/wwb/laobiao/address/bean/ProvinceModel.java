package com.wwb.laobiao.address.bean;

import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;
import java.util.List;

public class ProvinceModel extends BaseModel implements Serializable {
    public   Body   body;
    public class Body {
        public   int   currentPage;	//	integer(int32)
        public   int   maxResults;	//	integer(int32)
        // List   records;		//array	省份设置--列表
// String   currentPage;//	页码	string
// int   dataState;	//状态	integer(int32)
// String   gmtCreate;//	创建时间	string(date-time)
// String   gmtModified;//	修改时间	string(date-time)
//String    lat;	//纬度	string
//String    lng;	//经度	string
//String    memo;	//备注	string
//String    pageSize;//	页大小	string
//String    provinceCode;	//省份代码	string
//int    provinceId;	//自增列	integer(int32)
//String    provinceName;	//省份名称	string
//String    shortName;	//简称	string
//int    sort;	//排序	integer(int32)
//String    tenantCode;	//租户ID	string
        long    totalPages;//		integer(int64)
        long    totalRecords;//		integer(int64)
        public  List<Province>records;
        public class Province {
            public  String   currentPage;//	页码	string
            public   int   dataState;	//状态	integer(int32)
            public   String   gmtCreate;//	创建时间	string(date-time)
            public    String   gmtModified;//	修改时间	string(date-time)
            public   String    lat;	//纬度	string
            public   String    lng;	//经度	string
            public   String    memo;	//备注	string
            public   String    pageSize;//	页大小	string
            public   String    provinceCode;	//省份代码	string
            public   int    provinceId;	//自增列	integer(int32)
            public   String    provinceName;	//省份名称	string
            public   String    shortName;	//简称	string
            public   int    sort;	//排序	integer(int32)
            public   String    tenantCode;	//租户ID	string
        }
    }

}
