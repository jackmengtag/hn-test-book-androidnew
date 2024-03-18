package com.yangna.lbdsp.videoCom.AliPlay;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.videoCom.VideoBean;

/**
 * 视频信息, 包含用户头像, 用户昵称, 视频标题
 * @author xl
 */
public class VideoInfoView extends RelativeLayout {

    private ImageView userIcon;
    private TextView userNickName;
    private TextView title;

    public VideoInfoView(Context context) {
        this(context, null);
    }

    public VideoInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_video_cotent_view, this, true);
        userIcon = view.findViewById(R.id.iv_user_icon);
        userNickName = view.findViewById(R.id.tv_user_nick_name);
        title = view.findViewById(R.id.tv_title);
    }

    public void setVideoInfo(VideoBean videoInfo) {
        if (videoInfo.getNickName() != null) {
            Glide.with(this).load(videoInfo.getLogo())
                    .error(R.mipmap.ic_launcher) //异常时候显示的图片
                    .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                    .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
                    .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                    .into(userIcon);//为了加载网络图片的两步方法
            userNickName.setText(videoInfo.getNickName());
        } else {
            //  如果没有用户信息用默认名称和图标
            userIcon.setImageResource(R.mipmap.log);
            userNickName.setText(getResources().getString(R.string.a_cache));
        }

        title.setText(videoInfo.getDescription());
    }
}
