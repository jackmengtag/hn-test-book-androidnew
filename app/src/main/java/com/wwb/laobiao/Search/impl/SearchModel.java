package com.wwb.laobiao.Search.impl;

//import com.qzy.laobiao.common.base.BaseModel;

import com.yangna.lbdsp.common.base.BaseModel;

import java.util.List;

public class SearchModel extends BaseModel {
    public  Searchbody body;
//"body":{"currentPage":0,"maxResults":10,"totalPages":0,"totalRecords":0,"records":[]},"state":200,"msg":"请求成功"}
    public Searchbody getbody() {
        return body;///AgreementModel
//       return "";"body":{"currentPage":0,"maxResults":10,"totalPages":0,"totalRecords":0,"records":[]},"state":200,"msg":"请求成功"}
    }
    public class Searchbody {
        public int currentPage;//0
        public int maxResults;//0
        public int totalPages;//0
        public int totalRecords;//0
        public  List<record> records;
        public List<record> getlist() {
            return records;
        }
        private class record {
//          id":2,"accountName":"2358401767","mobile":"13707724100","createTime":1589875961194,"inviteCode":"oNrgDv0z","status":"0","nickName":"最爱柳州","logo":"/","des":"柳州人的生活","area":"柳州","identityAuth":"U","identityName":null,"relationStatus":null}]},"state":200,"msg":"请求成功"}
            private String accountName;
            private String area;
            private int createTime;//0
            private String des;
            private int id;//0
            private String identityAuth;
            private String identityName;
            private String inviteCode;
            private String logo;
            private String mobile;
            public String nickName;

            public String getname() {
                return this.accountName;
            }
        }

    }
    private Searchbody getBody() {
        return body;
    }
    private void setBody(Searchbody body) {
        this.body = body;
    }
//    String msg=getMsg();
//    String state=getState();
//	 "msg": "string",
//  "state": 0
//    public SearchModel() {
//        body=new Searchbody();
//    }
    //定义 输出返回数据 的方法
    public void show() {

        System.out.println(getMsg());
        System.out.println(getState()+"State");
        List<Searchbody.record> records=getbody().getlist();
        System.out.println(records.size()+"size");
//        for(int i=0;i<records.size();i++)
//        {
//            System.out.println(records.get(i).accountName);
//            System.out.println(records.get(i).nickName);
//        }
    }

    }

