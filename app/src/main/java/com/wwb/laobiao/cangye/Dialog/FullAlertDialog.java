package com.wwb.laobiao.cangye.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

public class FullAlertDialog extends AlertDialog {
    protected FullAlertDialog(Context context) {
        super(context);
        this.mContext=context;
    }

    protected FullAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected FullAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */

//        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.gravity= Gravity.BOTTOM;
//        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height= WindowManager.LayoutParams.MATCH_PARENT;
////        getWindow().getDecorView().setPadding(0, 0, 0, 0);
//        getWindow().setAttributes(layoutParams);
    }
}
