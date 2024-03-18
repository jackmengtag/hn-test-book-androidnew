package com.wwb.laobiao.instance;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wwb.laobiao.address.FukuanActivity1;
import com.wwb.laobiao.address.UserAdaddressActivity;
import com.wwb.laobiao.address.bean.AdressInfor;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.AdressUser;
import com.wwb.laobiao.address.bean.AreaInfor;
import com.wwb.laobiao.address.bean.AreaModel;
import com.wwb.laobiao.address.bean.CityInfor;
import com.wwb.laobiao.address.bean.CityModel;
import com.wwb.laobiao.address.bean.DelAdressInfor;
import com.wwb.laobiao.address.bean.DelListAdressInfor;
import com.wwb.laobiao.address.bean.ProvinceInfor;
import com.wwb.laobiao.address.bean.ProvinceModel;
import com.wwb.laobiao.address.bean.SelAdressInfor;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.address.bean.UpAdressInfor;
import com.wwb.laobiao.cangye.bean.OrderInfor;
import com.wwb.laobiao.cangye.bean.OrderListInfor;
import com.wwb.laobiao.cangye.bean.OrderListModel;
import com.wwb.laobiao.cangye.bean.OrderModel;
import com.wwb.laobiao.cangye.bean.PayInfor;
import com.wwb.laobiao.cangye.bean.PayModel;
import com.yangna.lbdsp.MainActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.wxapi.WXPayEntryActivity;

import static com.yangna.lbdsp.wxapi.util.WeiXinConstants.APP_ID;

import java.util.ArrayList;
import java.util.List;

public class UserOrder {
    public String goodid;
    public String goodsName;
    public String goodsPrice;
    public String pictureUrl;
    public String createTime;
    public double deliverFee;//
    public double discountAmount;//
    public double realPayAmount;//

    public ArrayList<String> Provincelist;
    public ArrayList<String> Citylist;
    public ArrayList<String> Arealist;
    public AdressUser adressUser;
    public Context context;
    public List<CityModel.Body.City> Citys;
    public List<AreaModel.Body.Area> Areas;
    public List<ProvinceModel.Body.Province> provinces;
    public String money;
    public String orderNo;

    private AlertDialog alert1,alert2;
    private boolean alert2bf=false;
    public boolean bfAdress=false;

    private UserOrder() {
        Citys=new ArrayList<>();
        Areas=new ArrayList<>();
        provinces=new ArrayList<>();
        if(Provincelist==null)
        {
            Provincelist=new ArrayList<>();
//            Provincelist.add("广西");
        }
        if(Citylist==null)
        {
            Citylist=new ArrayList<>();
//            Citylist.add("柳州");
        }
        if(Arealist==null)
        {
            Arealist=new ArrayList<>();
//            Arealist.add("柳南区");
        }
        if(adressUser==null)
        {
            adressUser=new AdressUser();
        }

    }
    public void selAdress(Context context,Orderinterface orderinterface) {
        bfAdress=true;
        MyObserver<SelAdressModel> observerx= new MyObserver<SelAdressModel>() {
            @Override
            public void onNext(SelAdressModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");//showtip
                    return;
                }
                else
                {

                    if(superiorModel.getState()==200)
                    {
                        adressUser.mlist.clear();
                        for(int i=0;i<superiorModel.getbodysize();i++)
                        {
                            SelAdressModel.Body.record tmppsx = superiorModel.body.records.get(i);;
                            adressUser.mlist.add(tmppsx);
                        }
                        if(orderinterface!=null)
                        {
                            orderinterface.onresh(3);
                        }
                    }
                    else
                    {
                        if(orderinterface!=null)
                        {
                            orderinterface.onresh(-3);
                        }
                    }

                }
            }
            @Override
            public void onError(Throwable e) {
                if(orderinterface!=null)
                {
                    orderinterface.onresh(-3);
                }
                ToastManager.showToast(context, e.toString());
            }
        };
        SelAdressInfor selAdressInfor=new SelAdressInfor();
        selAdressInfor.setPageSize("10");
        selAdressInfor.setCurrentPage("1");
        NetWorks.getInstance().selAdress(context,selAdressInfor,observerx);
    }
    public void addadress(Context context,AdressInfor user,Orderinterface orderinterface)
    {
        MyObserver<AdressModel> observerx= new MyObserver<AdressModel>() {
            @Override
            public void onNext(AdressModel superiorModel) {
                if(superiorModel==null)
                {
//                  ToastManager.showToast(context, "null");
                    return;
                }
                else
                {
//                    ToastManager.showToast(context, superiorModel.getMsg()+superiorModel.getState());
                    if(superiorModel.getState()==200)
                    {//tmpadress
//                        "body": true,
//                            "msg": "",
//                            "state": 0
                        if(orderinterface!=null)
                        {
                            orderinterface.onresh(4);
                            return;
                        }
                    }

                }
            }
            @Override
            public void onError(Throwable e) {
//                ToastManager.showToast(context, "onEr");
                if(orderinterface!=null)
                {
                    orderinterface.onresh(-4);
                }
            }
        };
        if(user==null)
        {
            user=new AdressInfor();
        }
        user.setaccountId(BaseApplication.getInstance().getAccountId());//getSubordinate
        NetWorks.getInstance().addAdress(context,user,observerx);
    }
    public void upAdress(Context context,AdressInfor user,Orderinterface orderinterface)
    {
        MyObserver<AdressModel> observerx= new MyObserver<AdressModel>() {
            @Override
            public void onNext(AdressModel superiorModel) {
                if(superiorModel==null)
                {
//                    ToastManager.showToast(context, "null");
                    return;
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(orderinterface!=null)
                        {
                            orderinterface.onresh(5);
                        }
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, "onEr");
                if(orderinterface!=null)
                {
                    orderinterface.onresh(-5);
                }
            }
        };
        if(user==null)
        {
            user=new AdressInfor();
        }
        UpAdressInfor tmpuser=new UpAdressInfor();
        tmpuser.set(user);
        user.setaccountId(BaseApplication.getInstance().getAccountId());//getSubordinate
        NetWorks.getInstance().upAdress(context, tmpuser,observerx);
    }
    public void dellistAdress(Context userAdaddressActivity, DelListAdressInfor user, Orderinterface orderinterface) {
//        AdressModel adressModel=new AdressModel(); DelListAdressInfor
//        AdressModel adressModel=adressUser.mlist.get(selectItem);
        MyObserver<BaseModel> observerx= new MyObserver<BaseModel>() {
            @Override
            public void onNext(BaseModel superiorModel) {
                if(superiorModel==null)
                {
//                    ToastManager.showToast(context, "null");
                    return;
                }
                else
                {//DelAdressInfor
//                    ToastManager.showToast(context, superiorModel.getMsg()+superiorModel.getState());
                    if(superiorModel.getState()==200)
                    {
                      if(orderinterface!=null)
                        {
                            orderinterface.onresh(6);
                        }
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
//                ToastManager.showToast(context, "onEr");
                if(orderinterface!=null)
                {
                    orderinterface.onresh(-6);
                }
            }
        };

        NetWorks.getInstance().DelListAdressInfor(context,user,observerx);
    }
    public void reshall(Context context) {
        shopProvincelist(context,null,true);
        selAdress(context,null);
    }
    public void shopProvincelist(Context context,Orderinterface orderinterface,boolean bfkeep) {
        MyObserver<ProvinceModel> observerx= new MyObserver<ProvinceModel>() {
            @Override
            public void onNext(ProvinceModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");//showtip
                    return;
                }
                else
                {

                    if(superiorModel.getState()==200)
                    {
                        if(Provincelist==null)
                        {
                            Provincelist=new ArrayList<>();
                        }
                        Provincelist.clear();
                        provinces=superiorModel.body.records;
                        for(int i=0;i<provinces.size();i++)
                        {
                            Provincelist.add(provinces.get(i).provinceName);
                        }
                        if(Provincelist.size()>0)
                        {
                            if(bfkeep)
                            {
                                shopCitylist(context,getProvinceCode(Provincelist.get(0)),orderinterface,bfkeep);
                            }

                        }
                        if(orderinterface!=null)
                        {
                            orderinterface.onresh(0);
                        }
                    }

                }
            }
            @Override
            public void onError(Throwable e) {
                if(orderinterface!=null)
                {
                    orderinterface.onresh(-9);
                }
                ToastManager.showToast(context, "onEr");
            }
        };
        ProvinceInfor selAdressInfor=new ProvinceInfor();
        selAdressInfor.setCurrentPage("1");
        selAdressInfor.setPageSize("200");
//        selAdressInfor.setaccountId(BaseApplication.getInstance().getAccountId());
        NetWorks.getInstance().shopProvincelist(context,selAdressInfor,observerx);
    }

    public String getProvinceCode(String Provincename) {
         for(int i=0;i<provinces.size();i++)
         {
             if(Provincename.equals(provinces.get(i).provinceName))
             {
                return  provinces.get(i).provinceCode;
             }
         }
        return "";
    }

    public void shopArealist(Context context,String citycode,Orderinterface orderinterface) {
        MyObserver<AreaModel> observerx= new MyObserver<AreaModel>() {
            @Override
            public void onNext(AreaModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");//showtip
                    return;
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(Arealist==null)
                        {
                            Arealist=new ArrayList<>();
                        }
                        Arealist.clear();
                        Areas=superiorModel.body.records;
                        for(int i=0;i<Areas.size();i++)
                        {
                            Arealist.add(Areas.get(i).areaName);
                        }
                        if(orderinterface!=null)
                        {
                            orderinterface.onresh(2);
                        }
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
                if(orderinterface!=null)
                {
                    orderinterface.onresh(-2);
                }
                ToastManager.showToast(context, "onEr");
            }
        };
        AreaInfor selAdressInfor=new AreaInfor();
        selAdressInfor.setCurrentPage("1");
        selAdressInfor.setPageSize("200");
        selAdressInfor.setCityCode(citycode);
//        selAdressInfor.setaccountId(BaseApplication.getInstance().getAccountId()); citycode
        NetWorks.getInstance().shopArealist(context,selAdressInfor,observerx);
    }
    public void shopCitylist(Context context, String ProvinceCode,Orderinterface orderinterface,boolean bfkeep) {
        MyObserver<CityModel> observerx= new MyObserver<CityModel>() {
            @Override
            public void onNext(CityModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");//showtip
                    return;
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(Citylist==null)
                        {
                            Citylist=new ArrayList<>();
                        }
                        Citylist.clear();
                        Citys=superiorModel.body.records;
                        for(int i=0;i<Citys.size();i++)
                        {
                            Citylist.add(Citys.get(i).cityName);
                        }
                        if(Citylist.size()>0)
                        {
                            if(bfkeep)
                            {
                                shopArealist(context,getCityCode(Citylist.get(0)),orderinterface);
                            }
                        }
                       if(orderinterface!=null)
                       {
                           orderinterface.onresh(1);
                       }
//                        if(Provincelist==null)
//                        {
//                            Provincelist=new ArrayList<>();
//                        }
//                        Provincelist.clear();
//                        provinces=superiorModel.records;
//                        for(int i=0;i<provinces.size();i++)
//                        {
//                            Provincelist.add(provinces.get(i).provinceName);
//                        }
//                        if(Provincelist.size()>0)
//                        {
//                            shopCitylist(context,getProvinceCode(Provincelist.get(0)));
//                        }
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, "onEr");
                if(orderinterface!=null)
                {
                    orderinterface.onresh(-1);
                }
            }
        };
        CityInfor selAdressInfor=new CityInfor();
        selAdressInfor.setCurrentPage("1");
        selAdressInfor.setPageSize("200");

        selAdressInfor.setProvinceCode(ProvinceCode);
//        selAdressInfor.setaccountId(BaseApplication.getInstance().getAccountId());
        NetWorks.getInstance().shopCitylist(context,selAdressInfor,observerx);
    }
    public String getCityCode(String Cityname) {
        for(int i=0;i<Citys.size();i++)
        {
            if(Cityname.equals(Citys.get(i).cityName))
            {
                return  Citys.get(i).cityCode;
            }
        }
        return "";
    }
    private static UserOrder mUserOrder;
    public static UserOrder getInstance() {
        if (mUserOrder == null) {
            synchronized (UserOrder.class) {
                if (mUserOrder == null) {
                    mUserOrder = new UserOrder();
                }
            }

        }
        return mUserOrder;
    }

    public int getprovincespos(String provinceid) {
        for(int i=0;i<provinces.size();i++)
        {
              String str0=String.format("%d",provinces.get(i).provinceId);
              if(str0.equals(provinceid))
              {
                      return i;
              }
        }
       return 0;
    }

    public int getcitypos(String cityId) {
        for(int i=0;i<Citys.size();i++)
        {
            String str0=String.format("%d",Citys.get(i).cityId);
            if(str0.equals(cityId))
            {
                return i;
            }
        }
        return 0;
    }
    public int getareapos(String areaId) {
        for(int i=0;i<Areas.size();i++)
        {
            String str0=String.format("%d",Areas.get(i).areaId);
            if(str0.equals(areaId))
            {
                return i;
            }
        }
        return 0;
    }
    public Boolean getprovincecityarename(List<String>names,List<String>Ids) {
        names.clear();
        if(Ids.size()==3)
        {
            for(int i=0;i<provinces.size();i++)
            {
                String str0=String.format("%d",provinces.get(i).provinceId);
                if(str0.equals(Ids.get(0)))
                {
                    names.add(provinces.get(i).provinceName);
                }
            }
        }
        return false;
    }
    public String getprovincename(String provinceId) {

        for(int i=0;i<provinces.size();i++)
        {
            String str0=String.format("%d",provinces.get(i).provinceId);
            if(str0.equals(provinceId))
            {
                return provinces.get(i).provinceName;
            }
        }
       return "";
    }
    public String getcityname(String cityId) {

        for(int i=0;i<Citys.size();i++)
        {
            String str0=String.format("%d",Citys.get(i).cityId);
            if(str0.equals(cityId))
            {
                return Citys.get(i).cityName;
            }
        }
        return "";
    }
    public String getarename(String areaName) {

        for(int i=0;i<Areas.size();i++)
        {
            String str0=String.format("%d",Areas.get(i).areaId);
            if(str0.equals(areaName))
            {
                return Areas.get(i).areaName;
            }
        }
        return "";
    }

    public AdressModel getseladress() {
        AdressModel adressModel=new AdressModel();
        adressModel.setid(-1);
        for(int i=0;i<adressUser.mlist.size();i++)
        {
            if(adressUser.mlist.get(i).isDefault>0)
            {
                adressModel.setid(adressUser.mlist.get(i).id);
                adressModel.Areid=adressUser.mlist.get(i).areaId;
                adressModel.CityId=adressUser.mlist.get(i).cityId;
                adressModel.ProvinceId=adressUser.mlist.get(i).provinceId;
                adressModel.provinceName=adressUser.mlist.get(i).provinceName;
                adressModel.cityName=adressUser.mlist.get(i).cityName;
                adressModel.areaName=adressUser.mlist.get(i).areaName;

                adressModel.setaddress(adressUser.mlist.get(i).getaddress());
                adressModel.setmobile(adressUser.mlist.get(i).getmobile());
                adressModel.setname(adressUser.mlist.get(i).getname());
                return  adressModel;
            }
        }
        if(adressUser.mlist.size()>0)
        {
            adressModel.setid(adressUser.mlist.get(0).id);
            adressModel.Areid=adressUser.mlist.get(0).areaId;
            adressModel.CityId=adressUser.mlist.get(0).cityId;
            adressModel.ProvinceId=adressUser.mlist.get(0).provinceId;
            adressModel.setaddress(adressUser.mlist.get(0).getaddress());
            adressModel.setmobile(adressUser.mlist.get(0).getmobile());
            adressModel.setname(adressUser.mlist.get(0).getname());

            adressModel.provinceName=adressUser.mlist.get(0).provinceName;
            adressModel.cityName=adressUser.mlist.get(0).cityName;
            adressModel.areaName=adressUser.mlist.get(0).areaName;
        }
        return  adressModel;
    }
    public void protip(String dellistAdress,Context context) {
         if(alert2!=null)
         {
             if(alert2.isShowing())
             {
                 alert2.setTitle(dellistAdress);
             }
         }
        if(alert2bf)
        {
            alert2bf=false;
            return;
        }
        alert2bf=true;
//        Thread thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i=0;i<10;i++)
//                {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if(!alert2bf)
//                    {
//                        return;
//                    }
//                }
//                alert2.show();
//
//            }
//        });
//        thread.start();
        alert2 = new AlertDialog.Builder(context).create();
//        alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
        alert2.setCancelable(false);
//        alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        alert2.setTitle(dellistAdress);
        alert2.show();

    }
    public void notetip(String tipstr,Context context) {
        if(alert1!=null)
        {
            if(alert1.isShowing())
            {
                alert1.setTitle(tipstr);
                return;
            }
        }
        alert1 = new AlertDialog.Builder(context).create();
//        alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
        alert1.setCancelable(false);
        alert1.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert1.setTitle(tipstr);
        alert1.show();

    }
    public void distip(boolean b) {
        if(b)
        {
            if(alert2!=null)
            {
                alert2.dismiss();
            }
        }
        else
        {
            if(alert2!=null)
            {
                alert2.show();
            }
        }
        alert2bf=false;
    }


    public void oncreatpay(Context context, OrderListInfor user,Orderinterface orderinterface) {
        MyObserver<OrderListModel> observerx= new MyObserver<OrderListModel>() {
            @Override
            public void onNext(OrderListModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
//                    ToastUtil.showToast("你已经了购买商品");
//                    showerweima("商家付款二维码");
//                    superiorModel.getBody().appId
//                    ToastUtil.showToast(superiorModel.getMsg());//wxb066fdba033fba34
                    if(superiorModel.getState()==200)
                    {
                        if(orderinterface!=null)
                        {
                            orderinterface.onresh(11);
                        }
                        onpaylist(context,superiorModel.body);

                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(orderinterface!=null)
                        {
                            orderinterface.onresh(-11);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(orderinterface!=null)
                {
                    orderinterface.onresh(-11);
                }
                ToastManager.showToast(context, "onEr");
            }
        };
        NetWorks.getInstance().CreateListOrder(context,user,observerx);

    }
    public void oncreatstillpay(Context context, OrderInfor user,Orderinterface orderinterface) {
        MyObserver<OrderModel> observerx= new MyObserver<OrderModel>() {
            @Override
            public void onNext(OrderModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
//                    ToastUtil.showToast("你已经了购买商品");
//                    showerweima("商家付款二维码");
//                    superiorModel.getBody().appId superiorModel
//                    ToastUtil.showToast(superiorModel.getMsg());//wxb066fdba033fba34
                    if(superiorModel.getState()==200)
                    {
                        if(orderinterface!=null)
                        {
                            orderinterface.onresh(12);
                        }
                        onpay(context,superiorModel.getBody());

                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(orderinterface!=null)
                        {
                            orderinterface.onresh(-12);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(orderinterface!=null)
                {
                    orderinterface.onresh(-12);
                }
                ToastManager.showToast(context, "onEr");
            }
        };




        NetWorks.getInstance().CreateOrder(context,user,observerx);

    }

    private void onpaylist(Context context, OrderListModel.Body body) {
        if(body.list.size()>0)
        {
            IWXAPI wxapi = WXAPIFactory.createWXAPI(context, APP_ID,false);
            wxapi.registerApp(APP_ID);
            MyObserver<PayModel> observerx= new MyObserver<PayModel>() {
                @Override
                public void onNext(PayModel superiorModel) {
                    if(superiorModel==null)
                    {
                        ToastManager.showToast(context, "null");
                        return;//
                    }
                    else
                    {
                        if(superiorModel.getState()==200)
                        {
                            PayReq req = new PayReq();
                            req.appId = superiorModel.getBody().appId;//wx35e432b6a10cde0f
                            req.partnerId =  superiorModel.getBody().partnerId;
                            req.prepayId =  superiorModel.getBody().prepayId;//wx14084951483143a08538978f1409630000
                            req.packageValue = superiorModel.getBody().packageStr;
                            req.nonceStr =  superiorModel.getBody().nonceStr;
                            req.timeStamp =  superiorModel.getBody().timeStamp;
                            req.sign = superiorModel.getBody().appSign;//a1bec05ab8b5d0b921f63949e0454b18
                            WXPayEntryActivity.orderNo=superiorModel.getBody().orderNo;
                            WXPayEntryActivity.money=superiorModel.getBody().money;
                            WXPayEntryActivity.payDate=superiorModel.getBody().payDate;
                            boolean result = wxapi.sendReq(req);
                            Log.v("支付结果:=======",result+"");
                        }
                        else
                        {
                            Log.v("支付结果:=======","errr"+superiorModel.getState());
                        }

                    }
                }
                @Override
                public void onError(Throwable e) {
                    ToastManager.showToast(context, e.toString()+"onEr");
                }
            };
            int   payType=2;
            PayInfor user=new PayInfor();
            user.orderIdList=new ArrayList<>();
            double fmoney=0;
            user.orderId=body.list.get(0).orderSn;
//            user.setOrderNo(body.list.get(0).orderSn);
            for(int i=0;i<body.list.size();i++)
               {
                   user.orderIdList.add(body.list.get(i).id);
                   fmoney=+body.list.get(i).realPayAmount;
               }
            user.setChannel(String.format("%02d",payType));
            user.setMoney(String.format("%4.2f",fmoney));
            NetWorks.getInstance().PayOrder(context,user,observerx);
        }
    }
    public static float getfloat(String strval) {
        try {
              return Float.valueOf(strval);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return 0;
    }
    private void onpay(Context context,OrderModel.OrderBody body) {
        UserOrder.getInstance().createTime=body.createTime;
        UserOrder.getInstance().deliverFee=body.deliverFee;
        UserOrder.getInstance().discountAmount=body.discountAmount;
        UserOrder.getInstance().realPayAmount=body.realPayAmount;
        {
            //orderId
            IWXAPI wxapi = WXAPIFactory.createWXAPI(context, APP_ID,false);
            wxapi.registerApp(APP_ID);
            MyObserver<PayModel> observerx= new MyObserver<PayModel>() {
                @Override
                public void onNext(PayModel superiorModel) {
                    if(superiorModel==null)
                    {
                        ToastManager.showToast(context, "null");
                        return;//
                    }
                    else
                    {
                        if(superiorModel.getState()==200)
                        {
                            PayReq req = new PayReq();
                            req.appId = superiorModel.getBody().appId;//wx35e432b6a10cde0f
                            req.partnerId =  superiorModel.getBody().partnerId;
                            req.prepayId =  superiorModel.getBody().prepayId;//wx14084951483143a08538978f1409630000
                            req.packageValue = superiorModel.getBody().packageStr;
                            req.nonceStr =  superiorModel.getBody().nonceStr;
                            req.timeStamp =  superiorModel.getBody().timeStamp;
                            req.sign = superiorModel.getBody().appSign;//a1bec05ab8b5d0b921f63949e0454b18
                            WXPayEntryActivity.orderNo=superiorModel.getBody().orderNo;
                            WXPayEntryActivity.money=superiorModel.getBody().money;
                            boolean result = wxapi.sendReq(req);
                            Log.v("支付结果:=======",result+"");
                        }
                        else
                        {
                            Log.v("支付结果:=======","errr"+superiorModel.getState());
                        }

                    }
                }
                @Override
                public void onError(Throwable e) {
                    ToastManager.showToast(context, e.toString()+"onEr");
                }
            };
            PayInfor user=new PayInfor();
            user.orderId=body.id;//money
            user.setMoney(user.money);
            user.setChannel(String.format("%02d",body.payType));
            NetWorks.getInstance().PayOrder(context,user,observerx);
        }
    }
    public interface Orderinterface {
        void onresh(int idex);/// 0  pro 1 city 2 area 3 seladdr 4 addadress  5 upAdress  6 delAdress 7 del list

    }
}
