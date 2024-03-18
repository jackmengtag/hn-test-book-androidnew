package com.yangna.lbdsp.common.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;

import com.yangna.lbdsp.mall.bean.MallGoodsModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/* 图片下载后获取宽高再设置显示具体宽高比Service */
public class DataService extends IntentService {

    public DataService() {
        super("");
    }

    public static void startService(Context context, List<MallGoodsModel.GoodsList.Records> datas) {
        Intent intent = new Intent(context, DataService.class);
        /*放入数组*/
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) datas);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        /*取出数组*/
        List<MallGoodsModel.GoodsList.Records> datas = intent.getParcelableArrayListExtra("data");
        if (datas.size() == 0) {
            EventBus.getDefault().post("finish");
            return;
        }
        for (MallGoodsModel.GoodsList.Records data : datas) {
            Bitmap bitmap = ImageLoader.load(this, data.getUseDes());
            if (bitmap != null) {
                data.setWidth(bitmap.getWidth());
                data.setHeight(bitmap.getHeight());
            }
        }
        EventBus.getDefault().post(datas);
    }

}