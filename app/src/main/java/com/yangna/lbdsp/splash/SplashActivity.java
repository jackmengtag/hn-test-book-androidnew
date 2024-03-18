package com.yangna.lbdsp.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.yangna.lbdsp.MainActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.UrlConfig;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.utils.SpUtils;
import com.yangna.lbdsp.login.view.AdvertisementActivity;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yangna.lbdsp.common.base.BaseApplication.BaseDisplay;
import static com.yangna.lbdsp.common.base.BaseApplication.jintiankanguoguanggao;
import static com.yangna.lbdsp.common.base.BaseApplication.shouciyunxing;

/*首次运行页面*/
public class SplashActivity extends AbsGuideActivity {

    private SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences("firstTimeRun", Context.MODE_PRIVATE);//SharedPreferences 是一个轻量级的存储类，主要是保存一些小的数据，一些状态信息
    public static String pattern = "yyyy-MM-dd";
    public static SimpleDateFormat formatter = new SimpleDateFormat(pattern);

    @Override
    public List<SinglePage> buildGuideContent() {
        Log.i("今天日期", getStringDateShort());
        BaseDisplay = getWindowManager().getDefaultDisplay();
        shouciyunxing = sharedPreferences.getString("FTR", "");//拿到第一次安装运行的状态值
        jintiankanguoguanggao = sharedPreferences.getString("JTKGGG", "");//拿到今天看过广告的状态 JTKGGG

        /* 判断是否运行过 */
        if (shouciyunxing.equals("") || shouciyunxing.equals("0")) {//刚安装，没运行过，或者清空过本地数据
            Log.e("首次运行判断", "没有运行过，跳到闪屏页");
            //把0改成1
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("FTR", "1");
            editor.apply();

            Log.e("闪屏页判断", "没有登录过，显示闪屏页");
            List<SinglePage> guideContent = new ArrayList<SinglePage>();
            SinglePage page01 = new SinglePage();
            page01.mBackground = getResources().getDrawable(R.drawable.sp1);
            guideContent.add(page01);

            SinglePage page02 = new SinglePage();
            page02.mBackground = getResources().getDrawable(R.drawable.sp2);
            guideContent.add(page02);

            SinglePage page03 = new SinglePage();
            page03.mBackground = getResources().getDrawable(R.drawable.sp3);
            guideContent.add(page03);

            SinglePage page05 = new SinglePage();
            page05.mCustomFragment = new EntryFragment();
            guideContent.add(page05);

            return guideContent;
        } else {//运行过一次，判断 今天有没有看过广告
            Log.d("闪屏页判断", "登录过了，判断 今天有没有 看过广告");

            /* 判断今天看没看过广告 *//* 老板说又要此次看见开页广告了，2020年11月16日17:56:21 */
            if (jintiankanguoguanggao.equals(getStringDateShort())) {//看过了 跳到主页
                Log.d("今天看过广告了", "还是跳到广告页");
                Intent intent = new Intent(SplashActivity.this, AdvertisementActivity.class);//
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);//
                startActivity(intent);
                finish();
            } else {//没看过 看一次，并且记录一次
                Log.e("没看过广告", "跳到广告页");
                //把JTKGGG改成今天日期
                SharedPreferences.Editor editor2 = sharedPreferences.edit();
                editor2.putString("JTKGGG", getStringDateShort());
                editor2.apply();
                Intent intent = new Intent(SplashActivity.this, AdvertisementActivity.class);
                startActivity(intent);
                finish();
            }
            return null;
        }
    }


    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        String dateString = formatter.format(new Date());
        return dateString;
    }

    @Override
    public boolean drawDot() {
        return true;
    }

    @Override
    public Bitmap dotDefault() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_default);
    }

    @Override
    public Bitmap dotSelected() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_selected);
    }

    /**
     * You need provide an id to the pager. You could define an id in values/ids.xml and use it.
     */
    @Override
    public int getPagerId() {
        return R.id.guide_container;
    }
}
