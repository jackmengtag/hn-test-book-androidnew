package com.wwb.laobiao.cangye.bean.Message;
import com.yangna.lbdsp.common.base.BaseModel;

import java.io.Serializable;

public class MessageamountModel extends BaseModel implements Serializable {
    public  Body body;
    public class Body {
        public	String   comment;//	评论	string
        public	String  fans	;//粉丝	string
        public	String   praise	;//点赞与收藏
    }

}
