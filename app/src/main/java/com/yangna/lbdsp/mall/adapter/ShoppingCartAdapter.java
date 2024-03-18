package com.yangna.lbdsp.mall.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.mall.bean.GoodsBean;
import com.yangna.lbdsp.mall.bean.ICartItem;
import com.yangna.lbdsp.mall.bean.NormalBean;
import com.yangna.lbdsp.mall.bean.ShopBean;

import java.util.List;

public class ShoppingCartAdapter extends CartAdapter<CartViewHolder> {

    List<ICartItem> videoInfoList;

    public ShoppingCartAdapter(Context context, List datas) {
        super(context, datas);
        Log.i("ShoppingCartAdapter", "跪求有数据了" + datas.size());
    }

    public void setNewData(List<ICartItem> videoInfoList) {
        this.videoInfoList = videoInfoList;
        notifyDataSetChanged();
        Log.i("ShoppingCartAdapter", "网络请求成功回调添加数据" + videoInfoList.size());
    }

    /**
     * NormalViewHolder通知有多少件降价商品
     * @param itemView
     * @return
     */
    @Override
    protected CartViewHolder getNormalViewHolder(View itemView) {
        return new NormalViewHolder(itemView, -1);
    }

    @Override
    protected CartViewHolder getGroupViewHolder(View itemView) {
        return (CartViewHolder) new GroupViewHolder(itemView, R.id.checkbox);
    }

    @Override
    protected CartViewHolder getChildViewHolder(View itemView) {
        return (CartViewHolder) (new ChildViewHolder(itemView, R.id.checkbox) {
            @Override
            public void onNeedCalculate() {
                if (onCheckChangeListener != null) {
                    onCheckChangeListener.onCalculateChanged(null);
                }
            }
        });
    }

    @Override
    protected int getChildItemLayout() {
        return R.layout.activity_main_item_child;
    }

    @Override
    protected int getGroupItemLayout() {
        return R.layout.activity_main_item_group;
    }

    @Override
    protected int getNormalItemLayout() {
        return R.layout.activity_main_item_normal;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ChildViewHolder) {
            ChildViewHolder childViewHolder = (ChildViewHolder) holder;
            childViewHolder.textView.setText(((GoodsBean) mDatas.get(position)).getGoods_name());
            childViewHolder.textViewPrice.setText(
                    mContext.getString(R.string.rmb_X, ((GoodsBean) mDatas.get(position)).getGoods_price()));
            childViewHolder.textViewNum.setText(String.valueOf(((GoodsBean) mDatas.get(position)).getGoods_amount()));
            Glide.with(mContext)
                    .load(((GoodsBean) mDatas.get(position)).getImageUrl())
                    .error(R.drawable.tjtp) //异常时候显示的图片
                    .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                    .fallback(R.drawable.tjtp) //url为空的时候,显示的图片
//                .bitmapTransform(new CropCircleTransformation(BaseApplication.getContext()))//显示为圆形图片
                    .into(childViewHolder.draw_goods);
            childViewHolder.tv_num.setText(((GoodsBean) mDatas.get(position)).getProductNumber() + "");
        } else if (holder instanceof GroupViewHolder) {
            GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
            groupViewHolder.textView.setText(((ShopBean) mDatas.get(position)).getShop_name());
        } else if (holder instanceof NormalViewHolder) {
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            normalViewHolder.imgViewClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatas.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mDatas.size());
                }
            });
            normalViewHolder.textView.setText(mContext.getString(R.string.normal_tip_X,
                    ((NormalBean) mDatas.get(position)).getMarkdownNumber()));
        }
    }
}
