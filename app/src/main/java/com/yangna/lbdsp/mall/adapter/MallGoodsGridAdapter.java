package com.yangna.lbdsp.mall.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.yangna.lbdsp.common.base.ImageLoader;
import com.yangna.lbdsp.common.base.ScaleImageView;
import com.yangna.lbdsp.mall.bean.MallGoodsModel;


import java.util.List;

public class MallGoodsGridAdapter extends BaseQuickAdapter<MallGoodsModel.GoodsList.Records, BaseViewHolder> {

    public MallGoodsGridAdapter() {
        super((R.layout.item_mall_goods_grid_layout));
    }

    public MallGoodsGridAdapter(int layoutResId, @Nullable List<MallGoodsModel.GoodsList.Records> data) {
        super(layoutResId, data);
    }

    public MallGoodsGridAdapter(int layoutResId) {
        super(layoutResId);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert( BaseViewHolder baseViewHolder, MallGoodsModel.GoodsList.Records mallGoodsModel) {
        ScaleImageView imageView = baseViewHolder.getView(R.id.mall_goods_grid_item_iv);
        imageView.setInitSize(mallGoodsModel.getWidth(), mallGoodsModel.getHeight());
        ImageLoader.load(BaseApplication.getContext(), mallGoodsModel.getUseDes(), imageView);

        TextView name = baseViewHolder.getView(R.id.mall_goods_grid_Name);
        name.setText(mallGoodsModel.getProductName());
        TextView price = baseViewHolder.getView(R.id.mall_goods_grid_Price);
        price.setText("¥" + mallGoodsModel.getMarketPrice());
        TextView amount = baseViewHolder.getView(R.id.mall_goods_grid_Amount);
        amount.setText("库存" + mallGoodsModel.getStockCount() + "件");
    }
}
