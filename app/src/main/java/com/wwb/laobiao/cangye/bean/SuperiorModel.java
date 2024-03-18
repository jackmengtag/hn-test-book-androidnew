package com.wwb.laobiao.cangye.bean;



import com.yangna.lbdsp.common.base.BaseModel;

public class SuperiorModel extends BaseModel {
//单次获取用户上级信息
    private long accountId;
    Superiorbody body;
    public void show() {
    }

    public long getid() {
        if(body!=null)
        {
            return body.id;
        }
        return 11112;
    }
    private class Superiorbody {
//        "accountName": "string",
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
        private long accountId;//:  string ,
        private String accountName ;//:  string ,
        private String area ;//:  string ,
        private long createTime ;//: 0,
        private String des ;//:  string ,
        private long id ;//: 0,
        private String identityAuth ;//:  string ,
        private String identityName ;//:  string ,
        private  String  inviteCode ;//:  string ,
        private String logo ;//:  string ,
        private String mobile ;//:  string ,
        private String nickName ;//:  string ,
        private String password ;//:  string ,
        private String promotionCode ;//:  string ,
        private String status ;//:  string ,
        private String userLevel ;//:  string
    }

}
