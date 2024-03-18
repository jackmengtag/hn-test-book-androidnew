package com.yangna.lbdsp.videoCom;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.home.adapter.BaseRvAdapter;
import com.yangna.lbdsp.home.adapter.BaseRvViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: ＱinZhenYu
 * @CreateDate: 2020/9/2 16:39
 * @Description:
 */

public class DouYinAliPlayAdapter extends BaseRvAdapter<VideoBean, DouYinAliPlayAdapter.ViewHolder> {

    Context mContext;
    List<VideoBean> videoInfoList;
    protected List<VideoBean> mDataList;

    public DouYinAliPlayAdapter(Context mContext, List<VideoBean> datas) {
        super(mContext, datas);
    }

    public List<VideoBean> getData() {
        return videoInfoList;
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
    public DouYinAliPlayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DouYinAliPlayAdapter.ViewHolder holder, int position) {


    }

    @Override
    protected void onBindData(ViewHolder holder, VideoBean data, int position) {
        Glide.with(context)
                .load(data.getUploadAddress())
                .into(holder.videoCoverImg);

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.OnItemClickListener(holder.mRootView, v, holder.getAdapterPosition());

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoInfoList == null ? 0 : videoInfoList.size();
    }


    public class ViewHolder extends BaseRvViewHolder {

        @BindView(R.id.root_view)
        FrameLayout mRootView;

        @BindView(R.id.imgVideoCover)
        ImageView videoCoverImg;


        public ImageView getVideoCoverImg() {
            return videoCoverImg;
        }

        public void setVideoCoverImg(ImageView videoCoverImg) {
            this.videoCoverImg = videoCoverImg;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ViewGroup getContainerView() {
            return mRootView;
        }
    }

    public interface OnItemClickListener {
        void OnItemClickListener(ViewGroup rootView, View v, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}