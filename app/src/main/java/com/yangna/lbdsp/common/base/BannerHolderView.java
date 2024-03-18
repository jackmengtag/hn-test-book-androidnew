package com.yangna.lbdsp.common.base;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.yangna.lbdsp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图片轮播适配器
 */
public class BannerHolderView implements Holder<String> {

    @BindView(R.id.banner_img)
    ImageView bannerImg;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_img, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
//        bannerImg.setImageURI(Uri.parse(data));
        Glide.with(context).load(data).error(R.mipmap.default_icon).into(bannerImg);
    }
}
