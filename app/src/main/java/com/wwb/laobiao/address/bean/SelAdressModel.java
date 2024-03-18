package com.wwb.laobiao.address.bean;

import com.yangna.lbdsp.common.base.BaseModel;

import java.util.List;

public class SelAdressModel extends BaseModel {
    public  Body body;

    public int getbodysize() {
        if(body==null)
        {
            return -1;
        }
        else
        {
            return body.getSize();
        }

    }

    public class Body {
        public  List<record>records;

        public int getSize() {
            if(records==null)
            {
                return -1;
            }
            return records.size();
        }

        public class record {
            public   long     accountId		;// integer(int64)
            public   String        address	;//	string
            public   String        areaId		;//string
            public   String       cityId		;//string
            public   String       consignee	;//	string
            public   int        deleted	;//	integer(int32)
            public   long       id		;//integer(int64)
            public   int        isDefault		;//integer(int32)
            public   double        latitude		;//number(double)
            public   double        longitude		;//number(double)
            public   String        mobile		;//string
            public   String        provinceId		;//string
            public   String        streetId     ;//
//     "provinceName": "广西壮族自治区",
//             "cityName": "南宁市",
//             "areaName": "兴宁区",
//             "streetName": null
            public   String        provinceName     ;//
            public   String        cityName     ;//
            public   String        areaName     ;//
            public   String        streetName     ;//
            public String getmobile() {
                return mobile;
            }

            public String getaddress() {
                return  address;
            }

            public String getname() {
                return consignee;
            }

            public long getid() {
                return  id;
            }
        }
        long  totalPages ;
        long  totalRecords;
    }
    long  totalPages ;
    long  totalRecords;
}
