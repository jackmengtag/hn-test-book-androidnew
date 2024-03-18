package com.wwb.laobiao.address.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdressUser implements Serializable {
    public static final String SER_KEY ="SER_KEY" ;
    public int msel;
    public long id;
    public int amount;
    public  List< SelAdressModel.Body.record>mlist;
    public AdressUser()
    {
        msel=0;
        mlist=new ArrayList<>();
//        {
//            AdressModel mtmp=new AdressModel();
//            mtmp.setmobile("a18677216659");
//            mtmp.setaddress("柳州柳南");
//            mlist.add(mtmp);
//        }
//        {
//            AdressModel mtmp=new AdressModel();
//            mtmp.setmobile("218677216659");
//            mtmp.setaddress("柳州柳北");
//            mlist.add(mtmp);
//        }
//        {
//            AdressModel mtmp=new AdressModel();
//            mtmp.setmobile("yyyyyyyy4e44");
//            mtmp.setaddress("yyyyy柳州柳北");
//            mlist.add(mtmp);
//        }
    }
    public long getselId() {
        return id;
    }
    public void setsel(int sel) {
//        id=sel;
//        if(sel>mlist.size())
//        {
//             return;
//        }
//        if(sel<0)
//        {
//            return;
//        }
//        AdressModel mttp = mlist.get(sel);
//        id=mttp.id;
    }

    public int getamount() {
        return amount;
    }
}
