package com.wwb.laobiao.cangye.bean.Fans;
import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;
import java.util.List;

public class FansselectAllModel extends BaseModel implements Serializable {
    Body body;

    public List<record> getrecords() {
        if(body==null)
        {
            return null;
        }
        return body.records;
    }

    public class Body {
             List<record>records;
             public	String    total;//"total": 1,
             public	String    size;//"size": 11,
             public	String    current;//"current": 1,
             public	List<String> orders;//"orders": [],
             public	String    hitCount;//"hitCount": false,
             public	String    searchCount;//"searchCount": true,
             public	String    pages;//"pages": 1

    }
    public class record {
		public	String id;//"id": 11,
		public	String accountId;//"accountId": 265,
		public	String fansAccountId;//"fansAccountId": 245,
		public	String status;//"status": 0,
		public	String deleted;//"deleted": 0,
		public	String createTime;//"createTime": null
    }
}
