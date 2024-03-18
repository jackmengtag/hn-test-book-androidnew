package com.wwb.laobiao.hongbao.bean;



import com.yangna.lbdsp.common.base.BaseModel;

import java.util.List;

public class RankingModel extends BaseModel {
    public List<record>body;
    public int currentPage;
    public  int maxResults;
    public int totalPages;
    public int totalRecords;

    public int getbodysize() {
        if(body==null)
        {
            return -1;
        }
        if(body.size()<1)
        {
            return -1;
        }
        return body.size();
    }

    public class record {
        public int accountId;
        public  String nickName;
        public int praiseTotal;
        public  String logo;
		//String imagPath = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3448936851,681826596&fm=26&gp=0.jpg";
    }
    public  void show()
    {
        if(body!=null)
        {
            System.out.println(body.size()+"size");
        }
        else
        {
//            System.out.println(""records.size()+"size""");
        }

    }
}
