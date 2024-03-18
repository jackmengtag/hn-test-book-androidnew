package com.yangna.lbdsp.mine.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yangna.lbdsp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * web页面
 */
public class WebActivity extends AppCompatActivity {
    @BindView(R.id.web_title)//标题栏
            TextView mtitle;
    private Context context;
    private WebView mWebView;    //声明WebView组件的对象

    @SuppressLint("SetJavaScriptEnabled")
    @Override//创建时运行
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        String url = intent.getStringExtra("httpUrl");//拿到上一个页面传过来的网址
        ButterKnife.bind(this);//布局注入，这句话后，上面的三个TextView就已经赋值了，就可以使用了。
        mWebView = (WebView) findViewById(R.id.web);//获取WebView组件

        mWebView.getSettings().setJavaScriptEnabled(true);//设置JavaScript可用
        mWebView.setWebChromeClient(new WebChromeClient());//处理JavaScript对话框
        mWebView.getSettings().setSupportZoom(true);//手势缩放按钮
        mWebView.getSettings().setBuiltInZoomControls(true);//手势缩放按钮
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

//        webView.setWebViewClient(new WebViewClient());//处理各种通知和请求事件，如果不使用该句代码，将使用内置浏览器访问网页
        mWebView.loadUrl(url);

        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//          public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                WebView.HitTestResult hit = view.getHitTestResult();
                //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
                if (TextUtils.isEmpty(hit.getExtra()) || hit.getType() == 0) {
                    //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
                    Log.e("重定向", "重定向: " + hit.getType() + " && EXTRA（）" + hit.getExtra() + "------");
                    Log.e("重定向", "GetURL: " + view.getUrl() + "\n" + "getOriginalUrl()" + view.getOriginalUrl());
                    Log.d("重定向", "URL: " + url);
                }
                if (url.startsWith("http://") || url.startsWith("https://")) { //加载的url是http/https协议地址
                    view.loadUrl(url);
                    return false; //返回false表示此url默认由系统处理,url未加载完成，会继续往下走
                } else { //加载的url是自定义协议地址
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }

            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("开始加载网页","onPageStarted()");//搞定！以后就用这种内部浏览器
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("结束加载网页","onPageFinished()");//搞定！以后就用这种内部浏览器
                if (!TextUtils.isEmpty(view.getTitle())) {
                    mtitle.setText(view.getTitle());
                } else {
                    mtitle.setText("获取当前网页标题……");
                }
            }

            //启用mixed content  Android webView 从 Lollipop 开始默认不开MixedContentMode,因此，我们开启即可满足我们大多数需求。
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                    mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
                }
                handler.proceed();
            }
        });
    }

    @Override//启动时运行
    protected void onStart() {
        super.onStart();
        //WebView不一定要在onStart方法里面才开始跑的
    }

    @Override
    public void onResume() {
        super.onResume();
        // 在当前的activity中注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("ExitApp");

    }

    /**
     * 点击跳回上一页
     */
    @OnClick(R.id.web_return)
    public void doBack(View view) {
//        Intent intent = new Intent();//显示意图
//        intent.setClass(WebActivity.this, MainActivity.class);
//        startActivity(intent);
        finish();//销毁页面
    }

    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
//        this.unregisterReceiver(this.broadcastReceiver);//销毁 退出 广播
        Log.e("通用web 页面", "WebActivity 已被全局销毁");
    }

}
