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

import java.util.List;

//管理门店内商品适配器
public class StoreManagementGoodsAdapter extends RecyclerView.Adapter<StoreManagementGoodsAdapter.ViewHolder> {

    private Context mContext;
    private List<MallGoodsModel.GoodsList.Records> mGoodsDataList;
    private OnItemClickListener mOnItemClickListener;

    public StoreManagementGoodsAdapter(Context context, List<MallGoodsModel.GoodsList.Records> mGoodsDataList) {
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
        MallGoodsModel.GoodsList.Records data = mGoodsDataList.get(position);
        Glide.with(mContext)
                .load(data.getUseDes())
                .error(R.mipmap.ic_launcher) //异常时候显示的图片
                .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
                .into(holder.goods_pictureUrl);//为了加载网络图片的两步方法
        holder.goods_Name.setText(data.getProductName());//设置商品名称
        holder.goods_Price.setText("¥" + data.getProductPrice());
        holder.goods_Amount.setText("库存" + data.getStockCount() + "件");
    }

    @Override
    public int getItemCount() {//返回数目
        return mGoodsDataList.size();
    }

    // 定义item条目点击事件接口
    public interface OnItemClickListener {
        void onItemClickListener(int position, List<MallGoodsModel> mallGoodsModelList);
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
