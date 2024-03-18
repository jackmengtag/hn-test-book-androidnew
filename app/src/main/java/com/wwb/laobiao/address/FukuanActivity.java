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
import com.wwb.laobiao.address.adp.UserAdressAdapter;
import com.wwb.laobiao.address.adp.UserOrderAdapter;
import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.address.bean.AdressUser;
import com.wwb.laobiao.address.bean.Ordercreate;
import com.wwb.laobiao.address.bean.SelAdressInfor;
import com.wwb.laobiao.address.bean.SelAdressModel;
import com.wwb.laobiao.address.model.AmountView;
import com.wwb.laobiao.address.model.LinearLayoutZhifu;
import com.wwb.laobiao.cangye.bean.OrderInfor;
import com.wwb.laobiao.cangye.bean.OrderListInfor;
import com.wwb.laobiao.cangye.bean.OrderModel;
import com.wwb.laobiao.cangye.bean.PayInfor;
import com.wwb.laobiao.cangye.bean.PayModel;
import com.wwb.laobiao.cangye.model.Controller;
import com.wwb.laobiao.common.Utilswwb;
import com.wwb.laobiao.hongbao.generateqrcode.ImageUtil;
import com.wwb.laobiao.instance.UserOrder;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.manager.BannerGlideImageLoader;
import com.yangna.lbdsp.common.manager.ToastManager;
import com.yangna.lbdsp.common.net.MyObserver;
import com.yangna.lbdsp.common.net.NetWorks;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.mall.model.ShopPingCartModel;
import com.yangna.lbdsp.wxapi.WXPayEntryActivity;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import static com.yangna.lbdsp.wxapi.util.WeiXinConstants.APP_ID;

//import com.tencent.mm.opensdk.utils.Log;
public class FukuanActivity extends AppCompatActivity implements View.OnClickListener {
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
    private TextView txmomey;
    private String ErweimaUrl = "http://www.lbdsp.com/code/lbdspsk.png";
    private ArrayList<String> banner_list = new ArrayList<String>();
    //    public AdressUser adressUser= UserOrder.getInstance().adressUser;
    Activity context;
    private LinearLayoutZhifu linearLayoutZhifu;
    private AlertDialog alert2;
    public Bitmap qrcode_bitmap;
    private AlertDialog.Builder erweichoiceBuilder;
    private Float fPrice;
    List<Ordercreate> ordercreateList = new ArrayList<>();

    private RecyclerView recyclerView0;
    private UserOrderAdapter adapter0;
    private AmountView amountView;

    int queidngtianjiadeshuliang;
    String dianming;
    String SpecficationBeanid;
    String SpecficationBeanruleName;
    String SpecficationBeanproductPrice;
    List<ShopPingCartModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitytransfer();
        setContentView(R.layout.activity_fukuan);
        context = this;
        txmobile = findViewById(R.id.textView1);
        txadress = findViewById(R.id.textView2);
        txmomey = findViewById(R.id.textView_mmy);
        recyclerView0 = findViewById(R.id.RecyclerView_0);
        findViewById(R.id.idexkong).setOnClickListener(this);//mlayadr
        findViewById(R.id.imageViewhd).setOnClickListener(this);//back
        findViewById(R.id.textView_zhifu).setOnClickListener(this);//back

        initrecy();
        reshmomey();
        initadress();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if(requestCode==0)
        {
            AdressModel adress = UserOrder.getInstance().getseladress();
            if (adress.getaddressall() == null) {
                Intent intentx = new Intent(context, UserAdaddressActivity.class);
                startActivityForResult(intentx, 0);
                finish();
            }
            else
            {
                if(adress.getid()<0)
                {
                    Intent intentx = new Intent(context, UserAdaddressActivity.class);
                    startActivityForResult(intent, 0);
                    finish();
                    return;
                }
                txmobile.setText(adress.getname()+" "+adress.getmobile());
                txadress.setText(adress.getaddressall());
            }
        }

    }

    private void initadress() {
        if (!UserOrder.getInstance().bfAdress) {
            UserOrder.getInstance().protip("更新地址中", this);
            UserOrder.getInstance().selAdress(this, new UserOrder.Orderinterface() {
                @Override
                public void onresh(int idex) {
                    UserOrder.getInstance().distip(true);
                    AdressModel adress = UserOrder.getInstance().getseladress();
                    if(adress.getid()<0)
                    {
                        Intent intent = new Intent(context, UserAdaddressActivity.class);
                        startActivityForResult(intent, 0);
                        finish();
                        return;
                    }
                    txmobile.setText(adress.getname()+" "+adress.getmobile());
                    txadress.setText(adress.getaddressall());
                }
            });
        } else {
            AdressModel adress = UserOrder.getInstance().getseladress();
            if (adress.getaddressall() == null) {
                Intent intent = new Intent(context, UserAdaddressActivity.class);
                startActivityForResult(intent, 0);
                finish();
                return;
            }
            else
            {
                if(adress.getid()<0)
                {
                    Intent intent = new Intent(context, UserAdaddressActivity.class);
                    startActivityForResult(intent, 0);
                    finish();
                    return;
                }
                txmobile.setText(adress.getname()+" "+adress.getmobile());
                txadress.setText(adress.getaddressall());
            }

        }
    }

    private void initrecy() {
//      recyclerView0.setVisibility(View.GONE);
        LinearLayoutManager layout1 = new LinearLayoutManager(this);
        layout1.setOrientation(RecyclerView.VERTICAL);
        recyclerView0.setLayoutManager(layout1);
        adapter0 = new UserOrderAdapter(ordercreateList);
        recyclerView0.setAdapter(adapter0); //  \\
        adapter0.setInterface(new UserOrderAdapter.LineIdexInterface() {
            @Override
            public void show(int postion, String toString1) {

            }

            @Override
            public void reshval(int postion, Object object) {
                reshmomey();
            }
        });
    }

    private void reshmomey() {
        if (ordercreateList == null) {
            return;
        }
        float fv0 = 0;
        for (int i = 0; i < ordercreateList.size(); i++) {
            Ordercreate tmp0 = ordercreateList.get(i);
            fv0 += (tmp0.amount * tmp0.fPrice);
        }
        txmomey.setText(String.format("%4.2f", fv0));
    }

    private void activitytransfer() {
        Intent intent = getIntent();
        data = (List<ShopPingCartModel>) getIntent().getSerializableExtra("data");
//        intent.putExtra("guige_number", queidngtianjiadeshuliang);//传当前购买数量
        goodid = intent.getStringExtra("goodid");//
        if (goodid != null) {
            goodsName = intent.getStringExtra("goodsName");//
            goodsPrice = intent.getStringExtra("goodsPrice");//
            pictureUrl = intent.getStringExtra("pictureUrl");//

            queidngtianjiadeshuliang = intent.getIntExtra("guige_number", -1);
            dianming = intent.getStringExtra("dianming");
            SpecficationBeanid = intent.getStringExtra("guige_id");
            SpecficationBeanruleName = intent.getStringExtra("guige_ruleName");
            SpecficationBeanproductPrice = intent.getStringExtra("guige_productPrice");
            {
                Ordercreate ordercreate = new Ordercreate();
                ordercreate.goodid = goodid;
                ordercreate.goodsName = goodsName;
                ordercreate.goodsPrice = SpecficationBeanproductPrice;
                if (ordercreate.goodsPrice.equals("")) {
                    ordercreate.goodsPrice = goodsPrice;
                }
                Log.e("goodsPrice", " " + goodsPrice + " <> " + SpecficationBeanproductPrice);
                ordercreate.fPrice = UserOrder.getfloat(ordercreate.goodsPrice);
//
                ordercreate.dianming = dianming;
                ordercreate.guigeid = SpecficationBeanid;
                ordercreate.ruleName = SpecficationBeanruleName;
                ordercreate.productPrice = SpecficationBeanproductPrice;
                ordercreate.amount = queidngtianjiadeshuliang;//   ||    \\

                ordercreate.pictureUrl = pictureUrl;
                ordercreateList.add(ordercreate);
            }
        }
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                for (int a = 0; a < data.get(i).getBody().getRecords().size(); a++){
                    for (int j = 0; j < data.get(i).getBody().getRecords().get(a).getCardProduct().size(); j++) {
                        Ordercreate ordercreate = new Ordercreate();
                        ordercreate.dianming = data.get(i).getBody().getRecords().get(a).getShopName();
                        ordercreate.goodid = data.get(i).getBody().getRecords().get(a).getCardProduct().get(j).getId();
                        ordercreate.goodsName = data.get(i).getBody().getRecords().get(a).getCardProduct().get(j).getProductName();
                        ordercreate.goodsPrice = String.valueOf(data.get(i).getBody().getRecords().get(a).getCardProduct().get(j).getProductPrice());
                        ordercreate.fPrice = UserOrder.getfloat(ordercreate.goodsPrice);
                        ordercreate.guigeid = data.get(i).getBody().getRecords().get(a).getCardProduct().get(j).getProductSpecficationId();
                        ordercreate.ruleName = data.get(i).getBody().getRecords().get(a).getCardProduct().get(j).getProductSpecficationName();
                        ordercreate.productPrice = String.valueOf(data.get(i).getBody().getRecords().get(a).getCardProduct().get(j).getProductPrice());
                        ordercreate.amount = data.get(i).getBody().getRecords().get(a).getCardProduct().get(j).getProductNumber();
                        ordercreate.pictureUrl = data.get(i).getBody().getRecords().get(a).getCardProduct().get(j).getImageUrl();
                        ordercreateList.add(ordercreate);
                    }
                }

            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idexkong: {
                Intent intent = new Intent(context, UserAdaddressActivity.class);
                startActivityForResult(intent, 0);
            }
            break;
            case R.id.imageViewhd: {
                finish();
            }
            break;
            case R.id.textView_zhifu: {
                AdressModel adress = UserOrder.getInstance().getseladress();
                if (adress.getid() < 0) {
                    Intent intent = new Intent(context, UserAdaddressActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                if(ordercreateList.size()>1)
                {

                    OrderListInfor users = new OrderListInfor();
                    users.list = new ArrayList<>();
                    for (int i = 0; i < ordercreateList.size(); i++) {
                        Ordercreate tmp0 = ordercreateList.get(i);
                        OrderInfor user = new OrderInfor();
                        user.setgoodsId(tmp0.goodid);
                        user.setadressId(adress.getid());
                        user.amount = tmp0.amount;//UserOrder.getInstance().amount
                        user.setMoney(String.format("%4.2f", tmp0.fPrice * tmp0.amount));
                        user.orderDesc = "";
                        users.list.add(user);
                    }
                    UserOrder.getInstance().protip("创建订单中",context);
                    UserOrder.getInstance().oncreatpay(context, users, new UserOrder.Orderinterface() {
                        @Override
                        public void onresh(int idex) {
                            if(idex==-11)
                            {
                                ToastUtil.showToast("创建订单失败");
                            }
                            UserOrder.getInstance().distip(true);
                        }
                    });
                }
                else  if(ordercreateList.size()==1)
                {
                    Ordercreate tmp0 = ordercreateList.get(0);
                    OrderInfor user=new OrderInfor();
//        user.setaccountId(BaseApplication.getInstance().getAccountId());//getSubordinate
                    user.productSpecficationId=tmp0.guigeid;
                    user.setgoodsId(goodid);//user
                    Log.v("goodid:=======","...."+goodid);
                    user.setadressId(adress.getid());//amount
                    user.setamount(tmp0.amount);//goodid
                    user.setChannel("02");
                    user.setMoney(String.format("%4.2f", tmp0.fPrice * tmp0.amount));
                    UserOrder.getInstance().protip("创建订单中",context);
                    UserOrder.getInstance().oncreatstillpay(context, user, new UserOrder.Orderinterface() {
                        @Override
                        public void onresh(int idex) {
                            if(idex==-12)
                            {
                                ToastUtil.showToast("创建订单失败");
                            }
                            UserOrder.getInstance().distip(true);
                        }
                    });
                }
            }
            break;
        }
    }
}
