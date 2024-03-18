package com.wwb.laobiao.address.bean;

import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;
import java.util.List;

public class AreaModel extends BaseModel implements Serializable {
    public   Body   body;
    public class Body {
        public   int   currentPage;	//	integer(int32)
        public   int   maxResults;	//	integer(int32)
        long    totalPages;//		integer(int64)
        long    totalRecords;//		integer(int64)
        public List<Area> records;
        public class Area {
            public  String           areaCode;//
            public  int           areaId;//
            public  String           areaName;//
            public  String           cityCode;//
            public  String           currentPage;//
            public  int           dataState;//
            public  String           gmtCreate;//
            public  String           gmtModified;//
            public  String           lat	;//
            public  String           lng	;//
            public  String           memo	;//
            public  String           pageSize;//
            public  String           shortName;//
            public  int           sort	;//
            public  String           tenantCode;//

        }

    }
}
