package com.yangna.lbdsp.videoCom.AliPlay;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.utils.OnVideoControllerListener;
import com.yangna.lbdsp.home.impl.ControllreClickListenter;
import com.yangna.lbdsp.videoCom.ControllerView;
import com.yangna.lbdsp.videoCom.VideoBean;

/**
 * 视频列表的adapter
 *
 * @author xlx
 */
public class LittleVideoListAdapter extends BaseVideoListAdapter<LittleVideoListAdapter.MyHolder, VideoBean> {

    public static final String TAG = LittleVideoListAdapter.class.getSimpleName();
    private OnItemBtnClick mItemBtnClick;

    private ControllreClickListenter mClickListener;


    public LittleVideoListAdapter(Context context) {
        super(context);
    }

    public void setmClickListener(ControllreClickListenter mClickListener) {
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public LittleVideoListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_pager, viewGroup, false);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int position) {
        super.onBindViewHolder(myHolder, position);
        myHolder.mIvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemBtnClick != null) {
                    mItemBtnClick.onDownloadClick(position);
                }
            }
        });
        myHolder.controllerView.setVideoData(list.get(position));
        myHolder.mVideoInfoView.setVideoInfo(list.get(position));
        VideoBean video = list.get(position);

        myHolder.controllerView.setListener(new OnVideoControllerListener() {
            @Override
            public void onHeadClick() {

            }

            @Override
            public void onLikeClick() {
                mClickListener.onClick(list.get(position),myHolder.controllerView,1);
            }

            @Override
            public void onCommentClick() {
                mClickListener.onClick(list.get(position),myHolder.controllerView,2);
            }

            @Override
            public void onShareClick() {
                mClickListener.onClick(list.get(position),myHolder.controllerView,3);
            }
        });



    }




    public final class MyHolder extends BaseVideoListAdapter.BaseHolder {
        private ImageView thumb;
        public FrameLayout playerView;
        private ImageView mIvDownload;
        private ViewGroup mRootView;
        private VideoInfoView mVideoInfoView;
        private ImageView mIvNarrow;
        private ImageView mIvLive;
        private ImageView mPlayIconImageView;
        private ControllerView controllerView;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "new PlayerManager");
            thumb = itemView.findViewById(R.id.img_thumb);
            playerView = itemView.findViewById(R.id.player_view);
            mIvDownload = itemView.findViewById(R.id.iv_download);
            mRootView = itemView.findViewById(R.id.root_view);
            mVideoInfoView = itemView.findViewById(R.id.content_view);
            mIvNarrow = itemView.findViewById(R.id.iv_narrow);
            mIvLive = itemView.findViewById(R.id.iv_live);
            mPlayIconImageView = itemView.findViewById(R.id.iv_play_icon);
            controllerView = itemView.findViewById(R.id.controller);
        }

        @Override
        public ImageView getCoverView() {
            return thumb;
        }

        @Override
        public ViewGroup getContainerView() {
            return mRootView;
        }

        @Override
        public ImageView getPlayIcon() {
            return mPlayIconImageView;
        }

    }

    public interface OnItemBtnClick {
        void onDownloadClick(int position);
    }

    public void setItemBtnClick(
            OnItemBtnClick mItemBtnClick) {
        this.mItemBtnClick = mItemBtnClick;
    }


}

