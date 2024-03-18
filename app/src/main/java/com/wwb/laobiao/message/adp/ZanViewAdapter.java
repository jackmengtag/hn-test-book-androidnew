package com.wwb.laobiao.message.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wwb.laobiao.hongbao.widget.AvatarImageView;
import com.wwb.laobiao.message.bean.FansDataModel;
import com.wwb.laobiao.message.bean.ZanDataModel;
import com.yangna.lbdsp.R;

import java.util.List;
import java.util.Objects;

public class ZanViewAdapter extends RecyclerView.Adapter<ZanViewAdapter.ViewHolder> {

    private Context mContext;
    private List<ZanDataModel> mList;
    private ZanDataModel data;
    private OnItemClikListener mOnItemClikListener;

    public ZanViewAdapter(Context context, List<ZanDataModel> mList) {
        this.mContext = context;
        this.mList = mList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_zan, parent, false);
        ViewHolder holder = new ViewHolder(view);//item_fensi  item_movie_layout
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        data = mList.get(position);

        holder.onshow(data,position);
        
        if (mOnItemClikListener != null) {
            // 设置item条目短按点击事件的监听
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClikListener.onItemClik(holder.itemView, pos);
                }
            });

            // 设置item条目长按点击事件的监听
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClikListener.onItemLongClik(holder.itemView, pos);
                    return false;
                }
            });
//            holder.guanzhu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = holder.getLayoutPosition();
//                    mOnItemClikListener.onItemDo(holder.itemView, pos);
//                }
//            });

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context mcontent;
        private ImageView coverUrl;
        private TextView state;
        private TextView home_item_movie_list_director;
        private TextView home_item_movie_list_Starring;
        private TextView home_item_movie_list_type;

        private TextView content;
        public TextView guanzhu;//iv_guanzhu
        public	TextView   createTime	;//创建时间	string
//        public	String  id	;//粉丝记录id	string
        public AvatarImageView logo	;//粉丝的用户头像	string
//        public	String   mutual	;//是否相互关注（为0时代表不是相互关注，为1时代表已相互关注）	string
        public	TextView  nickName	;//粉丝的用户名称	string
//        public	String  status	;//是否已阅读 0-未阅读 1-已阅读

        public int position;

        public ViewHolder(View itemView) {
            super(itemView);
            mcontent=itemView.getContext();
            coverUrl = (ImageView) itemView.findViewById(R.id.id_coverUrl);
            state = (TextView) itemView.findViewById(R.id.iv_state);
            content = (TextView) itemView.findViewById(R.id.iv_content);
            createTime = (TextView) itemView.findViewById(R.id.iv_createTime);
            nickName = (TextView) itemView.findViewById(R.id.iv_nickName);
            logo = (AvatarImageView) itemView.findViewById(R.id.jmui_avatar_iv);

//            home_item_movie_list_pic = (ImageView) itemView.findViewById(R.id.home_item_movie_list_pic);
//            home_item_movie_list_title = (TextView) itemView.findViewById(R.id.home_item_movie_list_title);
//            home_item_movie_list_director = (TextView) itemView.findViewById(R.id.home_item_movie_list_director);
//            home_item_movie_list_Starring = (TextView) itemView.findViewById(R.id.home_item_movie_list_Starring);
//            home_item_movie_list_type = (TextView) itemView.findViewById(R.id.home_item_movie_list_type);
//            home_item_movie_list_regions = (TextView) itemView.findViewById(R.id.home_item_movie_list_regions);  //iv_content
        }

        public void onshow(ZanDataModel data, int position) {
            this.position=position;
            createTime.setText(data.createTime);
            nickName.setText(data.nickName);

            Glide.with(Objects.requireNonNull(mcontent)).load(data.logo)
                    .error(R.mipmap.user_icon) //异常时候显示的图片
                    .placeholder(R.mipmap.user_icon) //加载成功前显示的图片
                    .fallback(R.mipmap.user_icon) //url为空的时候,显示的图片
                    .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                    .into(logo);//为了加载网络图片的两步方法

            Glide.with(Objects.requireNonNull(mcontent)).load(data.coverUrl)
                    .error(R.mipmap.user_icon) //异常时候显示的图片
                    .placeholder(R.mipmap.user_icon) //加载成功前显示的图片
                    .fallback(R.mipmap.user_icon) //url为空的时候,显示的图片
                    .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                    .into(coverUrl);//为了加载网络图片的两步方法

//            content.setText(data.status);
//            if(data.status.equals())

//guanzhu
        }
    }

    // 定义item条目点击事件接口
    public interface OnItemClikListener {
        void onItemClik(View view, int position);

        void onItemLongClik(View view, int position);

        void onItemDo(View view, int position);
    }

    public void setItemClikListener(OnItemClikListener mOnItemClikListener) {
        this.mOnItemClikListener = mOnItemClikListener;
    }

}
