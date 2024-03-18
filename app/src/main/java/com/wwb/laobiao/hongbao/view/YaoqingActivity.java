package com.wwb.laobiao.hongbao.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wwb.laobiao.hongbao.adapter.Yaoqing;
import com.wwb.laobiao.hongbao.adapter.YaoqingAdapter;
import com.wwb.laobiao.hongbao.generateqrcode.QRCodeUtil;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BasePresenterFragActivity;
import com.yangna.lbdsp.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import butterknife.BindView;

//import com.qzy.laobiao.R;
//import com.qzy.laobiao.common.base.BasePresenterFragActivity;
//import com.qzy.laobiao.home.presenter.HomePresenter;
//import com.qzy.laobiao.hongbao.adapter.Yaoqing;
//import com.qzy.laobiao.hongbao.adapter.YaoqingAdapter;
//import com.qzy.laobiao.hongbao.generateqrcode.QRCodeUtil;


public class YaoqingActivity extends BasePresenterFragActivity<HomePresenter> {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
//    @BindView(R.id.btn_generate)
//    Button generate;
    @BindView(R.id.button_tip_iv)
     Button buttontip;
//    Bitmap qrcode_bitmap;//生成的二维码  button_tip_iv
//    @BindView(R.id.layid0)
//    LinearLayout lay0;
    @BindView(R.id.iv_top)
    RelativeLayout laytop;

    private List<Yaoqing> yaoqinglist;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_yaoqing_main;
    }
    @Override
    protected HomePresenter setPresenter() {
        //回调
        return new HomePresenter(this);
    }
    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler
    Handler handler;
    {

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        super.initView();
        reajustlay();
//        if(0==1)
        {
            yaoqinglist=new ArrayList<>();
            {
                Yaoqing yaoqing=new Yaoqing();
                yaoqing.sky=0;
                yaoqing.name="我的邀请码：666666";
                yaoqing.bmp=generateQrcodeAndDisplay(yaoqing.getyaoqing());
                yaoqinglist.add(yaoqing);
            }
            for(int i=0;i<3;i++)
              {
                  Yaoqing yaoqing=new Yaoqing();
                  yaoqing.sky=1;
                  yaoqing.name="用户ID： 123456  123xxxx9999";
                  yaoqinglist.add(yaoqing);
              }
            LinearLayoutManager layout1 = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layout1);
            YaoqingAdapter adapter = new YaoqingAdapter(yaoqinglist);
            recyclerView.setAdapter(adapter);
        }
    }
    private Bitmap generateQrcodeAndDisplay(String content) {
        // content ="https://www.baidu.com/";
        String str_width = "650";
        String str_height = "650";
        int width;
        int height;
        if (str_width.length() <= 0 || str_height.length() <= 0) {
            width = 650;
            height = 650;
        } else {
            width = Integer.parseInt(str_width);
            height = Integer.parseInt(str_height);
        }

        if (content.length() <= 0) {

            return null;
        }
        String error_correction_level="";
        String margin="";
        int color_black= Color.BLACK;
        int color_white=Color.WHITE;
        Bitmap logoBitmap=null;
        Bitmap blackBitmap=null;
        return QRCodeUtil.createQRCodeBitmap(content, width, height, "UTF-8",
                error_correction_level, margin, color_black, color_white, logoBitmap, 0.2F, blackBitmap);

    }
    private void reajustlay() {
        if(laytop.getHeight()<1)
        {
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(int i=0;i<100;i++)
                    {
                        if(laytop.getHeight()>1)
                        {
                            break;
                        }
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    buttontip.setY(laytop.getHeight()-buttontip.getHeight()/2);
                }
            });
            thread.start();
        }
        buttontip.setY(laytop.getHeight()-buttontip.getHeight()/2);
    }

}
