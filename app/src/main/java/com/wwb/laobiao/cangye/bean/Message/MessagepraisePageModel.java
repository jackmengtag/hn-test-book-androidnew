package com.wwb.laobiao.cangye.bean.Message;
import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;
import java.util.List;

public class MessagepraisePageModel extends BaseModel implements Serializable {
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
//        accountId	点赞的用户id	string
//        coverUrl	视频封面	string
//        createTime	点赞时间	string
//        id	点赞记录id	string
//        logo	点赞的用户头像	string
//        nickName	点赞的用户名称	string
//        status	是否已阅读 0-未阅读 1-已阅读
        public	 String   accountId	;//粉丝的用户id	string
        public	String   coverUrl	;//创建时间	string
        public	String   createTime	;//创建时间	string
        public	String  id	;//粉丝记录id	string
        public	String  logo	;//粉丝的用户头像	string
        public	String  nickName	;//粉丝的用户名称	string
        public	String  status	;//是否已阅读 0-未阅读 1-已阅读

    }

}
