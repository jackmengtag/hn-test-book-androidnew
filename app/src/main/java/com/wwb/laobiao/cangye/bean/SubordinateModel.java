package com.wwb.laobiao.cangye.bean;



import com.yangna.lbdsp.common.base.BaseModel;

import java.util.List;

public class SubordinateModel extends BaseModel {
//
//  "body": {
//        "currentPage": 0,
//                "maxResults": 0,
//                "records": [
//        {
//            "accountName": "string",
//                "area": "string",
//                "createTime": 0,
//                "des": "string",
//                "id": 0,
//                "identityAuth": "string",
//                "identityName": "string",
//                "inviteCode": "string",
//                "logo": "string",
//                "mobile": "string",
//                "nickName": "string",
//                "password": "string",
//                "promotionCode": "string",
//                "status": "string",
//                "userLevel": "string"
//        }
//    ],
//        "totalPages": 0,
//                "totalRecords": 0
//    },
//            "msg": "string",
//            "state": 0
   private  Subordinatebody xxbody;
//   private  long body;
private   List<Integer>body;

    public long getnum() {
        return body.size();
    }

    public List<Integer> getBody() {
        return body;
    }

    private class Subordinatebody {
        int currentPage;
        int  maxResults;
        List<Records>records;
        int totalPages;
        int totalRecords;

        private class Records {
//            String    accountName;//": "string",
//            String    area;//": "string",
//            long    createTime;//": 0,
//            String    des;//": "string",
//            long      id;//": 0,
//            String    identityAuth;//": "string",
//            String    identityName;//": "string",
//            String    inviteCode;//": "string",
//            String    logo;//": "string",
//            String    mobile;//": "string",
//            String    nickName;//": "string",
//            String    password;//": "string",
//            String    promotionCode;//": "string",
//            String    status;//": "string",
//            String    userLevel;//": "string"
//              int
        }
    }
    public void show() {
    }
}
