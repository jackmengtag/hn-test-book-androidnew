package com.yangna.lbdsp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.common.utils.AppUtils;

import java.io.File;

public class PublishActivity extends AppCompatActivity {

    private ImageView ivBack;
    private VideoView vvVideo;
    private EditText etTitle;
    private EditText etContent;
    private TextView tvPublish;
    private Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initView();
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        tvPublish = findViewById(R.id.tv_publish);
        vvVideo = findViewById(R.id.vv_video);
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
//                    ToastUtil.showToast("请输入视频标题");
//                    return;
//                }
                if (TextUtils.isEmpty(etContent.getText().toString().trim())) {
                    ToastUtil.showToast("请填写视频内容");
                    return;
                }
//                MediaMetadataRetriever mmr=new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
//                File file=new File(uri);//实例化File对象
//                mmr.setDataSource(file.getAbsolutePath());
//                Bitmap bitmap = mmr.getFrameAtTime();//获得视频第一帧的Bitmap对象
//                Log.d("ddd", "bitmap==" + bitmap);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("title", etTitle.getText().toString().trim());
                bundle.putString("content", etContent.getText().toString().trim());
                bundle.putParcelable("uri", uri);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                hideInput();
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            uri = (Uri) intent.getExtras().getParcelable("uri");
        }
        if (uri != null) {
            vvVideo.setVideoURI(uri);
            vvVideo.start();
        }


    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);//Teacher
        }
    }
}