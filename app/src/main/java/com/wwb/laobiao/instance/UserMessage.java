package com.wwb.laobiao.instance;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wwb.laobiao.address.bean.AdressInfor;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.AdressUser;
import com.wwb.laobiao.address.bean.AreaInfor;
import com.wwb.laobiao.address.bean.AreaModel;
import com.wwb.laobiao.address.bean.CityInfor;
import com.wwb.laobiao.address.bean.CityModel;
import com.wwb.laobiao.address.bean.DelListAdressInfor;
import com.wwb.laobiao.address.bean.ProvinceInfor;
import com.wwb.laobiao.address.bean.ProvinceModel;
import com.wwb.laobiao.address.bean.SelAdressInfor;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.address.bean.UpAdressInfor;
import com.wwb.laobiao.cangye.bean.Fans.FansdeleteInfor;
import com.wwb.laobiao.cangye.bean.Fans.FansdeleteModel;
import com.wwb.laobiao.cangye.bean.Fans.FansinsertInfor;
import com.wwb.laobiao.cangye.bean.Fans.FansinsertModel;
import com.wwb.laobiao.cangye.bean.Fans.FansselectAllInfor;
import com.wwb.laobiao.cangye.bean.Fans.FansselectAllModel;
import com.wwb.laobiao.cangye.bean.Message.MessageFansEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageFansEreadModel;
import com.wwb.laobiao.cangye.bean.Message.MessageFansPageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageFansPageModel;
import com.wwb.laobiao.cangye.bean.Message.MessageamountInfor;
import com.wwb.laobiao.cangye.bean.Message.MessageamountModel;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentEreadModel;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentPageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagecommentPageModel;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisEreadInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisEreadModel;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisePageInfor;
import com.wwb.laobiao.cangye.bean.Message.MessagepraisePageModel;
import com.wwb.laobiao.cangye.bean.OrderInfor;
import com.wwb.laobiao.cangye.bean.OrderListInfor;
import com.wwb.laobiao.cangye.bean.OrderListModel;
import com.wwb.laobiao.cangye.bean.OrderModel;
import com.wwb.laobiao.cangye.bean.PayInfor;
import com.wwb.laobiao.cangye.bean.PayModel;
import com.wwb.laobiao.message.bean.FansDataModel;
import com.wwb.laobiao.message.bean.PinglunDataModel;
import com.wwb.laobiao.message.bean.ZanDataModel;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.wxapi.WXPayEntryActivity;

import java.util.ArrayList;
import java.util.List;

import static com.yangna.lbdsp.wxapi.util.WeiXinConstants.APP_ID;

public class UserMessage {
    public List<FansDataModel>FansDataModels;
    public List<ZanDataModel>ZanDataModels;
    public List<PinglunDataModel>PinglunDataModels;

    private UserMessage() {
        FansDataModels=new ArrayList<>();
        ZanDataModels=new ArrayList<>();
        PinglunDataModels=new ArrayList<>();
    }
    public void FansselectAllModel(Context context, FansselectAllInfor user, Messageinterface messageinterface) {
        MyObserver<FansselectAllModel> observer= new MyObserver<FansselectAllModel>() {
            @Override
            public void onNext(FansselectAllModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(67,superiorModel.getrecords());
                        }

                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(-67,null);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(messageinterface!=null)
                {
                    messageinterface.onresh(-67,null);
                }
                ToastManager.showToast(context, "onEr");
            }
        };
        user.setCurrentPage("1");
        user.setPageSize("2");
        NetWorks.getInstance().FansselectAll(context,user,observer);
    }
    public void MessageFansEread(Context context, MessageFansEreadInfor user, Messageinterface messageinterface,int key) {
        MyObserver<MessageFansEreadModel> observer= new MyObserver<MessageFansEreadModel>() {
            @Override
            public void onNext(MessageFansEreadModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(key,superiorModel.getMsg());
                        }

                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(0-key,null);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(messageinterface!=null)
                {
                    messageinterface.onresh(0-key,null);
                }
                ToastManager.showToast(context, "onEr");
            }
        };

        NetWorks.getInstance().MessageFansEread(context,user,observer);
    }
    public void MessagepraisEread(Context context, MessagepraisEreadInfor user, Messageinterface messageinterface,int key) {
        MyObserver<MessagepraisEreadModel> observer= new MyObserver<MessagepraisEreadModel>() {
            @Override
            public void onNext(MessagepraisEreadModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(key,superiorModel.getMsg());
                        }

                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(0-key,null);
                        }
                    }

                }
            }
            @Override
            public void onError(Throwable e) {
                if(messageinterface!=null)
                {
                    messageinterface.onresh(0-key,null);
                }
                ToastManager.showToast(context, "onEr");
            }
        };

        NetWorks.getInstance().MessagepraisEread(context,user,observer);
    }
    public void MessagecommentEread(Context context, MessagecommentEreadInfor user, Messageinterface messageinterface,int key) {
        MyObserver<MessagecommentEreadModel> observer= new MyObserver<MessagecommentEreadModel>() {
            @Override
            public void onNext(MessagecommentEreadModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(key,superiorModel.getMsg());
                        }

                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(0-key,null);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(messageinterface!=null)
                {
                    messageinterface.onresh(0-key,null);
                }
                ToastManager.showToast(context, "onEr");
            }
        };

        NetWorks.getInstance().MessagecommentEread(context,user,observer);
    }
    public void MessagecommentPage(Context context, MessagecommentPageInfor user, Messageinterface messageinterface,int key) {
        MyObserver<MessagecommentPageModel> observer= new MyObserver<MessagecommentPageModel>() {
            @Override
            public void onNext(MessagecommentPageModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(key,superiorModel.getrecords());
                        }

                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(0-key,null);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(messageinterface!=null)
                {
                    messageinterface.onresh(0-key,null);
                }
                ToastManager.showToast(context, "onEr");
            }
        };

        NetWorks.getInstance().MessagecommentPage(context,user,observer);
    }
    public void MessagepraisePage(Context context, MessagepraisePageInfor user, Messageinterface messageinterface,int key) {
        MyObserver<MessagepraisePageModel> observer= new MyObserver<MessagepraisePageModel>() {
            @Override
            public void onNext(MessagepraisePageModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(key,superiorModel.getrecords());
                        }

                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(0-key,null);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(messageinterface!=null)
                {
                    messageinterface.onresh(0-key,null);
                }
                ToastManager.showToast(context, "onEr");
            }
        };
        NetWorks.getInstance().MessagepraisePage(context,user,observer);
    }
    public void Fansdelete(Context context, FansdeleteInfor user, Messageinterface messageinterface, int key) {
        MyObserver<FansdeleteModel> observer= new MyObserver<FansdeleteModel>() {
            @Override
            public void onNext(FansdeleteModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(key,superiorModel.getMsg());
                        }

                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(0-key,null);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(messageinterface!=null)
                {
                    messageinterface.onresh(0-key,null);
                }
                ToastManager.showToast(context, "onEr");
            }
        };
        NetWorks.getInstance().Fansdelete(context,user,observer);
    }
    public void Fansinsert(Context context, FansinsertInfor user, Messageinterface messageinterface, int key) {
        MyObserver<FansinsertModel> observer= new MyObserver<FansinsertModel>() {
            @Override
            public void onNext(FansinsertModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(key,superiorModel.getMsg());
                        }

                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(0-key,null);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(messageinterface!=null)
                {
                    messageinterface.onresh(0-key,null);
                }
                ToastManager.showToast(context, "onEr");
            }
        };
        NetWorks.getInstance().Fansinsert(context,user,observer);
    }
    public void MessageFansPage(Context context, MessageFansPageInfor user, Messageinterface messageinterface,int key) {
        MyObserver<MessageFansPageModel> observer= new MyObserver<MessageFansPageModel>() {
            @Override
            public void onNext(MessageFansPageModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(key,superiorModel.getrecords());
                        }
                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(0-key,null);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(messageinterface!=null)
                {
                    messageinterface.onresh(0-key,null);
                }
                ToastManager.showToast(context, "onEr");
            }
        };
        NetWorks.getInstance().MessageFansPage(context,user,observer);
    }
    public void Messageamount(Context context, MessageamountInfor user, Messageinterface messageinterface, int key) {
        MyObserver<MessageamountModel> observer= new MyObserver<MessageamountModel>() {
            @Override
            public void onNext(MessageamountModel superiorModel) {
                if(superiorModel==null)
                {
                    ToastManager.showToast(context, "null");
                    return;//
                }
                else
                {
                    if(superiorModel.getState()==200)
                    {
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(key,superiorModel.body);
                        }
                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        if(messageinterface!=null)
                        {
                            messageinterface.onresh(0-key,null);
                        }
                    }


                }
            }
            @Override
            public void onError(Throwable e) {
                if(messageinterface!=null)
                {
                    messageinterface.onresh(0-key,null);
                }
                ToastManager.showToast(context, "onEr");
            }
        };
        NetWorks.getInstance().Messageamount(context,user,observer);
    }
    private static UserMessage userMessage;
    public static UserMessage getInstance() {
        if (userMessage == null) {
            synchronized (UserMessage.class) {
                if (userMessage == null) {
                    userMessage = new UserMessage();
                }
            }
        }
        return userMessage;
    }
    public interface Messageinterface {
        void onresh(int idex,Object objx);
    }
}
