package com.yangna.lbdsp.mall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.mall.bean.Records;

import java.util.List;

//管理门店内商品适配器
public class StoreManagementGoods2Adapter extends RecyclerView.Adapter<StoreManagementGoods2Adapter.ViewHolder> {

    private Context mContext;
    private List<Records> mGoodsDataList;
    private OnItemClickListener mOnItemClickListener;

    public StoreManagementGoods2Adapter(Context context, List<Records> mGoodsDataList) {
        this.mGoodsDataList = mGoodsDataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.store_management_goods_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Records data = mGoodsDataList.get(position);
        Glide.with(mContext)
                .load(data.getPictureUrl())
                .error(R.mipmap.ic_launcher) //异常时候显示的图片
                .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
                .into(holder.goods_pictureUrl);//为了加载网络图片的两步方法
        holder.goods_Name.setText(data.getGoodsName());//设置商品名称
        holder.goods_Price.setText("¥" + data.getGoodsPrice());
        holder.goods_Amount.setText("库存" + data.getGoodsAmount() + "件");
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
                    mOnItemClickListener.onItemClickLongListener(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {//返回数目
        return mGoodsDataList.size();
    }

    public void setItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    // 定义item条目点击事件接口
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);

        void onItemClickLongListener(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView goods_pictureUrl;
        TextView goods_Name;
        TextView goods_Price;
        TextView goods_Amount;

        ViewHolder(View view) {
            super(view);
            goods_pictureUrl = (ImageView) view.findViewById(R.id.goods_pictureUrl);
            goods_Name = (TextView) view.findViewById(R.id.goods_Name);
            goods_Price = (TextView) view.findViewById(R.id.goods_Price);
            goods_Amount = (TextView) view.findViewById(R.id.goods_Amount);
        }
    }
}
