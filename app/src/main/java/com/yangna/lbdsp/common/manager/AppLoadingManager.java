package com.yangna.lbdsp.common.manager;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yangna.lbdsp.R;

public class AppLoadingManager {

    private Activity ac;

    private LinearLayout loadCarLl = null;

    //加载中
    private LinearLayout loadIngLl = null;
    private ImageView loadCarImg = null;

    //加载失败
    private LinearLayout loadFailLl = null;
    private Button againBtn = null;

    public AppLoadingManager(Activity ac) {
        if (ac != null) {
            this.ac = ac;
            loadCarLl = ac.findViewById(R.id.load_car_ll);
            loadIngLl = ac.findViewById(R.id.load_ing_ll);
            loadCarImg = ac.findViewById(R.id.load_car_img);

            loadFailLl = ac.findViewById(R.id.load_fail_ll);
            againBtn = ac.findViewById(R.id.again_btn);

            initLoadingView();
        }
    }

    public AppLoadingManager(Activity ac, View view) {
        if (ac != null) {
            this.ac = ac;
            loadCarLl = view.findViewById(R.id.load_car_ll);
            loadIngLl = view.findViewById(R.id.load_ing_ll);
            loadCarImg = view.findViewById(R.id.load_car_img);

            loadFailLl = view.findViewById(R.id.load_fail_ll);
            againBtn = view.findViewById(R.id.again_btn);

            initLoadingView();
        }
    }

    public void initLoadingView() {
        if (loadCarLl != null && loadCarImg != null && loadIngLl != null && loadFailLl != null) {
            loadCarLl.setVisibility(View.VISIBLE);

            loadIngLl.setVisibility(View.VISIBLE);
            loadFailLl.setVisibility(View.GONE);
//            RequestOptions options = new RequestOptions()
//                    .placeholder(R.mipmap.load_no_car_img)
//                    .error(R.mipmap.load_no_car_img);
            Glide.with(ac)
                    .load(R.mipmap.load_car_img)//.asGif()  2021年1月16日11:43:57 如果添加.asGif()，这样的话就只能加载gif文件，如果不加，既可以加载图片也可以加载.gif。
                    .into(loadCarImg);

        }
    }

    /**
     * 网络请求成功
     */
    public void networkSuccess() {
        if (loadCarLl != null) {
            loadCarLl.setVisibility(View.GONE);
        }
    }

    /**
     * 网络请求失败
     */
    public void networkFail(final I0nClickListener listener) {
        if (loadCarLl != null && loadIngLl != null && loadFailLl != null) {
            loadCarLl.setVisibility(View.VISIBLE);

            loadIngLl.setVisibility(View.GONE);
            loadFailLl.setVisibility(View.VISIBLE);

            againBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onConfirm();

                        loadIngLl.setVisibility(View.VISIBLE);
                        loadFailLl.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    //点击事件回调
    public interface I0nClickListener {
        void onConfirm();
    }

}
