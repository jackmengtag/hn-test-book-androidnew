package com.yangna.lbdsp.videoCom.AliPlay;

import android.content.Context;

import com.aliyun.vod.common.utils.MySystemParams;


/**
 * 像素密度计算工具
 */
public class DensityUtil {
    public DensityUtil() {
    }

    public static int dip2px(Context paramContext, float paramFloat) {
        return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
    }

    public static int px2dip(Context paramContext, float paramFloat) {
        return (int)(0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
    }

    public static int sp2px(Context context, float spValue) {
        float var2 = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * var2 + 0.5F);
    }

    public static int dip2px(float value) {
        return (int)(0.5F + value * MySystemParams.getInstance().scale);
    }

    public static int px2dip(float value) {
        return (int)(0.5F + value / MySystemParams.getInstance().scale);
    }

    public static int sp2px(float value) {
        return (int)(0.5F + value * MySystemParams.getInstance().fontScale);
    }

    public static int getActualScreenWidth() {
        boolean var0 = false;
        MySystemParams var1 = MySystemParams.getInstance();
        int var2;
        if (var1.screenOrientation == 2) {
            var2 = var1.screenHeight;
        } else {
            var2 = var1.screenWidth;
        }

        return var2;
    }

    public static int getActualScreenHeight() {
        boolean var0 = false;
        MySystemParams var1 = MySystemParams.getInstance();
        int var2;
        if (var1.screenOrientation == 2) {
            var2 = var1.screenWidth;
        } else {
            var2 = var1.screenHeight;
        }

        return var2;
    }
}

