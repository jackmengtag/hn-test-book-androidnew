package com.wwb.laobiao.cangye.bean.Message;
import com.wwb.laobiao.cangye.bean.Fans.FansselectAllModel;
import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;
import java.util.List;

public class MessageFansPageModel extends BaseModel implements Serializable {
    Body body;
    public List<record> getrecords() {
        if(body==null)
        {
            return null;
        }
        return body.records;
    }
    public class Body {
        List<record> records;
        public	int   currentPage;
        public	int   maxResults;
        public	double   totalPages;
        public	double   totalRecords;
    }

    public class record {
//        public	String id;//"id": 11,
//        public	String accountId;//"accountId": 265,
//        public	String fansAccountId;//"fansAccountId": 245,
//        public	String status;//"status": 0,
//        public	String deleted;//"deleted": 0,
//        public	String createTime;//"createTime": null
        public	 String    accountId	;//粉丝的用户id	string
        public	String   createTime	;//创建时间	string
        public	String  id	;//粉丝记录id	string
        public	String  logo	;//粉丝的用户头像	string
        public	String   mutual	;//是否相互关注（为0时代表不是相互关注，为1时代表已相互关注）	string
        public	String  nickName	;//粉丝的用户名称	string
        public	String  status	;//是否已阅读 0-未阅读 1-已阅读
    }

}
