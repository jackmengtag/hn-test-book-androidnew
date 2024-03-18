package com.yangna.lbdsp.common.manager;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.base.BaseApplication;
import com.youth.banner.loader.ImageLoader;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * artifact  banner轮播图 图片加载管理
 */
public class BannerGlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(path)
                .error(R.drawable.tjtp) //异常时候显示的图片
                .placeholder(R.drawable.tjtp) //加载成功前显示的图片
                .fallback(R.drawable.tjtp) //url为空的时候,显示的图片
//                .bitmapTransform(new CropCircleTransformation(BaseApplication.getContext()))//轮播图不需要圆形图片
                .into(imageView);
    }

}
