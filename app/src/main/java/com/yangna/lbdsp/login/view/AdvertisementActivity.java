package com.yangna.lbdsp.login.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.yangna.lbdsp.MainActivity;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.login.presenter.AdInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yangna.lbdsp.common.base.BaseApplication.BaseDisplay;


//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;

/**
 * Created by yc on 2018/8/19.
 */
public class AdvertisementActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ad_image;
    private ImageView bottom_image;
    private Button ad_timer;
    private Integer ms = 4;
    private CountDownTimer textTimer;
    private Intent it;
    String httpUrl;
    // 针对SD卡读取权限申请
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
//        if (Build.VERSION.SDK_INT < 16) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
        setContentView(R.layout.activity_advertisement);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.black_21).init();//强制性保留手机电池、时间、信号状态栏和设置底色

        ad_image = (ImageView) findViewById(R.id.ad_image);
        ad_timer = (Button) findViewById(R.id.ad_timer);
        bottom_image = (ImageView) findViewById(R.id.bottom_image);
        ad_image.setOnClickListener(this);
        ad_timer.setOnClickListener(this);
        bottom_image.setOnClickListener(this);
        httpUrl = "http://www.lbdsp.com";

        // 检查当前是否存在广告图，如果存在，则直接从本地文件中读取，否则加载默认图片
        File PicFile = new File(Environment.getExternalStorageDirectory(), "adImage.jpg");
        if (PicFile.exists()) {
            // 加载本地图片的两种方式
            ad_image.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/adImage.jpg"));
            //ad_image.setImageURI(Uri.fromFile(PicFile));
        } else {
            // 可在此加载默认图片
            //ad_image.setImageResource(R.mipmap.ad_test_1);
//            SharedPreferencesUtil.put(this, "version", "0");
        }

        verifyStoragePermissions(this); //申请SD卡读写权限
        adCountDown(); //广告倒计时
        verifyNetwork(); //检查网络
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ad_image:
                /* 如果存在广告链接，则跳转到相应的webview界面 */
//                if (!httpUrl.equals("none")) {
//                    textTimer.cancel();
//                    it = new Intent(this, WebViewActivity.class);
//                    it.putExtra("httpUrl", httpUrl);
//                    startActivity(it);
//                    finish();
//                }
                break;
            case R.id.ad_timer:
                textTimer.cancel();
                goNextActivity();
                finish();
                break;
            case R.id.bottom_image:
                textTimer.cancel();
                goNextActivity();
                break;
        }
    }

    /**
     * 针对安卓6.0以上机型的动态权限获取
     */
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有SD卡读写权限
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //如果没有权限，则申请权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 广告倒计时
     */
    public void adCountDown() {
        textTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { //第一个参数为总计时，第二个参数为计时速度
//                ms -= 1;
                ad_timer.setText("跳过 " + millisUntilFinished / 1000 + " s");
            }

            @Override
            public void onFinish() {
                goNextActivity();
                finish();
            }
        }.start();
    }

    /**
     * 先判断是否有可用网络
     * 使用ConnectivityManager获取手机所有连接管理对象
     * 使用manager获取NetworkInfo对象
     * 最后判断当前网络状态是否为连接状态即可
     */
    public void verifyNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if ((networkInfo == null) || !networkInfo.isConnected()) {
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Log.i("当前网络可用", networkInfo.toString());
            // 网络获取广告资源
            getAdMsg();
        }
    }

    public void goNextActivity() {
        it = new Intent(this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        finish();
    }

    public void getAdMsg() {
        Log.i("已经启动网络", "去拿广告链接了！");
        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
//                .baseUrl("https://www.easy-mock.com/mock/5b71b3caebd4a208cce29bf1/advertisement/")//
                .baseUrl("http://open.iciba.com/")//Retrofit2 的baseUlr 必须以 /（斜线） 结束，不然会抛出一个IllegalArgumentException,所以如果你看到别的教程没有以 / 结束，那么多半是直接从Retrofit 1.X 照搬过来的。
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AdInterface ad = retrofit.create(AdInterface.class);
//        Call<List<Advertisement>> adCall = ad.getAdMsg();
        Call<ResponseBody> adCall = ad.getForm("");

        DisplayMetrics metrics = new DisplayMetrics();
        BaseDisplay.getMetrics(metrics);
        ViewGroup.LayoutParams params = ad_image.getLayoutParams();
        Log.d("当前屏幕宽", "metrics.widthPixels = " + metrics.widthPixels);
        Log.d("当前屏幕高", "metrics.heightPixels = " + metrics.heightPixels);

        params.width = metrics.widthPixels;
        params.height = (metrics.heightPixels < 1024) ? metrics.heightPixels + 1 : metrics.heightPixels;//高度不够1024就加100
//        ad_image.setLayoutParams(params);
        adCall.enqueue(new Callback</*List<Advertisement>*/ResponseBody>() {
            @Override
            public void onResponse(Call</*List<Advertisement>*/ResponseBody> call, Response</*List<Advertisement>*/ResponseBody> response) {
                if (!AdvertisementActivity.this.isFinishing()) {
                    try {
                        String fhz = response.body().string();
                        Log.i("拿广告 successfully", fhz);
                        try {
                            JSONObject jsonObject = new JSONObject(fhz);
                            String url = jsonObject.optString("fenxiang_img", "https://bbs-fd.zol-img.com.cn/t_s800x5000/g6/M00/01/02/ChMkKl-WltKICfrVAAN6saXejLMAAEXEgLonWAAA3rJ721.jpg");
                            //获取最新广告版本
//                    UpdateAdvertisement ad = new UpdateAdvertisement();
//                    ad.getLatestVersion(SplashActivity.this, fhz);
                            //https://bbs-fd.zol-img.com.cn/t_s800x5000/g6/M00/0E/0A/ChMkKV_IkBOIfXnqABXcqee_n2UAAGM4APAGyYAFdzB337.jpg
                            Glide.with(AdvertisementActivity.this).load("https://bbs-fd.zol-img.com.cn/t_s800x5000/g6/M00/04/04/ChMkKWAfcVyITmbhAAxHSMuRixUAAJjYALErxoADEdg907.jpg")
                                    .error(R.drawable.sp4) //异常时候显示的图片
                                    .placeholder(R.drawable.sp4) //加载成功前显示的图片
                                    .fallback(R.drawable.sp4) //url为空的时候,显示的图片
                                    .into(ad_image);//为了加载网络图片的两步方法
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call</*List<Advertisement>*/ResponseBody> call, Throwable t) {
                if (!AdvertisementActivity.this.isFinishing()) {
                    Log.e("拿广告 fail", " " + t);
                    //https://bbs-fd.zol-img.com.cn/t_s800x5000/g6/M00/0E/0A/ChMkKV_IkBOIfXnqABXcqee_n2UAAGM4APAGyYAFdzB337.jpg
                    Glide.with(AdvertisementActivity.this).load("https://bbs-fd.zol-img.com.cn/t_s800x5000/g6/M00/04/04/ChMkKWAfcVyITmbhAAxHSMuRixUAAJjYALErxoADEdg907.jpg")
                            .error(R.drawable.sp4) //异常时候显示的图片
                            .placeholder(R.drawable.sp4) //加载成功前显示的图片
                            .fallback(R.drawable.sp4) //url为空的时候,显示的图片
                            .into(ad_image);//为了加载网络图片的两步方法 太平洋图片有水印，用中关村的 https://bbs-fd.zol-img.com.cn/t_s800x5000/g6/M00/01/02/ChMkKl-WltKICfrVAAN6saXejLMAAEXEgLonWAAA3rJ721.jpg
                }
            }
        });
    }

}
