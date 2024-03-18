package com.yangna.lbdsp.mall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.mall.bean.AppraiseBean;
import com.yangna.lbdsp.mall.bean.MallRecordsModel;

import java.util.List;

import static com.yangna.lbdsp.MainActivity.display;

/* 商品评价适配器 */
public class GoodsAppraiseRecyclerViewAdapter extends RecyclerView.Adapter<GoodsAppraiseRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<AppraiseBean> mList;
    private OnItemClickListener mOnItemClickListener;

    public GoodsAppraiseRecyclerViewAdapter(Context context, List<AppraiseBean> mList) {
        this.mList = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_appraise_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AppraiseBean data = mList.get(position);
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        ViewGroup.LayoutParams params = holder.sangepingjiatu.getLayoutParams();
        params.width = metrics.widthPixels - 60;
        params.height = (metrics.widthPixels - 50) / 3;//门店的四大商品首图，以等宽四个显示
        holder.sangepingjiatu.setLayoutParams(params);

        //设置图片圆角角度
        Glide.with(mContext).load(data.getShoppingUser())//等后台给个用户头像的URL
                .error(R.drawable.no_banner) //异常时候显示的图片
                .placeholder(R.drawable.no_banner) //加载成功前显示的图片
                .fallback(R.drawable.no_banner) //url为空的时候,显示的图片
                .apply(RequestOptions.circleCropTransform())//加载为圆形图片
                .into(holder.appraiseUser_icon);//为了加载网络图片的两步方法

        holder.appraiseUser_name.setText(data.getShoppingUser());//设置用户昵称
//        if (data.getEnterpriseCertification().equals("0")) {//企业认证与否
//            holder.mall_qyrz.setVisibility(View.GONE);
//        } else if (data.getEnterpriseCertification() == null || data.getEnterpriseCertification().equals("")) {//后台没给
//            holder.mall_qyrz.setVisibility(View.GONE);
//        } else {
//            holder.mall_qyrz.setImageResource(R.drawable.qyfw);
//            holder.mall_qyrz.setVisibility(View.VISIBLE);
//        }
        holder.appraiseUser_content.setText(data.getContent());//设置评价内容
        if (data.getImageUrl1() == null || data.getImageUrl1().equals("")) {
        } else {
            Glide.with(mContext).load(data.getImageUrl1())
                    .error(R.drawable.no_banner) //异常时候显示的图片
                    .placeholder(R.drawable.no_banner) //加载成功前显示的图片
                    .fallback(R.drawable.no_banner) //url为空的时候,显示的图片
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))//加载为圆角正方形图片
                    .into(holder.appraiseUser_tu1);//为了加载网络图片的两步方法
        }
        if (data.getImageUrl2() == null || data.getImageUrl2().equals("")) {
        } else {
            Glide.with(mContext).load(data.getImageUrl2())
                    .error(R.drawable.no_banner) //异常时候显示的图片
                    .placeholder(R.drawable.no_banner) //加载成功前显示的图片
                    .fallback(R.drawable.no_banner) //url为空的时候,显示的图片
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))//加载为圆角正方形图片
                    .into(holder.appraiseUser_tu2);//为了加载网络图片的两步方法
        }
        if (data.getImageUrl3() == null || data.getImageUrl3().equals("")) {
        } else {
            Glide.with(mContext).load(data.getImageUrl3())
                    .error(R.drawable.no_banner) //异常时候显示的图片
                    .placeholder(R.drawable.no_banner) //加载成功前显示的图片
                    .fallback(R.drawable.no_banner) //url为空的时候,显示的图片
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))//加载为圆角正方形图片
                    .into(holder.appraiseUser_tu3);//为了加载网络图片的两步方法
        }
        if (mOnItemClickListener != null) {
            // 设置item条目短按点击事件的监听
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClickListener(holder.itemView, pos);
                }
            });
            // 设置item条目长按点击事件的监听
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {//返回数目
        return mList.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    // 定义item条目点击事件接口
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);

        void onItemLongClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView appraiseUser_icon;
        private TextView appraiseUser_name;
        private TextView appraiseUser_content;
        private LinearLayout sangepingjiatu;
        private ImageView appraiseUser_tu1;
        private ImageView appraiseUser_tu2;
        private ImageView appraiseUser_tu3;

        ViewHolder(View itemView) {
            super(itemView);
            appraiseUser_icon = (ImageView) itemView.findViewById(R.id.appraise_user_icon);
            appraiseUser_name = (TextView) itemView.findViewById(R.id.appraise_user_name);
            appraiseUser_content= (TextView) itemView.findViewById(R.id.appraise_user_content);
            sangepingjiatu = (LinearLayout) itemView.findViewById(R.id.sange_pingjiatu);
            appraiseUser_tu1 = (ImageView) itemView.findViewById(R.id.appraise_user_tu1);
            appraiseUser_tu2 = (ImageView) itemView.findViewById(R.id.appraise_user_tu2);
            appraiseUser_tu3 = (ImageView) itemView.findViewById(R.id.appraise_user_tu3);
        }
    }

}
