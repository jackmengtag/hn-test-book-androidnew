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
import com.yangna.lbdsp.mall.bean.MallGoodsModel;
import com.yangna.lbdsp.mall.bean.MallGoodsWPBLRecords;

import java.util.List;

/* 门店内商品适配器 无瀑布流版本 */
public class MallGoodsAdapter extends RecyclerView.Adapter<MallGoodsAdapter.ViewHolder> {

    private Context mContext;
    private List<MallGoodsWPBLRecords> mGoodsDataList;
    private OnItemClickListener mOnItemClickListener;

    // 定义item条目点击事件接口
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
//        void onItemClickListener(int position, List<MallGoodsModel> mallGoodsModelList);
    }

    public void setmOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public MallGoodsAdapter(Context context, List<MallGoodsWPBLRecords> mGoodsDataList) {
        this.mGoodsDataList = mGoodsDataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mall_goods_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnItemClickListener);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        MallGoodsWPBLRecords data = mGoodsDataList.get(position);
        List<String> tpsz = data.getMainImageUrls();
        if (tpsz.size() == 0) {
            Glide.with(mContext).load("")
                    .error(R.drawable.tjtp) //异常时候显示的图片
                    .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                    .fallback(R.drawable.tjtp) //url为空的时候,显示的图片
                    .into(holder.goods_pictureUrl);//为了加载网络图片的两步方法
        } else {
            Glide.with(mContext).load(tpsz.get(0))
                    .error(R.drawable.tjtp) //异常时候显示的图片
                    .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                    .fallback(R.drawable.tjtp) //url为空的时候,显示的图片
                    .into(holder.goods_pictureUrl);//为了加载网络图片的两步方法
        }
        holder.goods_Name.setText(data.getProductName());//设置商品名称
        holder.goods_Price.setText("¥" + data.getProductPrice());
        holder.goods_Amount.setText("库存" + data.getStockCount() + "件");
    }

    @Override
    public int getItemCount() {//返回数目
        return mGoodsDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView goods_pictureUrl;
        TextView goods_Name;
        TextView goods_Price;
        TextView goods_Amount;

        //ViewHolder是静态内部类，它的内部函数不能访问外部变量和方法。
        //所以在这里通过传参的方式获取onClickListerner。
        public ViewHolder(View view, final OnItemClickListener onItemClickListener) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        //确保position值有效
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClickListener(view, position);
                        }
                    }
                }
            });
            goods_pictureUrl = (ImageView) view.findViewById(R.id.goods_pictureUrl);
            goods_Name = (TextView) view.findViewById(R.id.goods_Name);
            goods_Price = (TextView) view.findViewById(R.id.goods_Price);
            goods_Amount = (TextView) view.findViewById(R.id.goods_Amount);
        }
    }
}
