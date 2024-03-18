package com.yangna.lbdsp.common.utils;

import android.annotation.SuppressLint;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

public class PermissionUtils {

    @SuppressLint("CheckResult")
    public static void requestPermission(FragmentActivity context, PermissionCallback callback, String... permissions) {
        new RxPermissions(context).requestEachCombined(permissions)
                .subscribe(permission -> {
                    if (permission.granted) {
                        callback.success();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        callback.failure();
                    } else {
                        callback.failure();
                    }
                });
    }
}
