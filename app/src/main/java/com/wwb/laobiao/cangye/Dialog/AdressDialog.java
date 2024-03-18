package com.wwb.laobiao.cangye.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.wwb.laobiao.address.LinearLayoutDialogTitle;
import com.wwb.laobiao.address.bean.AdressInfor;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.AdressUser;
import com.wwb.laobiao.address.bean.DelAdressInfor;
import com.wwb.laobiao.address.bean.SelAdressInfor;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.address.bean.UpAdressInfor;
import com.wwb.laobiao.cangye.adp.AdressAdapter0;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;

public class AdressDialog implements AdressAdapter0.MsgInterface {
    private  Context context;
    private  AlertDialog adressDialog;
    private AdressDialoginterface adressDialoginterface;
    private AdressUser adressUser;
    private ListView mlistview1;
    private AdressAdapter0 adressAdapter0;
    private Button okbt,addbt,editbt,deltbt;
    private AdressModel tmpadress;
    private int selectItem;
    private Handler mhandler=new Handler(){
        public void handleMessage(Message msg) {
            {
                switch(msg.what)
                {
                    case 0:
                    {

                    }
                    break;
                    case 2:
                    {
                        if(selectItem<0)
                        {
                            return;
                        }
                        if(adressUser.mlist.size()<=selectItem)
                        {
                            return;
                        }
                        delAdress(selectItem);
                    }
                    break;
                    case 1:
                    {
                      //  ToastUtil.showToast("edit");
                        if(selectItem<0)
                        {
                            return;
                        }
                        if(adressUser.mlist.size()<=selectItem)
                        {
                            return;
                        }
                        editadress(selectItem);
                    }
                    break;
                }
            }
        };
    };
    public AdressDialog() {

    }
    public void onCreate(Context context) {
        {
            this.context=context;
            View view = LayoutInflater.from(context).inflate(
                    R.layout.dialog_address, null);
            adressDialog=new AlertDialog.Builder(context)
                    .setView(view).create();
            {
                TextView title = new TextView(context);
                title.setTextSize(20);
                title.setGravity(Gravity.CENTER);
//              title.setBackgroundResource(R.drawable.list);
                title.setText("收货地址");//
                adressDialog.setCustomTitle(title);
            }
            {
                LinearLayoutDialogTitle linearLayoutBottomview=new LinearLayoutDialogTitle(context);
                linearLayoutBottomview.setGravity(Gravity.CENTER);
                linearLayoutBottomview.setinterface(new LinearLayoutDialogTitle.LinearLayoutDialogTitleinterface() {
                    @Override
                    public void onadd(int flat) {
                        if(flat==1)
                        {
                            if(adressDialoginterface!=null)
                            {
                                adressDialoginterface.onsel(0,0);
                            }
                        }
                    }
                });
                adressDialog.setCustomTitle(linearLayoutBottomview);
            }
            mlistview1=view.findViewById(R.id.recyclerview);
            {
                if(adressUser==null)
                {
                    adressUser=new AdressUser();
                }
                adressAdapter0 = new AdressAdapter0(context, adressUser.mlist);
                adressAdapter0.setinterface(this);
                adressAdapter0.setSelectItem(0);
                mlistview1.setAdapter(adressAdapter0);
            }
            {
                addbt=view.findViewById(R.id.buttonadd_iv);
                addbt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AdressModel adressModel=new AdressModel();
                        EditAdrDialog editAdrDialog=new EditAdrDialog();
                        editAdrDialog.setadrss(adressModel);
                        editAdrDialog.setname("添加收货地址");
                        editAdrDialog.onCreate(adressDialog.getContext());
                        editAdrDialog.setinterface(new EditAdrDialog.EditAdressinterface() {
                            @Override
                            public void setedit(AdressModel adressModel) {
                                onnetadd(adressModel);// comdlg  \\
                            }
                        });
                        editAdrDialog.show();
                    }
                });
                okbt=view.findViewById(R.id.buttonok);
                okbt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        adressDialog.dismiss();
                        if(adressDialoginterface!=null)
                        {
                            adressDialoginterface.onsel(0,0);
                        }
                    }
                });
                editbt=view.findViewById(R.id.button_edit);
                editbt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectItem<0)
                        {
                            return;
                        }
                        if(adressUser.mlist.size()<=selectItem)
                        {
                            return;
                        }
                        editadress(selectItem);
                    }
                });
                deltbt=view.findViewById(R.id.buttondelt);
                deltbt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        AdressModel adressModel=adressUser.mlist.get(selectItem);
                        if(selectItem<0)
                        {
                            return;
                        }
                        if(adressUser.mlist.size()<=selectItem)
                        {
                            return;
                        }
                        delAdress(selectItem);
                    }
                });
            }
        }
    }

    private void onnetadd(AdressModel adressModel) {
//        adressUser.mlist.add(adressModel);
//        adressAdapter0.notifyDataSetChanged();//addAdress
          tmpadress=adressModel;
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
//                    ToastManager.showToast(context, superiorModel.getMsg()+superiorModel.getState());
                    if(superiorModel.getState()==200)
                    {//tmpadress
                        onreshadress();
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
//                ToastManager.showToast(context, "onEr");
            }
        };
        AdressInfor user=new AdressInfor();
        user.setaccountId(BaseApplication.getInstance().getAccountId());//getSubordinate
        user.setaddress(adressModel.getaddress());
        user.setmobile(adressModel.getmobile());
        user.setname(adressModel.getname());
        NetWorks.getInstance().addAdress(context,user,observerx);
    }

    private void onreshadress() {
        MyObserver<SelAdressModel> observerx= new MyObserver<SelAdressModel>() {
            @Override
            public void onNext(SelAdressModel superiorModel) {

            }
            @Override
            public void onError(Throwable e) {
//                ToastManager.showToast(context, "onEr");
            }
        };
//        SelAdressInfor selAdressInfor=new SelAdressInfor();
//        selAdressInfor.setaccountId(BaseApplication.getInstance().getAccountId());
//        NetWorks.getInstance().selAdress(context,selAdressInfor,observerx);
    }
    public void show() {
        adressDialog.show();
        {
            WindowManager.LayoutParams layoutParams = adressDialog.getWindow().getAttributes();
            layoutParams.gravity= Gravity.CENTER_VERTICAL;
            layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height= WindowManager.LayoutParams.MATCH_PARENT;
            adressDialog.getWindow().setAttributes(layoutParams);
        }
    }
    public void setinterface(AdressDialoginterface adressDialoginterface) {
        this.adressDialoginterface=adressDialoginterface;
    }

    public void dismiss() {
        adressDialog.dismiss();
    }

    public void setadrss(AdressUser adressUser) {
        this.adressUser=adressUser;
    }

    @Override
    public void onsel(int selectItem,int flat) {
             if(flat==0)
             {// SEL
                 adressUser.msel=selectItem;
                 this.selectItem=selectItem;
                 Message msg=new Message();
                 msg.what=flat;
                 mhandler.sendMessageDelayed(msg,100);
             }
             else if(flat==1)
             {//EDIT
//this.selectItem=selectItem;
                 this.selectItem=selectItem;
                 Message msg=new Message();
                 msg.what=flat;
                 mhandler.sendMessageDelayed(msg,100);
             }
             else if(flat==2)
             {//delt
                 this.selectItem=selectItem;
                 Message msg=new Message();
                 msg.what=flat;
                 mhandler.sendMessageDelayed(msg,100);
             }

//        {
//            Message msg=new Message();
//            msg.what=flat;
////            mhandler.sendMessage(msg);	//mhandler
//            mhandler.sendMessageDelayed(msg,100);
//        }
      //  adressAdapter0.notifyDataSetChanged();  // AdressModel adressModel=adressUser.mlist.get(selectItem);
    }

    private void editadress(int selectItem) {
//        AdressModel adressModel=adressUser.mlist.get(selectItem);
        EditAdrDialog editAdrDialog=new EditAdrDialog();
        editAdrDialog.setname("修改收货地址");
        AdressModel adressModel=new AdressModel();
        editAdrDialog.setadrss(adressModel);
        editAdrDialog.onCreate(adressDialog.getContext());
        editAdrDialog.setinterface(new EditAdrDialog.EditAdressinterface() {
            @Override
            public void setedit(AdressModel adressModel) {
//                        adressUser.mlist.add(adressModel);  //upAdress
//                         adressUser.mlist.set(selectItem,adressModel);
//                         adressAdapter0.notifyDataSetChanged();
//                         adressModel.setid(selectItem);
                upAdress(adressModel);
            }
        });
        editAdrDialog.show();
    }

    private void delAdress(int selectItem) {
//        AdressModel adressModel=adressUser.mlist.get(selectItem);
        AdressModel adressModel=new AdressModel();
        MyObserver<AdressModel> observerx= new MyObserver<AdressModel>() {
            @Override
            public void onNext(AdressModel superiorModel) {
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
                        adressUser.mlist.remove(selectItem);
                        adressAdapter0.notifyDataSetChanged();//
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
//                ToastManager.showToast(context, "onEr");
            }
        };
        DelAdressInfor user=new DelAdressInfor();//id
        user.setaccountId(BaseApplication.getInstance().getAccountId());//getSubordinate
        user.setId(adressModel.getid());
        NetWorks.getInstance().delAdress(context,user,observerx);
    }
    private void upAdress(AdressModel adressModel) {
        tmpadress=adressModel;
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
//                    ToastManager.showToast(context, superiorModel.getMsg()+superiorModel.getState());
                    if(superiorModel.getState()==200)
                    {
                          onreshadress();
//                        adressUser.mlist.set((int)tmpadress.getid(),tmpadress);
//                        adressAdapter0.notifyDataSetChanged();//
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
//                ToastManager.showToast(context, "onEr");
            }
        };
        UpAdressInfor user=new UpAdressInfor();
        user.setaccountId(BaseApplication.getInstance().getAccountId());//getSubordinate
        user.setaddress(adressModel.getaddress());
        user.setmobile(adressModel.getmobile());//UpAdressInfor
        user.setname(adressModel.getname());
//        user.setId(adressModel.getid());
        NetWorks.getInstance().upAdress(context,user,observerx);
    }
    public interface AdressDialoginterface {
        void onsel(int idex, int fvvvlat);
    }
}
