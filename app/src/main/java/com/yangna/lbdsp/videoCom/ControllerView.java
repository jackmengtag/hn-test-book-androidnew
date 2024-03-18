package com.yangna.lbdsp.videoCom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;
import com.yangna.lbdsp.common.utils.NumUtils;
import com.yangna.lbdsp.common.utils.OnVideoControllerListener;
import com.yangna.lbdsp.home.view.MallActivity;
import com.yangna.lbdsp.mall.view.GoodsActivity;
import com.yangna.lbdsp.widget.CircleImageView;
import com.yangna.lbdsp.widget.IconFontTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.view.animation.Animation.INFINITE;

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class ControllerView extends RelativeLayout implements View.OnClickListener {
    @BindView(R.id.tv_content)
    TextView autoLinkTextView;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.lottie_anim)
    LottieAnimationView animationView;
    @BindView(R.id.rl_like)
    RelativeLayout rlLike;
    @BindView(R.id.iv_comment)
    IconFontTextView ivComment;
    @BindView(R.id.iv_share)
    IconFontTextView ivShare;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.rl_record)
    RelativeLayout rlRecord;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_head_anim)
    CircleImageView ivHeadAnim;
    @BindView(R.id.iv_like)
    IconFontTextView ivLike;
    @BindView(R.id.tv_likecount)
    TextView tvLikecount;
    @BindView(R.id.tv_commentcount)
    TextView tvCommentcount;
    @BindView(R.id.tv_sharecount)
    TextView tvSharecount;
    @BindView(R.id.iv_focus)
    ImageView ivFocus;
    @BindView(R.id.gouwuche)
    ImageView gouwuche;
    @BindView(R.id.video_count)
    MarqueeTextView video_count;
    private OnVideoControllerListener listener;
    private VideoBean videoData;

    private static final String TAG = "LocalFragment";

    public ControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }


    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_controller, this);
        ButterKnife.bind(this, rootView);

        ivHead.setOnClickListener(this);
        ivComment.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        rlLike.setOnClickListener(this);
        ivFocus.setOnClickListener(this);

        /**
         * 视频唱片旋转
         */
//        setRotateAnim();
    }

    public void setVideoData(VideoBean videoData) {
        this.videoData = videoData;
        Log.i(TAG, "videoData" + videoData.getNickName());
        if (videoData != null) {
            Glide.with(BaseApplication.getContext()).load(videoData.getLogo())
                    .error(R.mipmap.user_icon) //异常时候显示的图片
                    .placeholder(R.mipmap.user_icon) //加载成功前显示的图片
                    .fallback(R.mipmap.user_icon) //url为空的时候,显示的图片
                    .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                    .into(ivHead);//为了加载网络图片的两步方法

        }
        tvNickname.setText(videoData.getNickName());
//        ivHeadAnim.setImageResource(videoData.getUserBean().getHead());

        if (videoData.getIdentityAuth() != null && videoData.getIdentityAuth().equals("S")) {
            autoLinkTextView.setVisibility(View.VISIBLE);
            gouwuche.setVisibility(View.VISIBLE);
        } else {
            autoLinkTextView.setVisibility(View.GONE);
            gouwuche.setVisibility(View.GONE);
        }
        if (autoLinkTextView != null && videoData != null) {
            autoLinkTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "点击进入商城界面" + videoData.getShopId());
                    Map<String, Object> maps = new HashMap<>();
                    maps.put("shopId", videoData.getShopId() + "");
                    maps.put("shopName", videoData.getShopName());
                    maps.put("shopLogo", videoData.getShopLogoUrl());
                    UIManager.switcher(getContext(), maps, MallActivity.class);
                }
            });
        }
        if (videoData.getDescription().equals("内容")) {
            video_count.setText("老表短视频欢迎您");
        } else {
            video_count.setText(videoData.getDescription());
        }

//        AutoLinkHerfManager.setContent(videoData.getContent(), autoLinkTextView);

        videoData.setCommentAmount(videoData.getCommentAmount());
        videoData.setPraiseAmount(videoData.getPraiseAmount());
        tvLikecount.setText(videoData.getPraiseAmount());
        tvCommentcount.setText(videoData.getCommentAmount());
        tvSharecount.setText(NumUtils.numberFilter(videoData.getShareCount()));


        //点赞状态
        if (videoData.isHasPraise()) {
            animationView.setVisibility(INVISIBLE);
            Log.d("tang", "点赞了");
            ivLike.setTextColor(getResources().getColor(R.color.color_FF0041));
        } else {
            animationView.setVisibility(INVISIBLE);
            ivLike.setTextColor(getResources().getColor(R.color.white));
            Log.d("tang", "没点赞");
        }

        //关注状态
        if (videoData.isFocused()) {
            ivFocus.setVisibility(GONE);
        } else {
            ivFocus.setVisibility(VISIBLE);
        }
    }

    public void setListener(OnVideoControllerListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.iv_head:
                listener.onHeadClick();
                break;
            case R.id.rl_like:
                listener.onLikeClick();
//                like();
                break;
            case R.id.iv_comment:
                listener.onCommentClick();
                break;
            case R.id.iv_share:
                listener.onShareClick();
                break;
            case R.id.iv_focus:
                if (!videoData.isFocused()) {
                    videoData.setHasPraise(true);
                    ivFocus.setVisibility(GONE);
                }
                break;
        }
    }

    /**
     * 评论动作
     */
    public void commMsg(boolean model) {
        if (model) {
            //评论成功
            videoData.setCommentAmount(String.valueOf(Integer.parseInt(videoData.getCommentAmount()) + 1));
        } else {
            //评论失败
            videoData.setCommentAmount(videoData.getCommentAmount());
        }
        Log.i(TAG, "评论数" + videoData.getCommentAmount());
        tvCommentcount.setText(videoData.getCommentAmount());
    }

    /**
     * 点赞动作
     */
    public void like(boolean model, VideoBean videoBean) {
        if (model) {
            //点赞
            this.videoData = videoBean;
            animationView.setAnimation("like.json");
            videoData.setPraiseAmount(String.valueOf(Integer.parseInt(videoData.getPraiseAmount()) + 1));
            animationView.setVisibility(VISIBLE);
            animationView.playAnimation();
            ivLike.setTextColor(getResources().getColor(R.color.color_FF0041));
        } else {
            videoData.setPraiseAmount(String.valueOf(Integer.parseInt(videoData.getPraiseAmount()) - 1));
            //取消点赞
            animationView.setVisibility(INVISIBLE);
            ivLike.setTextColor(getResources().getColor(R.color.white));
        }
        tvLikecount.setText(videoData.getPraiseAmount());
        videoData.setHasPraise(!videoData.isHasPraise());
    }



    /**
     * 循环旋转动画
     */
    private void setRotateAnim() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 359,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(INFINITE);
        rotateAnimation.setDuration(8000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rlRecord.startAnimation(rotateAnimation);
    }

    /**
     * 获取评论数
     */
    public void setCommentCount(int commentTotal) {

    }

    /**
     * 获取点赞数
     */
    public void setLikeCount(int praiseTotal) {
    }
}
