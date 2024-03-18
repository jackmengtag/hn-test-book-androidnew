package com.wwb.laobiao.cangye.model;

import android.app.Activity;
import android.content.Context;

import com.wwb.laobiao.cangye.bean.ContractByaccountIdInfor;
import com.wwb.laobiao.cangye.bean.ContractByaccountIdModel;
import com.wwb.laobiao.cangye.bean.ContractInfor;
import com.wwb.laobiao.cangye.bean.ContractModel;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.login.model.UserInfoModel;
import com.yangna.lbdsp.mine.model.BasePageRequestParam;

import java.util.Map;

public class Controller {
    private static final String YONGHUXIYI ="用户协议" ;
    private static final String DAZONGCANGYE ="大众创业" ;
    private Controllerinterface Controllerinterface;

    //eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl9hY2NvdW50X2tleSI6IjI1MDUwMzcyLWFkM2MtNDNmYy1iNGQ1LTNkOTVhN2Q0MTc0NCJ9.DKVOBs79yx1zTvf0iYRBdSOda59wkJwtkXb9TKocHzycsjRRZpfgcGAzrCGoW6ZNdvaOQV1iM_I9bJUtoKQHSw
    public void Disagree(Context context) {
//        /contract/contract/insertContract
        BaseApplication.getInstance().setAgree(false);//gree()
    }
    public void agree(Context context,String title) {
//        /contract/contract/insertContract
        if(BaseApplication.getInstance().getAccountId()<0)
        {
            BasePageRequestParam requestParam = new BasePageRequestParam();
            requestParam.setCurrentPage("1");
            requestParam.setPageSize("4");
            requestParam.setToken(BaseApplication.getInstance().getUserId());
//            map.put("token", BaseApplication.getInstance().getUserId());
            NetWorks.getInstance().getMineInfo(context, requestParam, new MyObserver<UserInfoModel>() {
                @Override
                public void onNext(UserInfoModel model) {
                    try {
                        if (UrlConfig.RESULT_OK == model.getState()) {
                        } else {
                            ToastManager.showToast(context, model.getMsg());
                        }
                        BaseApplication.getInstance().SetPromotionCode(model.getBody().getAccount().getPromotionCode());
                        BaseApplication.getInstance().SetAccountId(model.getBody().getAccount().getId());
                        BaseApplication.getInstance().SetUserLevel(model.getBody().getAccount().getUserLevel());
                        reagree(context,title);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Throwable e) {
                    ToastManager.showToast(context, e);//getSuperior
                }
            });
        }
        else
        {
            reagree(context,title);
        }
    }
    public void reagree(Context context,String title) {
        MyObserver<ContractModel> observerx= new MyObserver<ContractModel>() {
            @Override
            public void onNext(ContractModel model) {
                if (UrlConfig.RESULT_OK == model.getState()) {
                } else {
                    ToastManager.showToast(context, model.getMsg());
                }
            }
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);//getSuperior
            }
        };
        ContractInfor user=new ContractInfor();
        user.setaccountId(BaseApplication.getInstance().getAccountId());
        user.settitle(title);
        NetWorks.getInstance().insertContract(context,user,observerx);

    }
    public void OnGetagree(Context context,String title) {
// /contract/contract/getContractByaccountId
        if(BaseApplication.getInstance().getAccountId()<0)
        {
//            Map<String, String> map = UrlConfig.getCommonMap();
//            map.put("token", BaseApplication.getInstance().getUserId());
            BasePageRequestParam requestParam = new BasePageRequestParam();
            requestParam.setCurrentPage("1");
            requestParam.setPageSize("4");
            requestParam.setToken(BaseApplication.getInstance().getUserId());
            NetWorks.getInstance().getMineInfo(context, requestParam, new MyObserver<UserInfoModel>() {
                @Override
                public void onNext(UserInfoModel model) {
                    try {
                        if (UrlConfig.RESULT_OK == model.getState()) {
                        } else {
                            ToastManager.showToast(context, model.getMsg());
                        }
                        BaseApplication.getInstance().SetPromotionCode(model.getBody().getAccount().getPromotionCode());
                        BaseApplication.getInstance().SetAccountId(model.getBody().getAccount().getId());
                        BaseApplication.getInstance().SetUserLevel(model.getBody().getAccount().getUserLevel());
                        OnReGetagree(context,title);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(Controllerinterface!=null)
                        {
                            Controllerinterface.onsel(context,2);//
                        }
                    }
                }
                @Override
                public void onError(Throwable e) {
                    ToastManager.showToast(context, e);//getSuperior
                    if(Controllerinterface!=null)
                    {
                        Controllerinterface.onsel(context,2);
                    }
                }
            });
        }
        else
        {
            if(BaseApplication.getInstance().getPromotionCode()==null)
            {
                if(Controllerinterface!=null)
                {
                    Controllerinterface.onsel(context,1);//
                }
            }
            else
            {
                OnReGetagree(context,title);
            }
        }
    }

    private void OnReGetagree(Context context, String title) {
        MyObserver<ContractByaccountIdModel> observerx= new MyObserver<ContractByaccountIdModel>() {
            @Override
            public void onNext(ContractByaccountIdModel model) {
                if (UrlConfig.RESULT_OK == model.getState()) {
                    BaseApplication.getInstance().setAgree(model.getbody());
                    if(Controllerinterface!=null)
                    {
                        Controllerinterface.onsel(context,1);
                    }
                } else {
                    ToastManager.showToast(context, model.getMsg());
                    if(Controllerinterface!=null)
                    {
                        Controllerinterface.onsel(context,0);
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);//getSuperior
                if(Controllerinterface!=null)
                {
                    Controllerinterface.onsel(context,2);
                }
            }
        };
        ContractByaccountIdInfor user=new ContractByaccountIdInfor();
        user.setaccountId(BaseApplication.getInstance().getAccountId());
        user.settitle(title);//
        NetWorks.getInstance().getContractByaccountId(context,user,observerx);
    }

    public void Oncontroller(Activity context,int idex) {

        if(idex==1)
        {
            OnGetagree(context,DAZONGCANGYE);
        }
        else
        {
            OnGetagree(context,YONGHUXIYI);
        }

    }

    public void agreewith(Activity context, int idex) {
        //
        if(idex==1)
        {
            agree(context,DAZONGCANGYE);
        }
        else
        {
            agree(context,YONGHUXIYI);
        }

    }

    public void setinterface(Controllerinterface Controllerinterface) {
        this.Controllerinterface=Controllerinterface;
    }

    public void reinit(Context context) {
//        Map<String, String> map = UrlConfig.getCommonMap();
//        map.put("token", BaseApplication.getInstance().getUserId());
        BasePageRequestParam requestParam = new BasePageRequestParam();
        requestParam.setCurrentPage("1");
        requestParam.setPageSize("4");
        requestParam.setToken(BaseApplication.getInstance().getUserId());
        NetWorks.getInstance().getMineInfo(context, requestParam, new MyObserver<UserInfoModel>() {
            @Override
            public void onNext(UserInfoModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                        BaseApplication.getInstance().SetPromotionCode(model.getBody().getAccount().getPromotionCode());
                        BaseApplication.getInstance().SetAccountId(model.getBody().getAccount().getId());
                        BaseApplication.getInstance().SetUserLevel(model.getBody().getAccount().getUserLevel());
                        BaseApplication.getInstance().SetAccountName(model.getBody().getAccount().getAccountName());
                        if(Controllerinterface!=null)
                        {
                            Controllerinterface.onsel(context,0);//
                        }
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                        if(Controllerinterface!=null)
                        {
                            Controllerinterface.onsel(context,2);//
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastManager.showToast(context, e);
                    if(Controllerinterface!=null)
                    {
                        Controllerinterface.onsel(context,2);//
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);//getSuperior
                if(Controllerinterface!=null)
                {
                    Controllerinterface.onsel(context,2);//
                }
            }
        });
    }

    public void update(Context context) {
//        Map<String, String> map = UrlConfig.getCommonMap();
//        map.put("token", BaseApplication.getInstance().getUserId());
        BasePageRequestParam requestParam = new BasePageRequestParam();
        requestParam.setCurrentPage("1");
        requestParam.setPageSize("4");
        requestParam.setToken(BaseApplication.getInstance().getUserId());
        NetWorks.getInstance().getMineInfo(context, requestParam, new MyObserver<UserInfoModel>() {
            @Override
            public void onNext(UserInfoModel model) {
                try {
                    if (UrlConfig.RESULT_OK == model.getState()) {
                    } else {
                        ToastManager.showToast(context, model.getMsg());
                    }
                    BaseApplication.getInstance().SetPromotionCode(model.getBody().getAccount().getPromotionCode());
                    BaseApplication.getInstance().SetAccountId(model.getBody().getAccount().getId());
                    BaseApplication.getInstance().SetUserLevel(model.getBody().getAccount().getUserLevel());
                    BaseApplication.getInstance().SetAccountName(model.getBody().getAccount().getAccountName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, e);//getSuperior
            }
        });
    }
    public interface Controllerinterface {
        void onsel(Context context, int idxe);
    }
}
