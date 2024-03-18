package com.wwb.laobiao.cangye.Dialog;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.yangna.lbdsp.common.swiperefreshLayout.ToastUtil;

import java.util.Observable;

public class UTipDialog {
    private AlertDialog.Builder choiceBuilder;
    private UTipDialog() {

    }
    private static UTipDialog teacher = null;

    public void eshowtip(String strtip, Context context) {
//        ToastUtil.showToast(strtip);
//        choiceBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
//        choiceBuilder.setCancelable(false);
//        choiceBuilder
//                .setTitle(strtip)
//                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//        choiceBuilder.create();
//        choiceBuilder.show();

    }
    public static UTipDialog getInstance() {
        if (teacher == null) {
            synchronized (UTipDialog.class) {
                if (teacher == null) {
                    teacher = new UTipDialog();
                }
            }

        }
        return teacher;
    }

    /**
     * 长按二维码图片弹出选择框（保存或分享）
     */
    private void imgChooseDialog(Context context){
        androidx.appcompat.app.AlertDialog.Builder choiceBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
        choiceBuilder.setCancelable(false);
        choiceBuilder
                .setTitle("选择")
                .setSingleChoiceItems(new String[]{"存储至手机", "分享"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0://存储

                                        break;
                                    case 1:// 分享

                                        break;
                                    default:
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        choiceBuilder.create();
        choiceBuilder.show();
    }
}
