package com.yangna.lbdsp.mine.view;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.home.impl.HomeView;
import com.yangna.lbdsp.widget.AppUpdateProgressDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

/* 隐私政策 页面 */
public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        ButterKnife.bind(this);
    }

    /*退出本页*/
    @OnClick(R.id.ys_return)
    public void doBack(View view){
        finish();
    }

//    @SuppressLint("SetTextI18n")
//    @Override//恢复时运行
//    public void onResume() {
//        super.onResume();
//    }

}
