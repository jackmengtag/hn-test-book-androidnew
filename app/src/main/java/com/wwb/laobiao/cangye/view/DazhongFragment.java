package com.wwb.laobiao.cangye.view;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wwb.laobiao.MyObservable.Teacher;
import com.wwb.laobiao.cangye.bean.SuperiorInfor;
import com.wwb.laobiao.cangye.bean.SuperiorModel;
import com.wwb.laobiao.cangye.bean.UserUpgradeModel;
import com.wwb.laobiao.common.Utilswwb;
import com.wwb.laobiao.usmsg.websocketclient.WebMainActivity;
import com.wwb.laobiao.usmsg.websocketclient.bean.ChatUser;
import com.yangna.lbdsp.MainActivity;
import com.yangna.lbdsp.PublishActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.utils.UriTool;
import com.yangna.lbdsp.home.view.HomeFragment;
import com.yangna.lbdsp.login.model.UserInfoModel;
import com.yangna.lbdsp.mall.view.ModifyStoreNamePictureActivity;
import com.yangna.lbdsp.mine.model.BasePageRequestParam;
import com.yangna.lbdsp.msg.model.XiaoxiLvModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;

/**
 *
 * 消息fragment
 */
public class DazhongFragment extends BaseDazhongFragment {
    @BindView(R.id.iv_ok)
    Button buttonok;
    @BindView(R.id.iv_copy)
    TextView codecopy;
    @BindView(R.id.name_vs)
    TextView nameid;
    @BindView(R.id.iv_code_vs)
    TextView TextpromotionCode;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;//
    @BindView(R.id.imageView_erweima)
    ImageView imageViewerweima;//imageView_erweima
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;//imageView_erweima
    private ArrayList<XiaoxiLvModel> mlist;
    private boolean rfusid;
    private AlertDialog alert2;
    private Bitmap qrcode_bitmap;
    private long firstPressedTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_dazhong;
    }

    @Override
    protected void initView() {
        super.initView();
        rfusid=false;
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIManager.switcher(context, WebMainActivity.class);
//                onstartwebchat();
//                onjump();
//                showListDialog();
                  selectVideo();
//                 Onshop();
            }
        });
        recyclerView.setBackgroundColor(Color.BLACK);//fruit_item
        more.setText(HigherXingStr);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                morefensi();
                regetusid();
            }
        });
        codecopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  promotionCode=TextpromotionCode.getText().toString();
                if (promotionCode.equals("推广码") || promotionCode.equals("") || promotionCode == null) {
                    ToastManager.showToast(context, "获取 推广码 失败\n" + "请联系后台客服处理");
                } else {

                    ToastManager.showToast(context, "复制 推广码" + promotionCode + " 成功");
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", promotionCode);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                }
            }
        });
        initerweima();
        Usid= BaseApplication.getInstance().getAccountId();
        if(Usid<0)
        {
//            ToastManager.showToast(context, "获取用户信息");
            regetusid();
            //imageViewerweima
        }
        else
        {
            initxingji(BaseApplication.getInstance().getUserLevel());
            ReinitCodeandId();
            reshfensi();
        }
        initxingji(BaseApplication.getInstance().getUserLevel());
        setRefreshEvent();
    }

    private void initerweima() {
        if(imageViewerweima!=null)
        {
            qrcode_bitmap=generateQrcodeAndDisplay(BaseApplication.getInstance().getPromotionCode());
            if(qrcode_bitmap!=null)
            {
                imageViewerweima.setImageBitmap(qrcode_bitmap);
            }

        }

    }
    //激活相册操作
    private void selectVideo() {
        if(agreeFragmentInterface!=null)
        {
            agreeFragmentInterface.onshow(7);
            return;
        }
    }
    public void regetusid() {
        Log.e("dazhong", "regetusid on "+rfusid);
        if(rfusid)
        {
              ToastManager.showToast(context,"正在刷新，请稍后");
            if (System.currentTimeMillis() - firstPressedTime >180000) {
                rfusid=false;
            }
        }
        else
        {
            firstPressedTime = System.currentTimeMillis();
            rfusid=true;
            {
                Observer Observer = new Observer() {
                    @Override
                    public void update(Observable o, Object data) {
                        if (data instanceof String) {
                          if(alert2!=null)
                          {
                              alert2.setTitle("刷新完成");//设置对话框的标题
                              alert2.dismiss();
                          }
                            rfusid=false;
                        }
                    }
                };
                Teacher.getInstance().deleteObservers();
                Teacher.getInstance().addObserver(Observer);
                alert2 = new AlertDialog.Builder(getContext()).create();
//                alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
                alert2.setTitle("正在刷新");//设置对话框的标题
                alert2.setCancelable(true);
                alert2.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        rfusid=false;
                    }
                });
//              alert.setMessage("");//设置要显示的内容
                //添加确定按钮
//                alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        alert2.getOwnerActivity().finish();//结束页面
//                    }
//                });
                alert2.show();// 创建对话框并显示
            }
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
                        Usid= BaseApplication.getInstance().getAccountId();
                        initxingji(BaseApplication.getInstance().getUserLevel());
                        ReinitCodeandId();
                        reshfensi();
                        rfusid=false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        rfusid=false;
                        ToastManager.showToast(context, e);//getSuperior
                        showtip("网络错误");

                    }
                }
                @Override
                public void onError(Throwable e) {
                    ToastManager.showToast(context, e);//getSuperior
                    rfusid=false;

                }
            });
        }
    }
    private void ReinitCodeandId() {
        TextpromotionCode.setText( BaseApplication.getInstance().getPromotionCode());
        nameid.setText(BaseApplication.getInstance().getAccountId()+"");
        initerweima();
    }
    private void initxingji(int idex) {
        ImageView imageView;
        for(int i=0;i<MAXXING;i++)
        {
            imageView= getxingview(i);
            if(imageView==null)
            {
                continue;
            }
            if(idex>i)
            {
                imageView.setBackgroundResource(R.drawable.y_xingxing);
            }
            else
            {
                imageView.setBackgroundResource(R.drawable.w_xingxing);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    private void setRefreshEvent() {
//        swipeRefreshLayout.setColorSchemeResources(R.color.color_link);
//        swipeRefreshLayout.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        }.start());
        if(swipeRefreshLayout!=null)
        {
//            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    regetusid();
////                    swipeRefreshLayout
//                }
//            });
            swipeRefreshLayout.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
//                    regetusid();
                }
                @Override
                public void onFinish() {
                    regetusid();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }.start());
        }

    }

    private void webchat(SuperiorModel model) {
        requestion();
        ChatUser chatUser=new ChatUser();
        chatUser.ips=""+model.getid();
        //chatUser.setinviteId(chatModel.getinviteAccountId());
        chatUser.setdecideId(model.getid());
        chatUser.tokens= BaseApplication.getInstance().getUserId();
        chatUser.settitle("我要升星");
        Bundle bundle=new Bundle();
        bundle.putSerializable(ChatUser.KEYTRACY,chatUser);
        Intent intent=new Intent(getContext(), WebMainActivity.class);
        intent.putExtra("bundle",bundle);
        startActivityForResult(intent,0);
    }

    private void onstartwebchat() {
        Usid= BaseApplication.getInstance().getAccountId();
        if(Usid<0)
        {
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
                    BaseApplication.getInstance().SetAccountId(model.getBody().getAccount().getId());
                    BaseApplication.getInstance().SetUserLevel(model.getBody().getAccount().getUserLevel());
                    Usid= BaseApplication.getInstance().getAccountId();
                    initxingji(BaseApplication.getInstance().getUserLevel());
                    reshfensi();
                    if(Usid>0)
                    {
                        onstartwebchat();
                    }

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
            MyObserver<SuperiorModel> observerx= new MyObserver<SuperiorModel>() {
                @Override
                public void onNext(SuperiorModel superiorModel) {
                    if(superiorModel==null)
                    {
//                        ToastManager.showToast(context, "null");
                        return;
                    }
                    else
                    {
//                        ToastManager.showToast(context, superiorModel.getMsg());
                    }
                    superiorModel.show();
                    webchat(superiorModel);
                }
                @Override
                public void onError(Throwable e) {
                    ToastManager.showToast(context, "onEr");
                }
            };
            SuperiorInfor user=new SuperiorInfor();
            user.setaccountId(Usid);//getSubordinate
            NetWorks.getInstance().getSuperior(context,user,observerx);
        }
    }

    private void requestion() {
        MyObserver<UserUpgradeModel> observerx= new MyObserver<UserUpgradeModel>() {
            @Override
            public void onNext(UserUpgradeModel rankingModel) {
//                ToastManager.showToast(context, "test onNext"+rankingModel.getMsg());
                if(rankingModel==null)
                {
//                    ToastManager.showToast(context, "null");
                    return;
                }
                else
                {
//                    ToastManager.showToast(context, rankingModel.getMsg());
                }
                rankingModel.show();
            }
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, "onEr");
            }
        };
        NetWorks.getInstance().getuserUpgrade(context,observerx);
        Log.e("MainActivity", "requestion");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if (3 == requestCode) {
                Intent intent = new Intent();
                intent.setClass(context, PublishActivity.class);
                Bundle bundle = new Bundle();
                Uri uri = Objects.requireNonNull(data).getData();
                bundle.putParcelable("uri", uri);
                intent.putExtras(bundle);
                startActivityForResult(intent, 689);
            }
            else  if (689 == requestCode)
            {
                Bundle bundle = data.getExtras();
                String title = (String) bundle.get("title");
                String content = (String) bundle.get("content");
                Uri uri = bundle.getParcelable("uri");
                if (uri != null ) {
                    String path = UriTool.getFilePathByUri(context,uri);
                    Log.d("path", "path==" + path);
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
                    File file = new File(path);
                    mmr.setDataSource(file.getAbsolutePath());
                    Bitmap bitmap = mmr.getFrameAtTime();//获得视频第一帧的Bitmap对象
                    String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
                    Log.d("ddd", "duration==" + duration);
                    int int_duration= Integer.parseInt(duration);
                    if (int_duration > 30000) {
                        Toast.makeText(context, "视频时长已超过30秒，请重新选择", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

            }
        }
    }



}
