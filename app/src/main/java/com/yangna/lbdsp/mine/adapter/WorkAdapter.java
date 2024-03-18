package com.yangna.lbdsp.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.manager.UIManager;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.utils.NumUtils;
import com.yangna.lbdsp.home.adapter.BaseRvAdapter;
import com.yangna.lbdsp.home.adapter.BaseRvViewHolder;
import com.yangna.lbdsp.mine.view.PlayListActivity;
import com.yangna.lbdsp.videoCom.VideoBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * create on 2020-05-21
 * description
 *
 * @author Administrator
 */
public class WorkAdapter extends BaseRvAdapter<VideoBean, WorkAdapter.WorkViewHolder> {
    List<VideoBean> videoInfoList;

    public WorkAdapter(Context context, List<VideoBean> datas) {
        super(context, datas);
        Log.e("--message", "WorkAdapter: " + datas.size());
    }

    public void setNewData(List<VideoBean> videoInfoList) {
        this.videoInfoList = videoInfoList;
        notifyDataSetChanged();
    }

    public void addData(List<VideoBean> datas) {
        videoInfoList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    protected void onBindData(WorkViewHolder holder, VideoBean videoBean, int position) {

        holder.tvLikeCount.setText(videoBean.getPraiseAmount());

        holder.itemView.setOnClickListener(v -> {
            PlayListActivity.initPos = position;
            Intent intent = new Intent();
            intent.setClass(context,PlayListActivity.class);
            intent.putExtra("videoInfoList", (Serializable) videoInfoList);
            context.startActivity(intent);
        });
        Glide.with(context)
                .load(videoBean.getUploadAddress())
                .error(R.drawable.tjtp) //异常时候显示的图片
                .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                .fallback(R.drawable.tjtp) //url为空的时候,显示的图片
//                .bitmapTransform(new CropCircleTransformation(BaseApplication.getContext()))//显示为圆形图片
                .into(holder.ivCover);
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rooView = LayoutInflater.from(context).inflate(R.layout.item_work, parent, false);
        return new WorkViewHolder(rooView);
    }

    public class WorkViewHolder extends BaseRvViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_likecount)
        TextView tvLikeCount;

        public WorkViewHolder(View itemView) {
            super(itemView);
        }
    }

}
