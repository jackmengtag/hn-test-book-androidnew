package com.wwb.laobiao.address;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wwb.laobiao.address.adp.UserOrderAdapter;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.AdressUser;
import com.wwb.laobiao.address.bean.Ordercreate;
import com.wwb.laobiao.address.model.AmountView;
import com.wwb.laobiao.address.model.LinearLayoutZhifu;
import com.wwb.laobiao.cangye.bean.OrderInfor;
import com.wwb.laobiao.cangye.bean.OrderModel;
import com.wwb.laobiao.cangye.bean.PayInfor;
import com.wwb.laobiao.cangye.bean.PayModel;
import com.wwb.laobiao.cangye.model.Controller;
import com.wwb.laobiao.hongbao.generateqrcode.ImageUtil;
import com.wwb.laobiao.instance.UserOrder;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.manager.BannerGlideImageLoader;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.wxapi.WXPayEntryActivity;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import static com.yangna.lbdsp.wxapi.util.WeiXinConstants.APP_ID;

//import com.tencent.mm.opensdk.utils.Log;


public class FukuanActivity1 extends AppCompatActivity {
    private com.youth.banner.Banner banner;
    private String goodid;
    private String goodsName = "";
    private String goodsPrice = "";
    private String pictureUrl = "";
    private LinearLayout mlayadr;
    private LinearLayout mlaybottom;
//    private LinearLayout mlayAmount;
    private ImageView mback;
    private TextView txgoadress;
	private TextView txgoodsName;
	private TextView txgoodsPrice;
	private TextView txgoodsnum;
//    android:background="@drawable/bt_5_background"
	private TextView txname;
	private TextView txmobile;
	private TextView txadress;
    private String ErweimaUrl="http://www.lbdsp.com/code/lbdspsk.png";
    private ArrayList<String> banner_list = new ArrayList<String>();
    public AdressUser adressUser= UserOrder.getInstance().adressUser;
    Activity context;
    private LinearLayoutZhifu linearLayoutZhifu;
    private AlertDialog alert2;
    public Bitmap qrcode_bitmap;
    private AlertDialog.Builder erweichoiceBuilder;
    private Float fPrice;
    List<Ordercreate>ordercreateList=new ArrayList<>();

    private RecyclerView recyclerView0;
    private UserOrderAdapter adapter0;
    private AmountView amountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fukuan);

//        LinearLayout madrlay=findViewById(R.id.lay_sel_0_iv);
//        madrlay.setVisibility(View.GONE);
        context=this;

        recyclerView0=findViewById(R.id.RecyclerView_0);
        mback =findViewById(R.id.imageViewhd);

        txgoadress =findViewById(R.id.textView_adress);
        txname =findViewById(R.id.textView_00);
        txmobile =findViewById(R.id.textView_1);
        txadress =findViewById(R.id.textView_2);
        txgoodsName =findViewById(R.id.textView_goodsName);
		txgoodsPrice =findViewById(R.id.textView_goodsPrice);
		txgoodsnum =findViewById(R.id.textView_goodsnum);
		mlayadr =findViewById(R.id.idexkong);
		amountView=findViewById(R.id.amount_view);
        mlaybottom =findViewById(R.id.layout_bottom);
		banner =findViewById(R.id.goods_banner);
        mlayadr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                if(adressUser==null)
                {
                    adressUser=new AdressUser();
                }
                Intent intent=new Intent(context, UserAdaddressActivity.class);
                startActivityForResult(intent,0);
            }
        });
        amountView.setGoods_storage(100);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(adressUser==null)
        {
            adressUser=new AdressUser();
        }
//        mlaybottom.removeAllViews();
//        linearLayoutZhifu=new LinearLayoutZhifu(this);
//        linearLayoutZhifu.setinterface(new LinearLayoutZhifu.LinearLayoutZhifuinterface() {
//            @Override
//            public void onok() {
//                Onshop();
////              onBackPressed();
//            }
//        });
//        linearLayoutZhifu.setinterface();
//        mlaybottom.addView(linearLayoutZhifu);
//        <LinearLayout
//        android:layout_width="wrap_content"
//        android:id="@+id/layamount"
        activitytransfer();
        initrecy();
//        Onshow();
    }
    private void initrecy() {
//
//        recyclerView0.setVisibility(View.GONE);
        LinearLayoutManager layout1 = new LinearLayoutManager(this);
        layout1.setOrientation(RecyclerView.VERTICAL);
        recyclerView0.setLayoutManager(layout1);
        adapter0 = new UserOrderAdapter(ordercreateList);
        recyclerView0.setAdapter(adapter0);
//        adapter0.setInterface(this);
    }

    private void onsetsel() {
//        int sel=adressUser.msel;
//        if(sel<0)
//        {
//            adressUser.id=-1;
//        }
        AdressModel mmb = UserOrder.getInstance().getseladress();
        adressUser.id=mmb.getid();
        adressUser.amount=getval(txgoodsnum.getText().toString());
//        adressUser.setsel(sel);
//        if(adressUser.mlist.size()<1)
//        {
//            adressUser.id=-1;
//        }
    }
    private int getval(String toString) {
        try {
            return  Integer.valueOf(toString);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }
    private void activitytransfer() {
//        Utilswwb utilswwb=new Utilswwb();
//        utilswwb.setcb(new Utilswwb.Utilswwbinterface() {
//            @Override
//            public void setback(Bitmap bitmap) {
//                qrcode_bitmap=bitmap;
//            }
//        });
//        utilswwb.setImageURL(ErweimaUrl);
        Intent intent = getIntent();
//        bundle.putString("goodid",goodid);
//        bundle.putString("goodsName",goodsName);
//        bundle.putString("goodsPrice",goodsPrice);
//        bundle.putString("pictureUrl",pictureUrl);
        goodid = intent.getStringExtra("goodid");//
        goodsName = intent.getStringExtra("goodsName");//
        goodsPrice = intent.getStringExtra("goodsPrice");//
        pictureUrl = intent.getStringExtra("pictureUrl");//
        {
            banner_list.add(pictureUrl);
            banner.setBannerStyle(BannerConfig.RIGHT);
            banner.setImageLoader(new BannerGlideImageLoader()).setImages(banner_list).start();
            banner.stopAutoPlay();
        }
        txgoodsName.setText(goodsName);
        txgoodsPrice.setText(goodsPrice);
        try {
            fPrice=Float.valueOf(goodsPrice);
        } catch (Exception e) {
            // TODO: handle exception
        }
//        int UserLevel=BaseApplication.getInstance().getUserLevel()+1;
        int UserLevel=1;
        txgoodsnum.setText(""+UserLevel);
        linearLayoutZhifu.setzongji(UserLevel,goodsPrice);
        txgoodsPrice.setText(linearLayoutZhifu.getzongji());
//        selAdress();
        {
            Ordercreate ordercreate=new Ordercreate();
            ordercreate.goodid=goodid;
            ordercreate.goodsName=goodsName;
            ordercreate.goodsPrice=goodsPrice;
            ordercreate.pictureUrl=pictureUrl;
            ordercreateList.add(ordercreate);
        }
        {
            Ordercreate ordercreate=new Ordercreate();
            ordercreate.goodid=goodid;
            ordercreate.goodsName=goodsName;
            ordercreate.goodsPrice=goodsPrice;
            ordercreate.pictureUrl=pictureUrl;
            ordercreateList.add(ordercreate);
        }
    }

    private void setmap() {
//        int UserLevel=BaseApplication.getInstance().getUserLevel()+1;
        int UserLevel=1;
        txgoodsnum.setText(""+ UserLevel);
        linearLayoutZhifu.setzongji(UserLevel,goodsPrice);
        txgoodsPrice.setText(linearLayoutZhifu.getzongji());

        if(adressUser.mlist.size()<1)
        {
            txname.setText("请输入用户名");
            txmobile.setText("请输入电话号");
            txadress.setText("请输入地址");
            return;
        }
//        AdressModel madr = adressUser.mlist.get(adressUser.msel);
        AdressModel madr =UserOrder.getInstance().getseladress();
		txname.setText(madr.getname());
        txmobile.setText(madr.getmobile());
//        txadress.setText(madr.getaddress());// UserOrder.getInstance().pictureUrl=pictureUrl;

        txadress.setText(String.format("%s %s %s %s",UserOrder.getInstance().getprovincename(madr.ProvinceId),
                UserOrder.getInstance().getcityname(madr.CityId),
                UserOrder.getInstance().getarename(madr.Areid)
                ,madr.getaddress()));
    }
    public void Onshow() {
//        imgChooseDialog();  //AddressActivity   \\
        showtip("进入购买");
        if(BaseApplication.getInstance().getAccountId()<0)
        {
            Controller Controller=new Controller();
            Controller.setinterface(new com.wwb.laobiao.cangye.model.Controller.Controllerinterface() {
                @Override
                public void onsel(Context context, int idxe) {
                    if(idxe==2)
                    {
                        alert2.setTitle("加载有错");
                    }
                    StartselAdress();
                }
            });
            Controller.reinit(context);
        }
        else
        {
            StartselAdress();
        }

    }
    private void StartselAdress() {
//        MyObserver<SelAdressModel> observerx= new MyObserver<SelAdressModel>() {
//            @Override
//            public void onNext(SelAdressModel superiorModel) {
//                if(superiorModel==null)
//                {
//                    ToastManager.showToast(context, "null");
//                    alert2.setTitle(" null");//设置对话框的标题
//                    return;
//                }
//                else
//                {
////                    ToastManager.showToast(context, superiorModel.getMsg()+superiorModel.getState());
//                    if(superiorModel.getState()==200)
//                    {//tmpadress
//                        if(superiorModel.body==null)
//                        {
//                            alert2.setTitle("body null");//设置对话框的标题
//                            return;
//
//                        }
//                        adressUser.mlist.clear();
//                        for(int i=0;i<superiorModel.getbodysize();i++)
//                        {
//                            SelAdressModel.Recbody tmppsx = superiorModel.body.get(i);
//                            AdressModel tmp=new AdressModel();
//                            tmp.setname(tmppsx.getname());
//                            tmp.setmobile(tmppsx.getmobile());
//                            tmp.setaddress(tmppsx.getaddress());
//                            tmp.setid(tmppsx.getid());
//                            adressUser.mlist.add(tmppsx);
//                        }
//                        setmap();
//
//                        alert2.dismiss();
//                    }
//
//                }
//            }
//            @Override
//            public void onError(Throwable e) {
//                ToastManager.showToast(context, "onEr");
//                alert2.setTitle("onEr");//设置对话框的标题
//            }
//        };
//        SelAdressInfor selAdressInfor=new SelAdressInfor();
//        selAdressInfor.setaccountId(BaseApplication.getInstance().getAccountId());
//        NetWorks.getInstance().selAdress(context,selAdressInfor,observerx);
         UserOrder.getInstance().selAdress(context, new UserOrder.Orderinterface() {
            @Override
            public void onresh(int idex) {
                alert2.dismiss();
                setmap();
            }
        });
    }
    private void showtip(String strtip) {
        ToastUtil.showToast(strtip);
        alert2 = new AlertDialog.Builder(context).create();
        alert2.setIcon(R.mipmap.ic_launcher);//设置对话框的图标
        alert2.setTitle(strtip);//设置对话框的标题
        alert2.setCancelable(false);
//                            alert.setMessage("");//设置要显示的内容
        //添加确定按钮
        alert2.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();//结束页面
            }
        });
        alert2.show();// 创建对话框并显示
    }
    private void showerweima(String strtip) {

        if(qrcode_bitmap==null)
        {
            showtip("二维码加载有误");
            return;
        }
//        ToastUtil.showToast(strtip);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_erweima, null);
        ImageView imageView =view.findViewById(R.id.imageViewshop);//shopDialog.qrcode_bitmap;
        imageView.setImageBitmap(qrcode_bitmap);
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
                onBackPressed();
            }
        });
        erweichoiceBuilder.create();
        erweichoiceBuilder.show();
    }
    /**
     * 长按二维码图片弹出选择框（保存或分享）
     */
    private void imgChooseDialog(){
        if(qrcode_bitmap==null)
        {
            showtip("二维码加载有误");
            return;
        }
        AlertDialog.Builder choiceBuilder = new AlertDialog.Builder(context);
        choiceBuilder.setCancelable(false);
        choiceBuilder
                .setTitle("选择")
                .setSingleChoiceItems(new String[]{"存储至手机", "分享"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(qrcode_bitmap==null)
                                {
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
     * 长按二维码图片弹出选择框（保存或分享）
     */
    private void payDialog(OrderModel.OrderBody body){

        UserOrder.getInstance().createTime=body.createTime;
        UserOrder.getInstance().deliverFee=body.deliverFee;
        UserOrder.getInstance().discountAmount=body.discountAmount;
        UserOrder.getInstance().realPayAmount=body.realPayAmount;

        onpay(body);
//        androidx.appcompat.app.AlertDialog.Builder choiceBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
//        choiceBuilder.setCancelable(false);
//        choiceBuilder
//                .setTitle("去支付")
//                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        onpay(body);
//                    }
//                });
//             choiceBuilder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//        choiceBuilder.create();
//        choiceBuilder.show();
    }
    /**
     * 保存图片至本地
     * @param bitmap
     */
    private void saveImg(Bitmap bitmap){
        String fileName = "qr_"+System.currentTimeMillis() + ".jpg";
        boolean isSaveSuccess = ImageUtil.saveImageToGallery(getApplicationContext(), bitmap,fileName);
        if (isSaveSuccess) {
            Toast.makeText(context, "图片已保存至本地", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "保存图片失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 分享图片(直接将bitamp转换为Uri)
     * @param bitmap
     */
    private void shareImg(Bitmap bitmap){
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");//设置分享内容的类型
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent = Intent.createChooser(intent, "分享");
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Bundle bundle = data.getBundleExtra("bundle");
//                data.putExtra("bundle",bundle);
//        adressUser= (AdressUser) bundle.getSerializable(AdressUser.SER_KEY);
//        if(adressUser==null)
//        {
//            adressUser=new AdressUser();
//        }
//        selAdress();
        setmap();
    }
    public void Onshop() {

        if(adressUser.mlist.size()<1)
        {
            showtip("请输入购买地址");
            return;
        }
        onsetsel();
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
//                    superiorModel.getBody().appId
//                    ToastUtil.showToast(superiorModel.getMsg());//wxb066fdba033fba34
                    if(superiorModel.getState()==200)
                    {

                        payDialog(superiorModel.getBody());
                    }
                    else
                    {
//                       ToastUtil.showToast(superiorModel.getMsg());
                        Toast.makeText(FukuanActivity1.this, superiorModel.getMsg(), Toast.LENGTH_LONG).show();
                    }

                }
            }
            @Override
            public void onError(Throwable e) {
                ToastManager.showToast(context, "onEr");
            }
        };
        OrderInfor user=new OrderInfor();
//        user.setaccountId(BaseApplication.getInstance().getAccountId());//getSubordinate
        user.setgoodsId(goodid);//user
        Log.v("goodid:=======","...."+goodid);
        user.setadressId(adressUser.getselId());//amount
        user.setamount(adressUser.getamount());//goodid
        user.setChannel("02");
        user.setMoney(String.format("%4.2f",fPrice));
//        shopDialog.adressUser.getselId();
        if(adressUser.id<0)
        {
            showtip("请输入购买地址");
            return;
        }
        if(adressUser.getamount()<1)
        {
            showtip("请输入商品数量大于0");
            return;
        }
        NetWorks.getInstance().CreateOrder(context,user,observerx);
    }

    private void onpay(OrderModel.OrderBody body) {
        {

            IWXAPI wxapi = WXAPIFactory.createWXAPI(this, APP_ID,false);
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
//                        req.appId ="wx35e432b6a10cde0f";//
                            req.partnerId =  superiorModel.getBody().partnerId;
                            req.prepayId =  superiorModel.getBody().prepayId;//wx14084951483143a08538978f1409630000
//                        req.prepayId =  "wx14084951483143a08538978f1409630000";//
                            req.packageValue = superiorModel.getBody().packageStr;
                            req.nonceStr =  superiorModel.getBody().nonceStr;
                            req.timeStamp =  superiorModel.getBody().timeStamp;
//                       req.extData =  superiorModel.getBody().;
                            req.sign = superiorModel.getBody().appSign;//a1bec05ab8b5d0b921f63949e0454b18
//                         req.sign ="b4f4a9460e1b92bad7a45c2571fc3131";//   \\CEB9459C0A4F903A7B25F4DD444226AC
//                        req.extData = "3333333323232355";sendResp sendReq
//                        req.sign ="CEB9459C0A4F903A7B25F4DD444226AC";
//                        wxapi.openWXApp();
//                           int result= WeiXinPay.getInstance(context).startWXPay(req);
//                            Log.d("wxapi:",req.sign);
                            WXPayEntryActivity.orderNo=superiorModel.getBody().orderNo;
                            WXPayEntryActivity.money=superiorModel.getBody().money;
                            boolean result = wxapi.sendReq(req);
                            Log.v("支付状态:=======","支付成功....");
                            Log.v("支付结果:=======",result+"");
                            Toast.makeText(FukuanActivity1.this, superiorModel.getMsg()+" 调起支付结果:" +result, Toast.LENGTH_LONG).show();
                        }
                        else
                        {
//                       ToastUtil.showToast(superiorModel.getMsg());
                          Toast.makeText(FukuanActivity1.this, superiorModel.getMsg(), Toast.LENGTH_LONG).show();
                        }

                    }
                }
                @Override
                public void onError(Throwable e) {
                    ToastManager.showToast(context, "onEr");
                }
            };
            PayInfor user=new PayInfor();
//            user.setOrderNo(body.orderSn);
            user.setChannel(String.format("%02d",body.payType));
            user.setMoney(String.format("%4.2f",fPrice));
            NetWorks.getInstance().PayOrder(context,user,observerx);
        }
    }
//    private void debuge() {
//        goodid="1";
//    }
}
