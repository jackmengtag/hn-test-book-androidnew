package com.wwb.laobiao.address.bean;

import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.home.bean.GoodsDetailBean;

import java.io.Serializable;
import java.util.List;

public class CityModel extends BaseModel implements Serializable {
	public   Body   body;
	public class Body {
		public   int   currentPage;	//	integer(int32)
		public   int   maxResults;	//	integer(int32)
		long    totalPages;//		integer(int64)
		long    totalRecords;//		integer(int64)
		public List<City> records;
		public class City {
			public  String         cityCode;//
			public  int             cityId;//
			public  String          cityName;//
			public  String          currentPage;//
			public  int           dataState;//
			public  String          gmtCreate;//
			public  String          gmtModified;//
			public  String          lat;//
			public  String          lng;//
			public  String          memo;//
			public  String          pageSize;//
			public  String          provinceCode;//
			public  String          shortName;//
			public  int          sort;//
			public  String          tenantCode;//
		}
	}

}
