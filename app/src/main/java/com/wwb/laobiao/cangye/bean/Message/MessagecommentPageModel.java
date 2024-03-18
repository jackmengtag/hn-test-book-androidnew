package com.wwb.laobiao.cangye.bean.Message;
import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;
import java.util.List;

public class MessagecommentPageModel extends BaseModel implements Serializable {
    Body body;

    public List<record> getrecords() {
        if (body == null) {
            return null;
        }
        return body.records;
    }

    public class Body {
        List<record> records;
        public int currentPage;
        public int maxResults;
        public double totalPages;
        public double totalRecords;
    }

    public class record {
//        accountId	评论的用户id	string
//        coverUrl	视频封面	string
//        createTime	评论时间	string
//        id	评论记录id	string
//        logo	评论的用户头像	string
//        nickName	评论的用户名称	string
//        parent	父评论（为0时代表回复的是视频，为1时代表回复的是评论）	string
//        status	是否已阅读 0-未阅读 1-已阅读	string
        public String accountId;//
        public String contents;//
        public String coverUrl;// 	视频封面	string
        public String createTime;//
        public String id;//
        public String logo;//
        public String nickName;//
        public String parent;//
        public String status;//
    }
}