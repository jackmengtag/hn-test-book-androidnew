package com.yangna.lbdsp.common.swiperefreshLayout;

import android.widget.Toast;

import com.yangna.lbdsp.common.base.BaseApplication;

/**
 * @author jingbin
 */
public class ToastUtil {

    public static void showToast(String text) {
        Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
