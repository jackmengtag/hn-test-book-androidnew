package com.yangna.lbdsp.wxapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wwb.laobiao.address.bean.AdressModel;
import com.wwb.laobiao.instance.UserOrder;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.manager.BannerGlideImageLoader;
import com.yangna.lbdsp.login.bean.User;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

public class LookorderActivity extends AppCompatActivity implements View.OnClickListener {
    private com.youth.banner.Banner banner;
    private String goodid=UserOrder.getInstance().goodid;
    private String goodsName = UserOrder.getInstance().goodsName;
    private String goodsPrice = UserOrder.getInstance().goodsPrice;
    private String pictureUrl = UserOrder.getInstance().pictureUrl;

    private TextView txshopname;
    private TextView txgoodsName;
    private TextView txgoodsPrice;
    private TextView txgoodsnum;
    private TextView txnamemobile;
    private TextView txadress;

    private TextView tx_0_1;
    private TextView tx_0_3;

    private TextView tx_1_0;
    private TextView tx_1_1;
    private TextView tx_1_2;
    private TextView tx_1_3;

    private ArrayList<String> banner_list = new ArrayList<String>();
    private int goodsnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookorder);
        //LookorderActivity
//      UserOrder.getInstance().goodid; //textView_shop_name_iv textView1
        findViewById(R.id.account_balance_back).setOnClickListener(this);
        txnamemobile =findViewById(R.id.textView1);//textView1 周生  13677894892  "
        txadress =findViewById(R.id.textView2);//"广西 柳州 柳南区 航三路 南利3-31十大大大大"\
        AdressModel mmm = UserOrder.getInstance().getseladress();
        txnamemobile.setText(String.format("%s  %s",mmm.getname(),mmm.getmobile()));
        txadress.setText(String.format("%s %s %s %s",UserOrder.getInstance().getprovincename(mmm.ProvinceId),
                UserOrder.getInstance().getcityname(mmm.CityId),
                UserOrder.getInstance().getarename(mmm.Areid)
                ,mmm.getaddress()));

        txshopname =findViewById(R.id.textView_shop_name_iv);

        txgoodsnum =findViewById(R.id.textView_goodsnum);
        txgoodsName =findViewById(R.id.textView_goodsName);
        txgoodsPrice =findViewById(R.id.textView_goodsPrice);
        banner =findViewById(R.id.goods_banner);
        if(pictureUrl!=null)
        {
            banner_list.add(pictureUrl);
            banner.setBannerStyle(BannerConfig.RIGHT);
            banner.setImageLoader(new BannerGlideImageLoader()).setImages(banner_list).start();
            banner.stopAutoPlay();
        }
        txgoodsName.setText(goodsName);
        txgoodsnum.setText(""+goodsnum);

        tx_0_1 =findViewById(R.id.textView_0_1);
        tx_0_3 =findViewById(R.id.textView_0_3);

        tx_1_0 =findViewById(R.id.textView_1_0);
        tx_1_1 =findViewById(R.id.textView_1_1);
        tx_1_2 =findViewById(R.id.textView_1_2);
        tx_1_3 =findViewById(R.id.textView_1_3);

        float fPrice=1;
        try {
            fPrice = Float.valueOf(goodsPrice);
            txgoodsPrice.setText(String.format("%4.2f",fPrice*goodsnum));
            tx_0_1.setText(String.format("%4.2f",fPrice*goodsnum));
            tx_0_3.setText(String.format("%4.2f",fPrice*goodsnum));
        } catch (Exception e) {
            // TODO: handle exception
            txgoodsPrice.setText("--");
            tx_0_1.setText("--");
            tx_0_3.setText("--");
        }
       /// 订单信息
        tx_1_0.setText(UserOrder.getInstance().orderNo);
        tx_1_1.setText(UserOrder.getInstance().createTime);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.account_balance_back:
            {
                finish();
            }
            break;

        }
    }
}