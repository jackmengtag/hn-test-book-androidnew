package com.yangna.lbdsp.home.view;


import android.content.Intent;
import android.widget.MediaController;
import android.widget.VideoView;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseActivity;

import butterknife.BindView;

public class PreviewVideoActivity extends BaseActivity {

    @BindView(R.id.pv_video)
    VideoView videoview;

    private String path = null;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_preview_video;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent != null) {
            path = intent.getStringExtra("path");
        }
        videoview.setVideoPath("http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8");  // 香港卫视地址
        videoview.start();
        MediaController mediaController = new MediaController(this);
        videoview.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoview);
    }
}
