package com.wwb.laobiao.cangye.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wwb.laobiao.MyObservable.Teacher;
import com.wwb.laobiao.address.AddressActivity;
import com.wwb.laobiao.address.FukuanActivity;
import com.wwb.laobiao.address.bean.AdressUser;
import com.wwb.laobiao.cangye.Dialog.ShopDialog;
import com.wwb.laobiao.cangye.Dialog.ShopDialogErweima;
import com.wwb.laobiao.cangye.bean.OrderInfor;
import com.wwb.laobiao.cangye.bean.OrderModel;
import com.wwb.laobiao.hongbao.generateqrcode.ImageUtil;
import com.wwb.laobiao.instance.UserOrder;
import com.wwb.laobiao.usmsg.websocketclient.WebMainActivity;
import com.wwb.laobiao.usmsg.websocketclient.modle.ChatMessage;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.home.adapter.CommentDialogSingleAdapter;
import com.yangna.lbdsp.mall.view.GoodsActivity;

import java.util.Observable;
import java.util.Observer;

import butterknife.OnClick;

public class CanyedacongGood extends Activity {

    TextView goods_shop;
//    TextView add_cart;
    Activity context;
    private ShopDialog shopDialog;
    private AdressUser adressUser;
    private String goodid;
    private String goodsName;
    private String goodsPrice;
    private String pictureUrl;
    private ShopDialogErweima shopErweima;
    public Bitmap qrcode_bitmap;
    private AlertDialog.Builder erweichoiceBuilder;
    private ViewGroup.LayoutParams params;
    private ChatReceiver chatMessageReceiver;
    private BottomSheetDialog bottomSheetDialog;
    private RecyclerView rv_dialog_lists;
    private CommentDialogSingleAdapter bottomSheetAdapter;
    private float slideOffsets = 0;

    //加入购物车
//    @SuppressLint("NonConstantResourceId")
//    @OnClick(R.id.tv_add_cart)
//    public void doAddCart(View view){
//        Log.e("加入购物车", "加入购物车");
//    }

    public CanyedacongGood(GoodsActivity goodsActivity) {
        Controller controller = new Controller();
        controller.update(goodsActivity.getBaseContext());
        context = goodsActivity;
        adressUser = new AdressUser();
//        goods_shop = goodsActivity.findViewById(R.id.iv_goods_shop);
        goods_shop.setText("购买商品");
        goods_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                  showListDialog();
//                if (BaseApplication.getInstance().getPromotionCode() == null) {
//                    showtip("你没有输入邀请码，不可以购买该商品");
//                    return;
//                }
                UserOrder.getInstance().goodid=goodid;
                UserOrder.getInstance().goodsName=goodsName;
                UserOrder.getInstance().goodsPrice=goodsPrice;
                UserOrder.getInstance().pictureUrl=pictureUrl;

                Intent intent = new Intent(context, FukuanActivity.class);
                intent.putExtra("goodid", goodid);
                intent.putExtra("goodsName", goodsName);
                intent.putExtra("goodsPrice", goodsPrice);
                intent.putExtra("pictureUrl", pictureUrl);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivityForResult(intent, 0);
            }
        });
//        doRegisterReceiver();
        /*加入购物车*/
//        add_cart = goodsActivity.findViewById(R.id.tv_add_cart);
//        add_cart.setText("加入购物车");
//        add_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("加入购物车", "加入购物车");
//                if (bottomSheetDialog != null) {
//                    bottomSheetDialog.show();
////            mPresenter.getComments(getActivity(), videoId);
//                    //开始把拿到的规格列表显示出来
//                    return;
//                }
//                view = View.inflate(BaseApplication.getContext(), R.layout.dialog_add_cart, null);
//                ImageView iv_dialog_close = (ImageView) view.findViewById(R.id.dialog_add_cart_iv_close);
//                rv_dialog_lists = (RecyclerView) view.findViewById(R.id.dialog_add_cart_rv_list);
//                RelativeLayout rl_comment = view.findViewById(R.id.rl_add_cart);
//
//                iv_dialog_close.setOnClickListener(v -> bottomSheetDialog.dismiss());
//
//                rl_comment.setOnClickListener(v -> {
////            initInputTextMsgDialog(null, false, null, -1);
//                });
////        mPresenter.getComments(getActivity(), videoId);
//                //开始把拿到的规格列表显示出来
////        bottomSheetAdapter = new CommentDialogSingleAdapter(BaseApplication.getContext(), this);
////        bottomSheetAdapter.setNewData(data);
//                rv_dialog_lists.setHasFixedSize(true);
//                rv_dialog_lists.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext()));
//                rv_dialog_lists.setItemAnimator(new DefaultItemAnimator());
////        bottomSheetAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
////            @Override
////            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
////                int id = view.getId();
////                switch (id) {
////                    case R.id.rl_group:
////                        ToastUtil.showToast("点击了rl_group");
////                        break;
////                    case R.id.ll_like:
////                        ToastUtil.showToast("点击了ll_like");
////                        break;
////
////                }
////            }
////        });
////        bottomSheetAdapter.setLoadMoreView(new SimpleLoadMoreView());
////        bottomSheetAdapter.setOnLoadMoreListener(this, rv_dialog_lists);
////        rv_dialog_lists.setAdapter(bottomSheetAdapter);
//
////        initListener();// 点击事件
//
//                bottomSheetDialog = new BottomSheetDialog(BaseApplication.getContext(), R.style.dialog);
//                bottomSheetDialog.setContentView(view);
//                bottomSheetDialog.setCanceledOnTouchOutside(true);
//                final BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
//                mDialogBehavior.setPeekHeight(getWindowHeight());
//                mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//                    @Override
//                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
////                    bottomSheetDialog.dismiss();
//                            mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                        } else if (newState == BottomSheetBehavior.STATE_SETTLING) {
//                            if (slideOffsets <= -0.28) {
//                                bottomSheetDialog.dismiss();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                        slideOffsets = slideOffset;
//
//                    }
//                });
//            }
//        });
    }

    private int getWindowHeight() {
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    private void showListDialog() {
        shopDialog = new ShopDialog();
        shopDialog.setname(goodsName);
        shopDialog.setgoodsPrice(goodsPrice);//pictureUrl
        shopDialog.setpictureUrl(pictureUrl);//pictureUrl
        shopDialog.setinterface(new ShopDialog.ShopDialoginterface() {
            @Override
            public void onsel(int idex) {
                if (idex == 1) {
                    Onshop();//
                } else if (idex == 2) {
//                    Intent intent = new Intent();
//                    intent.setClass(context, AddressActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Bundle bundle=new Bundle();
////                  bundle.putSerializable(AdressUser.SER_KEY, adressUser);
//                    context.startActivity(intent);
//                    Intent intent = new Intent();
//                    intent.setAction("com.xch.ChatReceiver.content");
//                    sendBroadcast(intent);
                } else {
                    showtip("你取消了购买商品");
                }

            }
        });
        shopDialog.Oncreate(context);
        shopDialog.Onshow();
//        observerupdata();
    }

    private void observerupdata() {
        Observer Observer = new Observer() {
            @Override
            public void update(Observable o, Object data) {
                if (data instanceof String) {
                    shopDialog.onresh();
                }
            }
        };
        Teacher.getInstance().deleteObservers();
        Teacher.getInstance().addObserver(Observer);
    }

    private void ShowErweimadlg() {
        shopErweima = new ShopDialogErweima();
        shopErweima.qrcode_bitmap = shopDialog.qrcode_bitmap;
        shopErweima.setinterface(new ShopDialog.ShopDialoginterface() {
            @Override
            public void onsel(int idex) {
                showtip("你已经了购买商品");//
            }
        });
        shopErweima.Oncreate(shopDialog.comdlg.getOwnerActivity());
    }

    public void Onshop() {
        MyObserver<OrderModel> observerx = new MyObserver<OrderModel>() {
            @Override
            public void onNext(OrderModel superiorModel) {
                if (superiorModel == null) {
                    ToastManager.showToast(context, "null");
                    return;//
                } else {
                    showerweima("你已经了购买商品");//
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, "onEr");
            }
        };
        OrderInfor user = new OrderInfor();
//        user.setaccountId(BaseApplication.getInstance().getAccountId());//getSubordinate
        {
            int gv = 0;
            try {
                gv = Integer.valueOf(goodid);
            } catch (Exception e) {
                // TODO: handle exception
            }
            user.setgoodsId(goodid);//user
        }
        user.setadressId(shopDialog.adressUser.getselId());//amount
        user.setamount(shopDialog.adressUser.getamount());//goodid
//        shopDialog.adressUser.getselId();
        if (shopDialog.adressUser.id < 0) {
            showtip("请输入购买地址");
            return;
        }
        if (shopDialog.adressUser.getamount() < 1) {
            showtip("请输入商品数量大于0");
            return;
        }
        NetWorks.getInstance().CreateOrder(context, user, observerx);
    }

    private void showtip(String strtip) {
        ToastUtil.showToast(strtip);
        AlertDialog.Builder choiceBuilder = new AlertDialog.Builder(context);
        choiceBuilder.setCancelable(false);
        choiceBuilder
                .setTitle(strtip)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        choiceBuilder.create();
        choiceBuilder.show();
    }

    @SuppressLint("ResourceType")
    private void showerweima(String strtip) {

        qrcode_bitmap = shopDialog.qrcode_bitmap;
        if (qrcode_bitmap == null) {
            showtip("二维码加载有误");
            return;
        }
        ToastUtil.showToast(strtip);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_erweima, null);
        ImageView imageView = view.findViewById(R.id.imageViewshop);//shopDialog.qrcode_bitmap;
        imageView.setImageBitmap(shopDialog.qrcode_bitmap);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imgChooseDialog();
                return false;
            }
        });
//        Button btok =view.findViewById(R.id.iv_ok);//shopDialog.qrcode_bitmap;
//        btok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        erweichoiceBuilder = new AlertDialog.Builder(context);
        erweichoiceBuilder.setView(view);
        erweichoiceBuilder.setCancelable(false);
        erweichoiceBuilder.setTitle("商家付款二维码");
        erweichoiceBuilder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        erweichoiceBuilder.create();
        erweichoiceBuilder.show();
    }

    /**
     * 长按二维码图片弹出选择框（保存或分享）
     */
    private void imgChooseDialog() {
        if (qrcode_bitmap == null) {
            showtip("二维码加载有误");
            return;
        }
        AlertDialog.Builder choiceBuilder = new AlertDialog.Builder(erweichoiceBuilder.getContext());
        choiceBuilder.setCancelable(false);
        choiceBuilder
                .setTitle("选择")
                .setSingleChoiceItems(new String[]{"存储至手机", "分享"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (qrcode_bitmap == null) {
                                    showtip("二维码加载有误");
                                    dialog.dismiss();
                                    return;
                                }
                                switch (which) {
                                    case 0://存储
                                        saveImg(qrcode_bitmap);
                                        break;
                                    case 1:// 分享
                                        shareImg(qrcode_bitmap);
                                        break;
                                    default:
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        choiceBuilder.create();
        choiceBuilder.show();
    }

    /**
     * 保存图片至本地
     *
     * @param bitmap
     */
    private void saveImg(Bitmap bitmap) {
        String fileName = "qr_" + System.currentTimeMillis() + ".jpg";
        boolean isSaveSuccess = ImageUtil.saveImageToGallery(erweichoiceBuilder.getContext(), bitmap, fileName);
        if (isSaveSuccess) {
            Toast.makeText(context, "图片已保存至本地", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "保存图片失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享图片(直接将bitamp转换为Uri)
     *
     * @param bitmap
     */
    private void shareImg(Bitmap bitmap) {
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(erweichoiceBuilder.getContext().getContentResolver(), bitmap, null, null));
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");//设置分享内容的类型
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent = Intent.createChooser(intent, "分享");
        erweichoiceBuilder.getContext().startActivity(intent);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if(requestCode==0)
//            {
//                Bundle bundle = data.getExtras();
//                if(bundle!=null)
//                {//adressUser.amount
//                    adressUser= (AdressUser) bundle.getSerializable(AdressUser.SER_KEY);
//                    shopDialog.setaddr(adressUser);
//                }
//
//            }
//        }
    }

    public void setid(String id) {
        goodid = id;
    }

    public void setname(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public void setpictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setLayoutParams(ViewGroup.LayoutParams params) {
        this.params = params;
    }

    private class ChatReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            if (message != null) {
//                Intent intent = new Intent();
                intent.setClass(context, AddressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
//                  bundle.putSerializable(AdressUser.SER_KEY, adressUser);
                context.startActivity(intent);
            }
        }
    }
}
